package com.moa.domain.diary.diary.mapper;

import com.moa.domain.diary.diary.dto.DiaryDto;
import com.moa.domain.diary.diary.entity.Diary;
import com.moa.domain.diary.diarycomment.entity.DiaryComment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DiaryMapper {

    public DiaryDto.GetDiaryResponse diaryToGetDiaryResponse(Diary diary, List<DiaryComment> commentsAndReplies, Boolean isLiked) {
        List<DiaryDto.CommentData> commentDataList = commentsAndReplies.stream()
                .map(this::convertToCommentData)
                .toList();

        return DiaryDto.GetDiaryResponse.builder()
                .diaryId(diary.getDiaryId())
                .diaryAuthorId(diary.getUser().getUserId())
                .diaryAuthorNickname(diary.getUser().getUserNickname())
                .diaryAuthorProfileImage(diary.getUser().getUserProfileImage())
                .diaryTitle(diary.getDiaryTitle())
                .diaryContents(diary.getDiaryContents())
                .diaryThumbnail(diary.getDiaryThumbnail())
                .isDiaryPublic(diary.getIsDairyPublic())
                .isLiked(isLiked)
                .diaryPublishedAt(diary.getPublishedAt())
                .viewCount(diary.getViewCount())
                .likeCount(diary.getLikeCount())
                .commentCount(diary.getCommentCount())
                .comment(DiaryDto.Comments.builder()
                        .comments(commentDataList)
                        .build())
                .build();
    }

    public DiaryDto.DiaryPreview diaryTodiaryPreview(Diary diary) {
        return DiaryDto.DiaryPreview.builder()
                .diaryId(diary.getDiaryId())
                .diaryAuthorId(diary.getUser().getUserId())
                .diaryAuthorNickname(diary.getUser().getUserNickname())
                .diaryThumbnail(diary.getDiaryThumbnail())
                .diaryTitle(diary.getDiaryTitle())
                .diaryContents(diary.getDiaryContents())
                .diaryPublishedAt(LocalDate.from(diary.getPublishedAt()))
                .viewCount(diary.getViewCount())
                .likeCount(diary.getLikeCount())
                .commentCount(diary.getCommentCount())
                .build();
    }


    private DiaryDto.CommentData convertToCommentData(DiaryComment diaryComment) {
        return DiaryDto.CommentData.builder()
                .commentAuthorId(diaryComment.getUser().getUserId())
                .diaryAuthorNickname(diaryComment.getUser().getUserNickname())
                .diaryAuthorProfileImage(diaryComment.getUser().getUserProfileImage())
                .createdAt(diaryComment.getCreatedAt())
                .likeCount(diaryComment.getLikeCount())
                .commentContents(diaryComment.getCommentContents())
                .replies(convertToReplyDataList(diaryComment.getChildrenComments()))
                .build();
    }

    private List<DiaryDto.ReplyData> convertToReplyDataList(List<DiaryComment> childrenComments) {
        return childrenComments.stream()
                .map(child -> DiaryDto.ReplyData.builder()
                        .replyAuthorId(child.getUser().getUserId())
                        .replyAuthorNickname(child.getUser().getUserNickname())
                        .replyAuthorProfileImage(child.getUser().getUserProfileImage())
                        .createdAt(child.getCreatedAt())
                        .likeCount(child.getLikeCount())
                        .replyContents(child.getCommentContents())
                        .build())
                .toList();
    }

}
