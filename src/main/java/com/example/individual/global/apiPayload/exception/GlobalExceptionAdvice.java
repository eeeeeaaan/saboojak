package com.example.individual.global.apiPayload.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.individual.global.apiPayload.BaseResponse;
import com.example.individual.global.apiPayload.code.ReasonDTO;
import com.example.individual.global.apiPayload.code.status.ErrorStatus;
import com.example.individual.global.apiPayload.exception.GeneralException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler {

	// 1. 비즈니스 로직 에러 처리 (내가 직접 throw GeneralException 한 경우)
	@ExceptionHandler(GeneralException.class)
	public ResponseEntity<Object> onThrowException(GeneralException generalException, HttpServletRequest request) {
		ReasonDTO errorReasonHttpStatus = generalException.getErrorReasonHttpStatus();
		return handleExceptionInternal(generalException, errorReasonHttpStatus, null, request);
	}

	// 2. 일반적인 모든 예외 처리
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> exception(Exception e, WebRequest request) {
		return handleExceptionInternalFalse(e, ErrorStatus._INTERNAL_SERVER_ERROR,
			HttpHeaders.EMPTY, ErrorStatus._INTERNAL_SERVER_ERROR.getHttpStatus(), request, e.getMessage());
	}

	// 3. 내부 메서드: BaseCode(Enum)를 사용하는 경우
	private ResponseEntity<Object> handleExceptionInternal(Exception e, ReasonDTO reason, HttpHeaders headers, HttpServletRequest request) {
		// BaseResponse.onFailure(BaseCode code, T result) 호출
		BaseResponse<Object> body = BaseResponse.onFailure(ErrorStatus.valueOf(reason.getCode()), null);
		WebRequest webRequest = new ServletWebRequest(request);
		return super.handleExceptionInternal(e, body, headers, reason.getHttpStatus(), webRequest);
	}

	// 4. 내부 메서드: 직접 코드와 메시지를 주입하는 경우 (Spring 내장 예외용)
	private ResponseEntity<Object> handleExceptionInternalFalse(Exception e, ErrorStatus errorCommonStatus,
		HttpHeaders headers, HttpStatus status, WebRequest request, String errorPoint) {
		// BaseResponse.onFailure(String code, String message, T result) 호출
		BaseResponse<Object> body = BaseResponse.onFailure(errorCommonStatus.getCode(), errorCommonStatus.getMessage(), errorPoint);
		return super.handleExceptionInternal(e, body, headers, status, request);
	}

	// 5. Spring 내장 예외 - TypeMismatch 처리 예시
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String errorMessage = e.getPropertyName() + ": 올바른 값이 아닙니다.";
		return handleExceptionInternalFalse(e, ErrorStatus._BAD_REQUEST, headers, HttpStatus.valueOf(status.value()), request, errorMessage);
	}
}
