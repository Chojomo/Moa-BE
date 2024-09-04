package com.moa.domain.diary.dto;

import lombok.Builder;
import lombok.Data;

public class DiaryDto {

    @Data
    @Builder
    public static class CreateDiaryImageResponse {
        private String imageUrl;
    }

}
