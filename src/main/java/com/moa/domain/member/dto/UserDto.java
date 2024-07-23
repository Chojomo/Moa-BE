package com.moa.domain.member.dto;

import lombok.Data;
import org.hibernate.annotations.processing.Pattern;

public class UserDto {

    @Data
    public static class CreateUserReq {
        private String userEmail;
        private String userPw;
    }

}
