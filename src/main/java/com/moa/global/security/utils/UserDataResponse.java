package com.moa.global.security.utils;

import lombok.Builder;

import java.util.UUID;

@Builder
public class UserDataResponse {

    private UUID userId;
    private String userNickname;
    private String userProfileImage;

}
