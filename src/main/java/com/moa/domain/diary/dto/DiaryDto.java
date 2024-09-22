package com.moa.domain.diary.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
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
        private String thumbnail;
        private Boolean isDiaryPublic;
    }

    @Data
    @Builder
    public static class GetDiaryResponse {
        private UUID diaryId;
        private Byte diaryStatus;
        private String diaryTitle;
        private String diaryContents;
        private String diaryThumbnail;
        private Boolean isDiaryPublic;
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
    public static class DiaryPreview {
        private UUID diaryId;
        private UUID diaryAuthorId;
        private String diaryAuthorNickname;
        private String diaryThumbnail;
        private String diaryTitle;
        private String diaryContents;
        private LocalDate diaryPublishedAt;
    }

}
