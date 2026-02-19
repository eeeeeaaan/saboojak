package com.example.individual.domain.auth.dto;

import org.springframework.security.core.Authentication;

import com.example.individual.infra.security.CustomAuthenticationToken;

public record LoginRequestDTO(
	String email,
	String password
) {
	public static Authentication toAuthentication(LoginRequestDTO dto){
		return new CustomAuthenticationToken(dto.email, dto.password);
	}
}
