package com.moa.domain.diary.diarycomment.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

public class DiaryCommentDto {

    @Data
    public static class CreateCommentRequest {
        private String commentContents;
    }

    @Data
    public static class UpdateCommentRequest {
        private String commentContents;
    }

    @Data
    public static class CreateReplyRequest {
        private String replyContents;
    }

    @Data
    @Builder
    public static class CreateCommentResponse {
        private UUID commentId;
        private Boolean isCommentOwner;
        private UUID commentAuthorId;
        private String diaryAuthorNickname;
        private String diaryAuthorProfileImage;
        private LocalDateTime createdAt;
        private String commentContents;
    }

    @Data
    @Builder
    public static class UpdateCommentResponse {
        private UUID commentId;
        private Boolean isCommentOwner;
        private UUID commentAuthorId;
        private String diaryAuthorNickname;
        private String diaryAuthorProfileImage;
        private LocalDateTime createdAt;
        private String commentContents;
    }

    @Data
    @Builder
    public static class CreateReplyResponse {
        private UUID replyId;
        private Boolean isReplyOwner;
        private UUID replyAuthorId;
        private String replyAuthorNickname;
        private String replyAuthorProfileImage;
        private LocalDateTime createdAt;
        private String replyContents;
    }

}
