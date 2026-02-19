package com.example.individual.global.apiPayload.exception;

import com.example.individual.global.apiPayload.code.BaseCode;
import com.example.individual.global.apiPayload.code.ReasonDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {
	private BaseCode code;
	public ReasonDTO getErrorReasonHttpStatus(){
		return this.code.getReasonHttpStatus();
	}
}
