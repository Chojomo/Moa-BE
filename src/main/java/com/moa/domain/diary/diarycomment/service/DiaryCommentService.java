package com.moa.domain.diary.diarycomment.service;

import com.moa.domain.diary.diarycomment.dto.DiaryCommentDto;
import com.moa.domain.diary.diarycomment.dto.query.UserCommentDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface DiaryCommentService {

    DiaryCommentDto.CreateCommentResponse createComment(UUID diaryId, DiaryCommentDto.CreateCommentRequest request);

    DiaryCommentDto.CreateReplyResponse createReply(UUID diaryId, UUID commentId, DiaryCommentDto.CreateReplyRequest request);

    void toggleLikeOnDiary(UUID commentId);

    DiaryCommentDto.UpdateCommentResponse updateComment(UUID diaryId, UUID commentId, DiaryCommentDto.UpdateCommentRequest request);

    DiaryCommentDto.UpdateReplyResponse updateReply(UUID diaryId, UUID replyId, DiaryCommentDto.UpdateReplyRequest request);

    void deleteComment(UUID diaryId, UUID commentId);

    Page<UserCommentDto> getUserComments(UUID userId, Integer pageNumber, Integer pageSize);

}
