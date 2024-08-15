package com.moa.domain.member.dto;

import lombok.Data;

public class UserDto {

    @Data
    public static class CreateUserReq {
        private String userEmail;
        private String userPw;
    }

}
