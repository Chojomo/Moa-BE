package com.moa.global.security.dto;

import lombok.Data;

public class AuthDto {

    @Data
    public static class LoginDto {
        private String userEmail;
        private String userPassword;
    }

}
