package com.moa.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

    private final HttpStatus httpStatus;
    private final String message;
    private final String errorCode;

    public ErrorResponse(HttpStatus httpStatus, String message, String errorCode) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.errorCode = errorCode;
    }

    public static ErrorResponse of(BaseExceptionCode exceptionCode) {
        return new ErrorResponse(exceptionCode.getHttpStatus(), exceptionCode.getMessage(), exceptionCode.getErrorCode());
    }

}
