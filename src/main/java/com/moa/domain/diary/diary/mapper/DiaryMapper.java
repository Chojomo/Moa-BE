package com.moa.domain.diary.diary.mapper;

import com.moa.domain.diary.diary.dto.DiaryDto;
import com.moa.domain.diary.diary.entity.Diary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DiaryMapper {

    public DiaryDto.GetDiaryResponse diaryToGetDiaryResponse(Diary diary, Boolean isLiked) {
        return DiaryDto.GetDiaryResponse.builder()
                .diaryId(diary.getDiaryId())
                .diaryAuthorId(diary.getUser().getUserId())
                .diaryAuthorNickname(diary.getUser().getUserNickname())
                .diaryAuthorProfileImage(diary.getUser().getUserProfileImage())
                .diaryTitle(diary.getDiaryTitle())
                .diaryContents(diary.getDiaryContents())
                .diaryThumbnail(diary.getDiaryThumbnail())
                .isDiaryPublic(diary.getIsDairyPublic())
                .isLiked(isLiked)
                .diaryPublishedAt(diary.getPublishedAt())
                .viewCount(diary.getViewCount())
                .likeCount(diary.getLikeCount())
                .build();
    }

    public DiaryDto.DiaryPreview diaryTodiaryPreview(Diary diary) {
        return DiaryDto.DiaryPreview.builder()
                .diaryId(diary.getDiaryId())
                .diaryAuthorId(diary.getUser().getUserId())
                .diaryAuthorNickname(diary.getUser().getUserNickname())
                .diaryThumbnail(diary.getDiaryThumbnail())
                .diaryTitle(diary.getDiaryTitle())
                .diaryContents(diary.getDiaryContents())
                .diaryPublishedAt(LocalDate.from(diary.getPublishedAt()))
                .viewCount(diary.getViewCount())
                .likeCount(diary.getLikeCount())
                .commentCount(diary.getCommentCount())
                .build();
    }

}
