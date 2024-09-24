package com.moa.domain.diary.repository.diaryImage;

import java.util.UUID;

public interface DiaryImageRepositoryCustom {

    boolean existsByDiaryId(UUID diaryId);

}
