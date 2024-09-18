package com.moa.global.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final Integer status;
    private final String message;
    private final String errorCode;

    public ErrorResponse(Integer status, String message, String errorCode) {
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
    }

    public static ErrorResponse of(BaseExceptionCode exceptionCode) {
        return new ErrorResponse(exceptionCode.getStatus(), exceptionCode.getMessage(), exceptionCode.getErrorCode());
    }

}
