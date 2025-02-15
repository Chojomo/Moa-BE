package com.moa.domain.diary.diary.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class DiaryDto {

    @Data
    @Builder
    public static class CreateDiaryImageResponse {
        private String imageUrl;
    }

    @Data
    @Builder
    public static class InitializeDiaryResponse {
        private UUID diaryId;
    }

    @Data
    public static class UpdateDiaryRequest {
        private UUID diaryId;
        private String diaryTitle;
        private String diaryContents;
        private Boolean isDiaryPublic;
    }

    @Data
    @Builder
    public static class GetDiaryResponse {
        private UUID diaryId;
        private Boolean isDiaryOwner;
        private UUID diaryAuthorId;
        private String diaryAuthorNickname;
        private String diaryAuthorProfileImage;
        private String diaryTitle;
        private String diaryContents;
        private String diaryThumbnail;
        private Boolean isDiaryPublic;
        private Boolean isLiked;
        private LocalDateTime diaryPublishedAt;
        private Long viewCount;
        private Long likeCount;
        private Long commentCount;
        private Comments comment;
    }

    @Data
    @Builder
    public static class Comments {
        private List<CommentData> comments;
    }

    @Data
    @Builder
    public static class CommentData {
        private UUID commentId;
        private Boolean isCommentOwner;
        private UUID commentAuthorId;
        private String diaryAuthorNickname;
        private String diaryAuthorProfileImage;
        private LocalDateTime createdAt;
        private Boolean isLiked;
        private Long likeCount;
        private String commentContents;
        private List<ReplyData> replies;
    }

    @Data
    @Builder
    public static class ReplyData {
        private UUID replyId;
        private Boolean isReplyOwner;
        private UUID replyAuthorId;
        private String replyAuthorNickname;
        private String replyAuthorProfileImage;
        private LocalDateTime createdAt;
        private Boolean isLiked;
        private Long likeCount;
        private String replyContents;
    }

    @Data
    @Builder
    public static class PublishDiaryRequest {
        private UUID diaryId;
        private String diaryTitle;
        private String diaryContents;
        private String diaryThumbnail;
        private Boolean isDiaryPublic;
    }

    @Data
    @Builder
    public static class GetDiaryListResponse {
        private List<DiaryPreview> diaryPreviewList;
    }

    @Data
    @Builder
    public static class UploadThumbnailResponse {
        private String thumbnailUrl;
    }

    @Data
    @Builder
    public static class DiaryPreview {
        private UUID diaryId;
        private UUID diaryAuthorId;
        private String diaryAuthorNickname;
        private String diaryThumbnail;
        private String diaryTitle;
        private String diaryContents;
        private LocalDate diaryPublishedAt;
        private Long viewCount;
        private Long likeCount;
        private Long commentCount;
    }

    @Data
    @Builder
    public static class UserDiaryData {
        private UUID diaryId;
        private String diaryThumbnail;
        private String diaryTitle;
        private String diaryContents;
        private LocalDateTime diaryPublishedAt;
        private Long likeCount;
        private Long commentCount;

        @QueryProjection
        public UserDiaryData(UUID diaryId, String diaryThumbnail, String diaryTitle, String diaryContents, LocalDateTime diaryPublishedAt, Long likeCount, Long commentCount) {
            this.diaryId = diaryId;
            this.diaryThumbnail = diaryThumbnail;
            this.diaryTitle = diaryTitle;
            this.diaryContents = diaryContents;
            this.diaryPublishedAt = diaryPublishedAt;
            this.likeCount = likeCount;
            this.commentCount = commentCount;
        }
    }

}
