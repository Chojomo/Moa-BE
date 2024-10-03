package com.moa.domain.diary.diarylike.service.impl;

import com.moa.domain.diary.diary.entity.Diary;
import com.moa.domain.diary.diarylike.dto.DiaryLikeDto;
import com.moa.domain.diary.diarylike.entity.DiaryLike;
import com.moa.domain.diary.diarylike.mapper.DiaryLikeMapper;
import com.moa.domain.diary.diarylike.repository.DiaryLikeRepository;
import com.moa.domain.diary.diarylike.service.DiaryLikeService;
import com.moa.domain.member.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DairyLikeServiceImpl implements DiaryLikeService {

    private final DiaryLikeRepository diaryLikeRepository;
    private final DiaryLikeMapper diaryLikeMapper;

    @Override
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

    @Override
    public DiaryLikeDto.GetDiaryLikesResponse getDiaryLikes(Diary diary) {
        List<DiaryLike> diaryLikes = diaryLikeRepository.findDiaryLikesByDiary(diary);

        return diaryLikeMapper.toGetDiaryLikesResponse(diaryLikes);
    }

}
