package com.moa.domain.diary.mapper;

import com.moa.domain.diary.dto.DiaryDto;
import com.moa.domain.diary.entity.Diary;
import org.springframework.stereotype.Component;

@Component
public class DiaryMapper {

    public DiaryDto.GetDiaryResponse diaryToGetDiaryResponse(Diary diary) {
        return DiaryDto.GetDiaryResponse.builder()
                .diaryId(diary.getDiaryId())
                .diaryStatus(diary.getDiaryStatus())
                .diaryTitle(diary.getDiaryContents())
                .diaryContents(diary.getDiaryContents())
                .diaryThumbnail(diary.getDiaryThumbnail())
                .isDiaryPublic(diary.getIsDairyPublic())
                .build();
    }

}
