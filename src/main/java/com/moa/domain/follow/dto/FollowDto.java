package com.moa.domain.follow.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

public class FollowDto {

    @Data
    @Builder
    public static class GetUserFollowsResponse {
        private Integer followingCount;
        private Integer followerCount;
        private List<UserFollowingDto> followings;
        private List<UserFollowerDto> followers;
    }

    @Data
    @Builder
    public static class UserFollowingDto {
        private UUID userId;
        private String userNickname;
    }

    @Data
    @Builder
    public static class  UserFollowerDto {
        private UUID userId;
        private String userNickname;
    }

}
