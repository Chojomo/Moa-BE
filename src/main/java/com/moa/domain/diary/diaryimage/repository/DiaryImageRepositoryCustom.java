package com.moa.domain.diary.diaryimage.repository;

import java.util.UUID;

public interface DiaryImageRepositoryCustom {

    boolean existsByDiaryId(UUID diaryId);

}
