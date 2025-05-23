package com.moa.domain.diary.diarycomment.repository.querydsl;

import com.moa.domain.diary.diarycomment.dto.query.UserCommentDto;
import com.moa.domain.diary.diarycomment.entity.DiaryComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DiaryCommentRepositoryCustom {

    DiaryComment findCommentWithActiveChildrenComments(UUID commentId);

    Page<UserCommentDto> findUserComments(UUID userId, Pageable pageable);

}
