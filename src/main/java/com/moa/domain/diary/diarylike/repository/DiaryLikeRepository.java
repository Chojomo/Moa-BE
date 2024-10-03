package com.moa.domain.diary.diarylike.repository;

import com.moa.domain.diary.diary.entity.Diary;
import com.moa.domain.diary.diarylike.entity.DiaryLike;
import com.moa.domain.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiaryLikeRepository extends JpaRepository<DiaryLike, UUID> {

        Optional<DiaryLike> findDiaryLikeByUserAndDiary(User user, Diary diary);

        List<DiaryLike> findDiaryLikesByDiary(Diary diary);

}
