package com.moa.domain.diary.diarycomment.repository;

import com.moa.domain.diary.diarycomment.entity.DiaryComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DiaryCommentRepository extends JpaRepository<DiaryComment, UUID> {

}
