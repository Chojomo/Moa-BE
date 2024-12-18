package com.moa.domain.diary.diarycomment.service;

import com.moa.domain.diary.diarycomment.dto.DiaryCommentDto;

import java.util.UUID;

public interface DiaryCommentService {

    DiaryCommentDto.CreateCommentResponse createComment(UUID diaryId, DiaryCommentDto.CreateCommentRequest request);

    void createReply(UUID diaryId, UUID commentId, DiaryCommentDto.CreateReplyRequest request);

    void toggleLikeOnDiary(UUID commentId);

}
