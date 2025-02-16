package com.moa.domain.diary.diary.repository;

import com.moa.domain.diary.diary.dto.query.UserDiaryDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface DiaryRepositoryCustom {

    Page<UserDiaryDto> findUserDiaryList(UUID userId, Integer pageNumber, Integer pageSize);

}
