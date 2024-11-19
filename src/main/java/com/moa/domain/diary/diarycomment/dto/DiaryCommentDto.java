package com.moa.domain.diary.diarycomment.dto;

import lombok.Data;

public class DiaryCommentDto {

    @Data
    public static class CreateCommentRequest {
        private String commentContents;
    }

    @Data
    public static class CreateReplyRequest {
        private String replyContents;
    }

}
