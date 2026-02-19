package com.example.individual.domain.auth.tokenManager;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.individual.domain.user.entity.Members;
import com.example.individual.domain.user.enums.UserRole;
import com.example.individual.global.apiPayload.code.status.ErrorStatus;
import com.example.individual.global.apiPayload.exception.GeneralException;
import com.example.individual.infra.security.CustomAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtTokenManager implements TokenManager {

	private final SecretKey secretKey;

	public JwtTokenManager(@Value("${jwt.secret}") String secretKey) {
		this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public CustomAuthenticationToken readToken(String token) throws
		SignatureException, UnsupportedJwtException, MalformedJwtException, ExpiredJwtException {

		Claims claims = Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody();

		String email = claims.get("email", String.class);
		Long id = claims.get("id", Long.class);
		String roleStr = claims.get("role", String.class);
		UserRole userRole = UserRole.valueOf(roleStr);

		// [수정] Unsafe operation 경고 해결을 위한 타입 체크 및 변환
		Object authoritiesClaim = claims.get("authorities");
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

		if (authoritiesClaim instanceof List<?>) {
			List<?> list = (List<?>) authoritiesClaim;
			for (Object authority : list) {
				try {
					// String으로 안전하게 변환 후 UserRole 매핑
					grantedAuthorities.add(UserRole.valueOf(String.valueOf(authority)));
				} catch (IllegalArgumentException e) {
					throw new MalformedJwtException("등록되지 않은 권한이 포함되어 있습니다.");
				}
			}
		}

		if (grantedAuthorities.isEmpty()) {
			throw new MalformedJwtException("권한 정보가 없습니다.");
		}

		return new CustomAuthenticationToken(email, null, grantedAuthorities, id, userRole);
	}


	@Override
	public String createToken(Authentication authentication) {
		CustomAuthenticationToken customAuth = (CustomAuthenticationToken) authentication;

		// 만료 시간 설정 (24시간)
		long now = System.currentTimeMillis();
		Date expiryDate = new Date(now + 1000 * 60 * 60 * 24);

		return Jwts.builder()
			.setHeader(Map.of(
				"provider", "individual",
				"type", "accessToken"
			))
			.setClaims(Map.of(
				"email", customAuth.getName(),
				"authorities", customAuth.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList()), // 명시적 리스트 변환
				"id", customAuth.getDetails(),
				"role", customAuth.getRole().name() // Enum 대신 문자열로 저장 권장
			))
			.setIssuedAt(new Date(now))
			.setExpiration(expiryDate)
			.signWith(secretKey)
			.compact();
	}

	@Override
	public String createRefreshToken(Authentication authentication)  {
		long now = System.currentTimeMillis();
		long day = 1000L * 3600 * 24;
		Date expiryDate = new Date(now + day * 14);

		return Jwts.builder()
			.setHeader(Map.of("provider", "individual",
				"type", "refreshToken"))
			.setSubject(authentication.getName())
			.setIssuedAt(new Date(now))
			.setExpiration(expiryDate)
			.signWith(secretKey)
			.compact();
	}

	@Override
	public String getEmailFromRefreshToken(String refreshToken) {

		try {
			Jws<Claims> jws = Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(refreshToken);

			String type = (String) jws.getHeader().get("type");
			if(!"refreshToken".equals(type)){
				throw new GeneralException(ErrorStatus.NOT_REFRESH_TOKEN);
			}
			return jws.getBody().getSubject();
		} catch( ExpiredJwtException e){
			throw new GeneralException(ErrorStatus.REFRESH_TOKEN_EXPIRED);
		} catch (Exception e){
			throw new GeneralException(ErrorStatus.INVALID_REFRESH_TOKEN);
		}

	}
}