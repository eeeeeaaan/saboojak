package com.example.individual.global.apiPayload.code.status;

import org.springframework.http.HttpStatus;

import com.example.individual.global.apiPayload.code.BaseCode;
import com.example.individual.global.apiPayload.code.ReasonDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorStatus implements BaseCode {
	_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
	_BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
	_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
	_FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

	PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "AUTHORIZE4001" , "비밀번호가 일치하지 않습니다."),
	REFRESH_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "AUTHORIZE4002", "리프래시 토큰이 만료되었습니다."),
	INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "AUTHORIZE4003", "변형된 refreshToken 입니다."),
	NOT_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "AUTHORIZE4004", "리프래시 토큰이 아닌 엑세스 토큰입니다."),


	MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "해당 유저를 찾을 수 없습니다."),



	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ReasonDTO getReasonHttpStatus(){
		return ReasonDTO.builder().build();
	}
}
