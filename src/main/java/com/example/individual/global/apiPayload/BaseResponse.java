package com.example.individual.global.apiPayload;

import com.example.individual.global.apiPayload.code.BaseCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class BaseResponse<T> {
	@JsonProperty("isSuccess")
	private final Boolean isSuccess;

	private final String code;
	private final String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T result;

	// 성공 - 데이터 없음
	public static <T> BaseResponse<T> onSuccess(BaseCode code){
		return new BaseResponse<>(true, code.getReasonHttpStatus().getCode(),
			code.getReasonHttpStatus().getMessage(), null);
	}

	// 성공 - 데이터 포함
	public static <T> BaseResponse<T> onSuccess(BaseCode code, T result){
		return new BaseResponse<>(true, code.getReasonHttpStatus().getCode(),
			code.getReasonHttpStatus().getMessage(), result);
	}

	// 실패 - 인터페이스 기반
	public static <T> BaseResponse<T> onFailure(BaseCode code){
		return new BaseResponse<>(false, code.getReasonHttpStatus().getCode(),
			code.getReasonHttpStatus().getMessage(), null);
	}

	// 실패 - 인터페이스 기반 + 데이터(에러 상세) 포함
	public static <T> BaseResponse<T> onFailure(BaseCode code, T result){
		return new BaseResponse<>(false, code.getReasonHttpStatus().getCode(),
			code.getReasonHttpStatus().getMessage(), result);
	}

	// 실패 - 직접 코드와 메시지를 주입 (Spring 내장 예외 처리용)
	public static <T> BaseResponse<T> onFailure(String code, String message, T result) {
		return new BaseResponse<>(false, code, message, result);
	}
}
