package com.moa.domain.diary.diarycomment.service.impl;

import com.moa.domain.diary.diary.entity.Diary;
import com.moa.domain.diary.diary.service.DiaryService;
import com.moa.domain.diary.diarycomment.dto.DiaryCommentDto;
import com.moa.domain.diary.diarycomment.dto.query.UserCommentDto;
import com.moa.domain.diary.diarycomment.entity.DiaryComment;
import com.moa.domain.diary.diarycomment.exception.DiaryCommentException;
import com.moa.domain.diary.diarycomment.exception.DiaryCommentExceptionCode;
import com.moa.domain.diary.diarycomment.mapper.DiaryCommentMapper;
import com.moa.domain.diary.diarycomment.repository.DiaryCommentRepository;
import com.moa.domain.diary.diarycomment.service.DiaryCommentService;
import com.moa.domain.diary.diarycommentlike.service.DiaryCommentLikeService;
import com.moa.domain.member.entity.User;
import com.moa.domain.member.service.UserService;
import com.moa.global.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DiaryCommentServiceImpl implements DiaryCommentService {

    private final AuthService authService;
    private final DiaryService diaryService;
    private final UserService userService;
    private final DiaryCommentRepository diaryCommentRepository;
    private final DiaryCommentLikeService diaryCommentLikeService;
    private final DiaryCommentMapper diaryCommentMapper;

    @Override
    public DiaryCommentDto.CreateCommentResponse createComment(UUID diaryId, DiaryCommentDto.CreateCommentRequest request) {
        User loginUser = authService.getLoginUser();

        Diary diary = diaryService.findDiaryOrThrow(diaryId);

        diary.incrementCommentCount();

        DiaryComment comment = DiaryComment.createComment(diary, loginUser, request.getCommentContents());

        DiaryComment savedComment = diaryCommentRepository.save(comment);

        return diaryCommentMapper.commentToCreateCommentResponse(savedComment);
    }

    @Override
    public DiaryCommentDto.CreateReplyResponse createReply(UUID diaryId, UUID commentId, DiaryCommentDto.CreateReplyRequest request) {
        User loginUser = authService.getLoginUser();

        Diary diary = diaryService.findDiaryOrThrow(diaryId);

        diary.incrementCommentCount();

        DiaryComment comment = findCommentOrThrow(commentId);

        DiaryComment reply = DiaryComment.createReply(diary, comment, loginUser, request.getReplyContents());

        DiaryComment savedReply = diaryCommentRepository.save(reply);

        return diaryCommentMapper.commentToCreateReplyResponse(savedReply);
    }

    @Override
    public void toggleLikeOnDiary(UUID commentId) {
        User loginUser = authService.getLoginUser();

        DiaryComment comment = findCommentOrThrow(commentId);

        boolean isLiked = diaryCommentLikeService.toggleLikeOnComment(loginUser, comment);

        if (isLiked) {
            comment.incrementLikeCount();
        } else {
            comment.decrementLikeCount();
        }

        diaryCommentRepository.save(comment);
    }

    @Override
    public DiaryCommentDto.UpdateCommentResponse updateComment(UUID diaryId, UUID commentId, DiaryCommentDto.UpdateCommentRequest request) {
        User loginUser = authService.getLoginUser();

        diaryService.findDiaryOrThrow(diaryId);

        DiaryComment comment = findCommentOrThrow(commentId);

        checkCommentOwnership(comment, loginUser);

        comment.updateCommentContents(request.getCommentContents());


        return diaryCommentMapper.commentToUpdateCommentResponse(comment);
    }

    @Override
    public DiaryCommentDto.UpdateReplyResponse updateReply(UUID diaryId, UUID replyId, DiaryCommentDto.UpdateReplyRequest request) {
        User loginUser = authService.getLoginUser();

        diaryService.findDiaryOrThrow(diaryId);

        DiaryComment reply = findCommentOrThrow(replyId);

        checkCommentOwnership(reply, loginUser);

        reply.updateCommentContents(request.getReplyContents());

        return diaryCommentMapper.replyToUpdateReplyResponse(reply);
    }

    @Override
    public void deleteComment(UUID diaryId, UUID commentId) {
        User loginUser = authService.getLoginUser();

        Diary diary = diaryService.findDiaryOrThrow(diaryId);

        diary.decrementCommentCount();

        DiaryComment comment = diaryCommentRepository.findCommentWithActiveChildrenComments(commentId);

        if (comment == null) {
            throw new DiaryCommentException(DiaryCommentExceptionCode.COMMENT_NOT_EXISTS);
        }

        validateCommentNotDeleted(comment);

        decrementChildCommentsCount(comment, diary);

        checkCommentOwnership(comment, loginUser);

        comment.deleteComment();
    }

    @Override
    public Page<UserCommentDto> getUserComments(UUID userId, Integer pageNumber, Integer pageSize) {
        userService.findUserOrThrow(userId);

        return diaryCommentRepository.findUserComments(userId, pageNumber, pageSize);
    }

    private void validateCommentNotDeleted(DiaryComment comment) {
        if (comment.getDeletedAt() != null) {
            throw new DiaryCommentException(DiaryCommentExceptionCode.COMMENT_ALREADY_DELETED);
        }
    }

    private void decrementChildCommentsCount(DiaryComment comment, Diary diary) {
        if (comment.getParentComment() == null && !comment.getChildrenComments().isEmpty()) {
            diary.decrementReplyCount(comment.getChildrenComments().size());
        }
    }

    public DiaryComment findCommentOrThrow(UUID commentId) {
        Optional<DiaryComment> optionalDiaryComment = diaryCommentRepository.findById(commentId);
        return optionalDiaryComment.orElseThrow();
    }

    public void checkCommentOwnership(DiaryComment comment, User user) {
        if (!comment.getUser().getUserId().equals(user.getUserId())) {
            log.info("댓글 수정 권한이 없습니다.");
        }
    }

}
