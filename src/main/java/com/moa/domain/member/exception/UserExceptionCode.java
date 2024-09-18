package com.moa.domain.member.exception;

import com.moa.global.exception.BaseExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserExceptionCode implements BaseExceptionCode {

<<<<<<< Updated upstream
    USER_NOT_EXISTS(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.", "001");

    private final HttpStatus httpStatus;
=======
<<<<<<< Updated upstream
    USER_NOT_EXISTS(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.", "001");

    private final HttpStatus httpStatus;
=======
    USER_NOT_EXISTS(HttpStatus.NOT_FOUND.value(), "유저를 찾을 수 없습니다.", "USER_001");

    private final Integer status;
>>>>>>> Stashed changes
>>>>>>> Stashed changes
    private final String message;
    private final String errorCode;

}
