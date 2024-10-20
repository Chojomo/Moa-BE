package com.moa.domain.member.dto;

import lombok.Builder;
import lombok.Data;

public class UserDto {

    @Data
    public static class CreateUserReq {
        private String userEmail;
        private String userPassword;
    }

    @Data
    @Builder
    public static class GetUserMyPageResponse {
        private String userProfileImage;
        private String userNickname;
        private Integer followerCount;
        private Integer followingCount;
        private Boolean isMyPage;
    }

}
