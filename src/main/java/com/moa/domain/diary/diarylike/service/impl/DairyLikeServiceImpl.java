package com.moa.domain.diary.diarylike.service.impl;

import com.moa.domain.diary.diary.entity.Diary;
import com.moa.domain.diary.diarylike.entity.DiaryLike;
import com.moa.domain.diary.diarylike.repository.DiaryLikeRepository;
import com.moa.domain.diary.diarylike.service.DiaryLikeService;
import com.moa.domain.member.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DairyLikeServiceImpl implements DiaryLikeService {

    private final DiaryLikeRepository diaryLikeRepository;

    public void toggleLikeOnDiary(User user, Diary diary) {
        diaryLikeRepository.findDiaryLikeByUserAndDiary(user, diary)
                .ifPresentOrElse(
                        diaryLikeRepository::delete,
                        () -> diaryLikeRepository.save(
                                DiaryLike.builder()
                                        .user(user)
                                        .diary(diary)
                                        .build())
                );
    }

}
