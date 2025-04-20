package com.moa.domain.diary.diary.repository;

import com.moa.domain.diary.diary.dto.query.UserDiaryDto;
import com.moa.domain.diary.diary.dto.query.UserLikedDiaryDto;
import com.moa.domain.diary.diary.entity.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DiaryRepositoryCustom {

    Page<UserDiaryDto> findUserDiaryList(UUID userId, Pageable pageable);

    Page<UserLikedDiaryDto> findUserLikedDiaryList(UUID userId, Pageable pageable);

    Page<Diary> findAllWithUser(Pageable pageable);

}
