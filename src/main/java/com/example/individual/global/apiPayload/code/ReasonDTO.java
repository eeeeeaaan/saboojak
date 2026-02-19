package com.example.individual.global.apiPayload.code;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReasonDTO {
	private final HttpStatus httpStatus;
	private boolean isSuccess;
	private final String code;
	private final String message;
}
