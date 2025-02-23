package com.moa.domain.diary.diarycomment.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserCommentDto {
    private UUID commentId;
    private String commentContents;
    private UUID diaryId;
    private String diaryTitle;
    private LocalDateTime commentCreatedAt;

    @QueryProjection
    public UserCommentDto(UUID commentId, String commentContents, UUID diaryId, String diaryTitle, LocalDateTime commentCreatedAt) {
        this.commentId = commentId;
        this.commentContents = commentContents;
        this.diaryId = diaryId;
        this.diaryTitle = diaryTitle;
        this.commentCreatedAt = commentCreatedAt;
    }

}
