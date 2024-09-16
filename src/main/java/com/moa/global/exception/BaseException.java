package com.moa.global.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final BaseExceptionCode baseExceptionCode;

    public BaseException(BaseExceptionCode baseExceptionCode) {
        super(baseExceptionCode.getMessage());
        this.baseExceptionCode = baseExceptionCode;
    }

}

