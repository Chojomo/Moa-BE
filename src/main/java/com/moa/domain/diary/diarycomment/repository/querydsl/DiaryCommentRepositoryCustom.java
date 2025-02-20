package com.moa.domain.diary.diarycomment.repository.querydsl;

import com.moa.domain.diary.diarycomment.entity.DiaryComment;

import java.util.UUID;

public interface DiaryCommentRepositoryCustom {

    DiaryComment findCommentWithActiveChildrenComments(UUID commentId);

}
