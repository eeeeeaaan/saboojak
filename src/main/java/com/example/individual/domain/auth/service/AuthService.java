package com.example.individual.domain.auth.service;

import org.springframework.stereotype.Service;

import com.example.individual.domain.auth.dto.LoginRequestDTO;
import com.example.individual.domain.auth.dto.LoginResponseDTO;
import com.example.individual.domain.auth.dto.SignUpRequestDTO;


public interface AuthService {
	LoginResponseDTO processLogin(LoginRequestDTO dto);
	void processSignUp(SignUpRequestDTO dto);
	LoginResponseDTO reissueToken(String refreshToken);

}
