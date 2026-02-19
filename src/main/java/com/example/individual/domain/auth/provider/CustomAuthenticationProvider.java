package com.example.individual.domain.auth.provider;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.individual.global.apiPayload.code.status.ErrorStatus;
import com.example.individual.global.apiPayload.exception.GeneralException;
import com.example.individual.infra.security.CustomAuthenticationToken;
import com.example.individual.infra.security.CustomUserDetails;
import com.example.individual.infra.security.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {
	private final PasswordEncoder passwordEncoder;
	private final CustomUserDetailsService customUserDetailsService;


	@Override
	public CustomAuthenticationToken authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String pw = (String)authentication.getCredentials();

		UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

		if(passwordEncoder.matches(pw, userDetails.getPassword())){
			return new CustomAuthenticationToken(userDetails, null, userDetails.getAuthorities(), ((CustomUserDetails)userDetails).getDatabaseId(),
				((CustomUserDetails)userDetails).getUserRole());

		} else {
			throw new GeneralException(ErrorStatus.PASSWORD_NOT_MATCH);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return CustomAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
