package com.moa.domain.diary.diarylike.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public class DiaryLikeDto {

    @Data
    @Builder
    public static class GetDiaryLikesResponse {
        private Integer totalLikes;
        private List<DiaryLikedUserDto> likedUsers;
    }

    @Data
    @Builder
    public static class DiaryLikedUserDto {
        private String userId;
        private String userNickname;
        private String userProfileImage;
    }

}
