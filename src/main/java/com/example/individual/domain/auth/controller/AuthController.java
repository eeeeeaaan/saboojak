package com.example.individual.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.individual.domain.auth.dto.LoginRequestDTO;
import com.example.individual.domain.auth.dto.LoginResponseDTO;
import com.example.individual.domain.auth.dto.SignUpRequestDTO;
import com.example.individual.domain.auth.service.AuthService;
import com.example.individual.global.apiPayload.BaseResponse;
import com.example.individual.global.apiPayload.code.status.SuccessStatus;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<BaseResponse<LoginResponseDTO>> login(
		@RequestBody LoginRequestDTO dto
	){
		return ResponseEntity.ok(BaseResponse.onSuccess(SuccessStatus.MEMBER_LOGIN_SUCCESS, authService.processLogin(dto)));
	}

	@PostMapping("/signup")
	public ResponseEntity<BaseResponse<Void>> signIn(@RequestBody SignUpRequestDTO dto){
		authService.processSignUp(dto);
		return ResponseEntity.ok(BaseResponse.onSuccess(SuccessStatus.MEMBER_SIGNUP_SUCCESS, null ));
	}

	@PostMapping("/reissue")
	public ResponseEntity<BaseResponse<LoginResponseDTO>> reissue(String refreshToken){
		authService.reissueToken(refreshToken);
		return ResponseEntity.ok(BaseResponse.onSuccess(SuccessStatus.MEMBER_LOGIN_SUCCESS, null));
	}

}
