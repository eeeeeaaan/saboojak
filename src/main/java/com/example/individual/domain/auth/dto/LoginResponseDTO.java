package com.example.individual.domain.auth.dto;

public record LoginResponseDTO(
	String accessToken,
	String refreshToken
) {
	public static LoginResponseDTO from(String accessToken, String refreshToken){
		return new LoginResponseDTO(
			accessToken,
			refreshToken
		);
	}
}
