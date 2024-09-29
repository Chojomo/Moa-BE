package com.moa.domain.diary.diarylike.service;

import com.moa.domain.diary.diary.entity.Diary;
import com.moa.domain.member.entity.User;

public interface DiaryLikeService {

    void toggleLikeOnDiary(User user, Diary diary);

}
