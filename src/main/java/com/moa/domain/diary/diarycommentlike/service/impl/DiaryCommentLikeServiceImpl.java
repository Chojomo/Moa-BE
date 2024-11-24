package com.moa.domain.diary.diarycommentlike.service.impl;

import com.moa.domain.diary.diarycomment.entity.DiaryComment;
import com.moa.domain.diary.diarycommentlike.entity.DiaryCommentLike;
import com.moa.domain.diary.diarycommentlike.repository.DiaryCommentLikeRepository;
import com.moa.domain.diary.diarycommentlike.service.DiaryCommentLikeService;
import com.moa.domain.member.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DiaryCommentLikeServiceImpl implements DiaryCommentLikeService {

    private final DiaryCommentLikeRepository diaryCommentLikeRepository;

    @Override
    public boolean toggleLikeOnComment(User user, DiaryComment diaryComment) {
        Optional<DiaryCommentLike> existingLike = diaryCommentLikeRepository.findDiaryCommentLikeByUserAndDiaryComment(user, diaryComment);

        existingLike.ifPresentOrElse(
                diaryCommentLikeRepository::delete,
                () -> diaryCommentLikeRepository.save(
                        DiaryCommentLike.builder()
                                .user(user)
                                .diaryComment(diaryComment)
                                .build())
        );

        return existingLike.isEmpty();
    }

}
