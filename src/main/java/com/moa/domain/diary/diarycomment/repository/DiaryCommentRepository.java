package com.moa.domain.diary.diarycomment.repository;

import com.moa.domain.diary.diarycomment.entity.DiaryComment;
import com.moa.domain.diary.diarycomment.repository.querydsl.DiaryCommentRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DiaryCommentRepository extends JpaRepository<DiaryComment, UUID>, DiaryCommentRepositoryCustom {

    @Query("SELECT d FROM DIARY_COMMENT d " +
            "LEFT JOIN FETCH d.childrenComments c " +
            "LEFT JOIN FETCH d.user u " +
            "WHERE d.diary.diaryId = :diaryId " +
            "AND d.parentComment IS NULL " +
            "AND d.deletedAt IS NULL " +
            "ORDER BY d.createdAt")
    List<DiaryComment> findCommentsByDiaryId(UUID diaryId);

}
