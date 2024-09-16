package com.moa.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public interface BaseExceptionCode {

    HttpStatus getHttpStatus();
    String getMessage();
    String getErrorCode();

}
