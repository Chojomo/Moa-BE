package com.moa.domain.member.exception;

import com.moa.global.exception.BaseExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserExceptionCode implements BaseExceptionCode {

    USER_NOT_EXISTS(HttpStatus.NOT_FOUND.value(), "유저를 찾을 수 없습니다.", "USER_001"),
    EMAIL_NOT_EXISTS(HttpStatus.NOT_FOUND.value(), "이메일을 찾을 수 없습니다.", "USER_002"),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST.value(), "현재 비밀번호가 일치하지 않습니다.", "USER_003"),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST.value(), "새 비밀번호와 확인 비밀번호가 일치하지 않습니다.", "USER_004");

    private final Integer status;
    private final String message;
    private final String errorCode;

}
