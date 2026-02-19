package com.example.individual.domain.auth.tokenManager;

import org.springframework.security.core.Authentication;

import com.example.individual.infra.security.CustomAuthenticationToken;

public interface TokenManager {
	CustomAuthenticationToken readToken(String token);
	String createToken(Authentication authentication);
	String createRefreshToken(Authentication authentication);
	String getEmailFromRefreshToken(String refreshToken);
}
