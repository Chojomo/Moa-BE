package com.moa.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException exception) {
        ErrorResponse errorResponse = ErrorResponse.of(exception.getBaseExceptionCode());
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

}
