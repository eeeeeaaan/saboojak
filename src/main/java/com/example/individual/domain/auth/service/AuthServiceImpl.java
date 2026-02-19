package com.example.individual.domain.auth.service;

import static com.example.individual.domain.auth.dto.LoginRequestDTO.*;
import static com.example.individual.domain.auth.dto.SignUpRequestDTO.*;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.individual.domain.auth.dto.LoginRequestDTO;
import com.example.individual.domain.auth.dto.LoginResponseDTO;
import com.example.individual.domain.auth.dto.SignUpRequestDTO;
import com.example.individual.domain.auth.tokenManager.TokenManager;
import com.example.individual.domain.user.entity.Members;
import com.example.individual.domain.user.repository.MemberRepository;
import com.example.individual.global.apiPayload.code.status.ErrorStatus;
import com.example.individual.global.apiPayload.exception.GeneralException;
import com.example.individual.infra.redis.RefreshToken;
import com.example.individual.infra.redis.repository.RefreshTokenRepository;
import com.example.individual.infra.security.CustomAuthenticationToken;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService{
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final TokenManager tokenManager;
	private final RefreshTokenRepository refreshTokenRepository;
	private final RedisTemplate<String, String> redisTemplate;


	@Override
	public LoginResponseDTO processLogin(LoginRequestDTO dto) {
		Authentication authentication = authenticationManager.authenticate(
			toAuthentication(dto)
		);
		String accessToken = tokenManager.createToken(authentication);
		String refreshToken = tokenManager.createRefreshToken(authentication);

		RefreshToken refresh = new RefreshToken(authentication.getName(), refreshToken);
		refreshTokenRepository.save(refresh);

		return LoginResponseDTO.from(accessToken, refreshToken);
	}

	@Override
	public void processSignUp(SignUpRequestDTO dto) {

		Members member = toMember(dto, passwordEncoder);
		memberRepository.save(member);
	}

	@Override
	public LoginResponseDTO reissueToken(String refreshToken) {
		String email = tokenManager.getEmailFromRefreshToken(refreshToken);
		RefreshToken savedToken =refreshTokenRepository.findByEmail(email).orElseThrow(
			() -> new GeneralException(ErrorStatus.INVALID_REFRESH_TOKEN)
		);

		if(!savedToken.getRefreshToken().equals(refreshToken)){
			throw new GeneralException(ErrorStatus.INVALID_REFRESH_TOKEN);
		}

		Members member = memberRepository.findByEmail(email)
			.orElseThrow(
				() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND)
			);

		Authentication authentication = createAuthByMember(member);
		String newAccessToken = tokenManager.createToken(authentication);
		String newRefreshToken = tokenManager.createRefreshToken(authentication);

		redisTemplate.delete(email);
		refreshTokenRepository.save(new RefreshToken(email, newRefreshToken));

		return LoginResponseDTO.from(newAccessToken, newRefreshToken);

	}

	private Authentication createAuthByMember(Members member){
		List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(member.getUserRole().name()));

		return new CustomAuthenticationToken(member.getEmail(),
			null, authorities, member.getId(), member.getUserRole());
	}

}
