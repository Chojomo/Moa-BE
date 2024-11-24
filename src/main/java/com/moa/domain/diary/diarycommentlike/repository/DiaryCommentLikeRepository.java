package com.moa.domain.diary.diarycommentlike.repository;

import com.moa.domain.diary.diarycomment.entity.DiaryComment;
import com.moa.domain.diary.diarycommentlike.entity.DiaryCommentLike;
import com.moa.domain.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiaryCommentLikeRepository extends JpaRepository<DiaryCommentLike, UUID> {

    Optional<DiaryCommentLike> findDiaryCommentLikeByUserAndDiaryComment(User user, DiaryComment diaryComment);

}
