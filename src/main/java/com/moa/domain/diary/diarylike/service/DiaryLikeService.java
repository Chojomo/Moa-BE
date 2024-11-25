package com.moa.domain.diary.diarylike.service;

import com.moa.domain.diary.diary.entity.Diary;
import com.moa.domain.diary.diarylike.dto.DiaryLikeDto;
import com.moa.domain.member.entity.User;

public interface DiaryLikeService {

    boolean toggleLikeOnDiary(User user, Diary diary);

    DiaryLikeDto.GetDiaryLikesResponse getDiaryLikes(Diary diary);

    Boolean isDiaryLiked(Diary diary, User user);

}
