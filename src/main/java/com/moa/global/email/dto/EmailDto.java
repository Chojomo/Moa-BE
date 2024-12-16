package com.moa.global.email.dto;

import lombok.Data;

public class EmailDto {

    @Data
    public static class TempPasswordRequest {
        private String email;
    }

}
