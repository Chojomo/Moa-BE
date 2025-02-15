package com.moa.domain.diary.diary.repository;

import com.moa.domain.diary.diary.dto.DiaryDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface DiaryRepositoryCustom {

    Page<DiaryDto.UserDiaryData> findUserDiaryList(UUID userId, Integer pageNumber, Integer pageSize);

}
