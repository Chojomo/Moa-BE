package com.moa.domain.diary.diarycomment.mapper;

import com.moa.domain.diary.diarycomment.dto.DiaryCommentDto;
import com.moa.domain.diary.diarycomment.entity.DiaryComment;
import org.springframework.stereotype.Component;

@Component
public class DiaryCommentMapper {

    public DiaryCommentDto.CreateCommentResponse commentToCreateCommentResponse(DiaryComment comment) {
        return DiaryCommentDto.CreateCommentResponse.builder()
                .commentId(comment.getDiaryCommentId())
                .isCommentOwner(true)
                .commentAuthorId(comment.getUser().getUserId())
                .commentAuthorNickname(comment.getUser().getUserNickname())
                .commentAuthorProfileImage(comment.getUser().getUserProfileImage())
                .createdAt(comment.getCreatedAt())
                .commentContents(comment.getCommentContents())
                .build();
    }

    public DiaryCommentDto.CreateReplyResponse commentToCreateReplyResponse(DiaryComment reply) {
        return DiaryCommentDto.CreateReplyResponse.builder()
                .replyId(reply.getDiaryCommentId())
                .isReplyOwner(true)
                .replyAuthorId(reply.getUser().getUserId())
                .replyAuthorNickname(reply.getUser().getUserNickname())
                .replyAuthorProfileImage(reply.getUser().getUserProfileImage())
                .createdAt(reply.getCreatedAt())
                .replyContents(reply.getCommentContents())
                .build();
    }

    public DiaryCommentDto.UpdateCommentResponse commentToUpdateCommentResponse(DiaryComment comment) {
        return DiaryCommentDto.UpdateCommentResponse.builder()
                .commentId(comment.getDiaryCommentId())
                .isCommentOwner(true)
                .commentAuthorId(comment.getUser().getUserId())
                .commentAuthorNickname(comment.getUser().getUserNickname())
                .commentAuthorProfileImage(comment.getUser().getUserProfileImage())
                .createdAt(comment.getCreatedAt())
                .commentContents(comment.getCommentContents())
                .build();
    }

    public DiaryCommentDto.UpdateReplyResponse replyToUpdateReplyResponse(DiaryComment reply) {
        return DiaryCommentDto.UpdateReplyResponse.builder()
                .replyId(reply.getDiaryCommentId())
                .isReplyOwner(true)
                .replyAuthorId(reply.getUser().getUserId())
                .replyAuthorNickname(reply.getUser().getUserNickname())
                .replyAuthorProfileImage(reply.getUser().getUserProfileImage())
                .createdAt(reply.getCreatedAt())
                .replyContents(reply.getCommentContents())
                .build();
    }

}
