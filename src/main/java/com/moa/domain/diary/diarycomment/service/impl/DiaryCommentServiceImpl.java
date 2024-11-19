package com.moa.domain.diary.diarycomment.service.impl;

import com.moa.domain.diary.diary.entity.Diary;
import com.moa.domain.diary.diary.service.DiaryService;
import com.moa.domain.diary.diarycomment.dto.DiaryCommentDto;
import com.moa.domain.diary.diarycomment.entity.DiaryComment;
import com.moa.domain.diary.diarycomment.repository.DiaryCommentRepository;
import com.moa.domain.diary.diarycomment.service.DiaryCommentService;
import com.moa.domain.member.entity.User;
import com.moa.global.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final DiaryCommentRepository diaryCommentRepository;

    @Override
    public void createComment(UUID diaryId, DiaryCommentDto.CreateCommentRequest request) {
        User loginUser = authService.getLoginUser();

        Diary diary = diaryService.findDiaryOrThrow(diaryId);

        DiaryComment comment = DiaryComment.createComment(diary, loginUser, request.getCommentContents());

        diaryCommentRepository.save(comment);
    }

    @Override
    public void createReply(UUID diaryId, UUID commentId, DiaryCommentDto.CreateReplyRequest request) {
        User loginUser = authService.getLoginUser();

        Diary diary = diaryService.findDiaryOrThrow(diaryId);

        DiaryComment comment = findCommentOrThrow(commentId);

        DiaryComment reply = DiaryComment.createReply(diary, comment, loginUser, request.getReplyContents());

        diaryCommentRepository.save(reply);
    }

    public DiaryComment findCommentOrThrow(UUID commentId) {
        Optional<DiaryComment> optionalDiaryComment = diaryCommentRepository.findById(commentId);
        return optionalDiaryComment.orElseThrow();
    }

}
