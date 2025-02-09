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
        private String userIntroduce;
        private Boolean isFollowing;
        private Integer followerCount;
        private Integer followingCount;
        private Boolean isMyPage;
    }

    @Data
    public static class ChangePasswordRequest {
        private String currentPassword;
        private String newPassword;
        private String confirmNewPassword;
    }

}
