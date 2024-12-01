package com.moa.domain.diary.diary.mapper;

import com.moa.domain.diary.diary.dto.DiaryDto;
import com.moa.domain.diary.diary.entity.Diary;
import com.moa.domain.diary.diarycomment.entity.DiaryComment;
import com.moa.domain.member.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class DiaryMapper {

    public DiaryDto.GetDiaryResponse diaryToGetDiaryResponse(User loginUser, Diary diary, List<DiaryComment> commentsAndReplies, Boolean isLiked, Map<UUID, Boolean> loginUserCommentLikeData) {
        List<DiaryDto.CommentData> commentDataList = commentsAndReplies.stream()
                .map((DiaryComment diaryComment) -> convertToCommentData(diaryComment, loginUser, loginUserCommentLikeData))
                .toList();

        return DiaryDto.GetDiaryResponse.builder()
                .diaryId(diary.getDiaryId())
                .isDiaryOwner(loginUser != null && diary.getUser().getUserId().equals(loginUser.getUserId()))
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


    private DiaryDto.CommentData convertToCommentData(DiaryComment diaryComment, User loginUser, Map<UUID, Boolean> loginUserCommentLikeData) {
        return DiaryDto.CommentData.builder()
                .commentId(diaryComment.getDiaryCommentId())
                .isCommentOwner(loginUser != null && diaryComment.getUser().getUserId().equals(loginUser.getUserId()))
                .commentAuthorId(diaryComment.getUser().getUserId())
                .diaryAuthorNickname(diaryComment.getUser().getUserNickname())
                .diaryAuthorProfileImage(diaryComment.getUser().getUserProfileImage())
                .createdAt(diaryComment.getCreatedAt())
                .isLiked(loginUserCommentLikeData.getOrDefault(diaryComment.getDiaryCommentId(), false))
                .likeCount(diaryComment.getLikeCount())
                .commentContents(diaryComment.getCommentContents())
                .replies(convertToReplyDataList(diaryComment.getChildrenComments(), loginUser, loginUserCommentLikeData))
                .build();
    }

    private List<DiaryDto.ReplyData> convertToReplyDataList(List<DiaryComment> childrenComments, User loginUser, Map<UUID, Boolean> loginUserCommentLikeData) {
        return childrenComments.stream()
                .map(reply -> DiaryDto.ReplyData.builder()
                        .replyId(reply.getDiaryCommentId())
                        .isReplyOwner(loginUser != null && reply.getUser().getUserId().equals(loginUser.getUserId()))
                        .replyAuthorId(reply.getUser().getUserId())
                        .replyAuthorNickname(reply.getUser().getUserNickname())
                        .replyAuthorProfileImage(reply.getUser().getUserProfileImage())
                        .createdAt(reply.getCreatedAt())
                        .isLiked(loginUserCommentLikeData.getOrDefault(reply.getDiaryCommentId(), false))
                        .likeCount(reply.getLikeCount())
                        .replyContents(reply.getCommentContents())
                        .build())
                .toList();
    }

}
