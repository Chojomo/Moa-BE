package com.moa.domain.diary.diary.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserDiaryDto {
    private UUID diaryId;
    private String diaryThumbnail;
    private String diaryTitle;
    private String diaryContents;
    private LocalDateTime diaryPublishedAt;
    private Long likeCount;
    private Long commentCount;

    @QueryProjection
    public UserDiaryDto(UUID diaryId, String diaryThumbnail, String diaryTitle, String diaryContents, LocalDateTime diaryPublishedAt, Long likeCount, Long commentCount) {
        this.diaryId = diaryId;
        this.diaryThumbnail = diaryThumbnail;
        this.diaryTitle = diaryTitle;
        this.diaryContents = diaryContents;
        this.diaryPublishedAt = diaryPublishedAt;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

}
