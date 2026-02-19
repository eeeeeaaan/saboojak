package com.example.individual.global.apiPayload.code.status;

import org.springframework.http.HttpStatus;

import com.example.individual.global.apiPayload.code.BaseCode;
import com.example.individual.global.apiPayload.code.ReasonDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SuccessStatus implements BaseCode {
	_OK(HttpStatus.OK, "COMMON200", "성공입니다."),
	_CREATED(HttpStatus.CREATED, "COMMON201", "요청 성공 및 리소스 생성됨"),

	MEMBER_SIGNUP_SUCCESS(HttpStatus.OK, "MEMBER201", "회원가입이 완료되었습니다."),
	MEMBER_LOGIN_SUCCESS(HttpStatus.OK, "MEMBER202", "로그인이 완료되었습니다.");



	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ReasonDTO getReasonHttpStatus(){
		return ReasonDTO.builder().build();
	}
}
