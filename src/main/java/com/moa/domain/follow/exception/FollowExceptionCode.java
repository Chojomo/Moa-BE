package com.moa.domain.follow.exception;

import com.moa.global.exception.BaseExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FollowExceptionCode implements BaseExceptionCode {

    SELF_FOLLOW_NOT_ALLOWED(HttpStatus.BAD_REQUEST.value(), "자기 자신을 팔로우할 수 없습니다.", "SELF_FOLLOW_NOT_ALLOWED");

    private final Integer status;
    private final String message;
    private final String errorCode;

}
