package com.moa.domain.member.exception;

import com.moa.global.exception.BaseExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserExceptionCode implements BaseExceptionCode {

    USER_NOT_EXISTS(HttpStatus.NOT_FOUND.value(), "유저를 찾을 수 없습니다.", "USER_001"),
    EMAIL_NOT_EXISTS(HttpStatus.NOT_FOUND.value(), "이메일을 찾을 수 없습니다.", "USER_002");

    private final Integer status;
    private final String message;
    private final String errorCode;

}
