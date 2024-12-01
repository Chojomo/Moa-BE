package com.moa.domain.diary.diarycommentlike.repository;

import com.moa.domain.diary.diarycomment.entity.DiaryComment;
import com.moa.domain.diary.diarycommentlike.entity.DiaryCommentLike;
import com.moa.domain.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiaryCommentLikeRepository extends JpaRepository<DiaryCommentLike, UUID> {

    Optional<DiaryCommentLike> findDiaryCommentLikeByUserAndDiaryComment(User user, DiaryComment diaryComment);

    @Query("SELECT d.diaryComment.diaryCommentId FROM DIARY_COMMENT_LIKE d WHERE d.user = :user AND d.diaryComment.diaryCommentId IN :commentIds")
    List<UUID> findDiaryCommentLikesByUser(User user, List<UUID> commentIds);

}
