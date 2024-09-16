package com.moa.domain.member.exception;

import com.moa.global.exception.BaseException;
public class UserException extends BaseException {

    public UserException(UserExceptionCode userExceptionCode) {
        super(userExceptionCode);
    }

}
