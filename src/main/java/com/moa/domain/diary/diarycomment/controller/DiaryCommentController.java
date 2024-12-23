package com.moa.domain.diary.diarycomment.controller;

import com.moa.domain.diary.diarycomment.dto.DiaryCommentDto;
import com.moa.domain.diary.diarycomment.service.DiaryCommentService;
import com.moa.global.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/diaries")
public class DiaryCommentController {

    private final DiaryCommentService diaryCommentService;

    @PostMapping("/{diaryId}/comments")
    public ResponseEntity<SingleResponseDto<DiaryCommentDto.CreateCommentResponse>> createComment(
            @PathVariable UUID diaryId, @RequestBody DiaryCommentDto.CreateCommentRequest request) {
        DiaryCommentDto.CreateCommentResponse response = diaryCommentService.createComment(diaryId, request);
        return new ResponseEntity<>(new SingleResponseDto<>(HttpStatus.CREATED.value(), response), HttpStatus.CREATED);
    }

    @PostMapping("/{diaryId}/comments/{commentId}/replies")
    public ResponseEntity<SingleResponseDto<DiaryCommentDto.CreateReplyResponse>> createReply(
            @PathVariable UUID diaryId, @PathVariable UUID commentId, @RequestBody DiaryCommentDto.CreateReplyRequest request) {
        DiaryCommentDto.CreateReplyResponse response = diaryCommentService.createReply(diaryId, commentId, request);
        return new ResponseEntity<>(new SingleResponseDto<>(HttpStatus.CREATED.value(), response), HttpStatus.CREATED);
    }

    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<SingleResponseDto> toggleLikeOnComment(@PathVariable UUID commentId) {
        diaryCommentService.toggleLikeOnDiary(commentId);
        return new ResponseEntity<>(new SingleResponseDto<>(HttpStatus.OK.value()), HttpStatus.OK);
    }

    @PatchMapping("/{diaryId}/comments/{commentId}")
    public ResponseEntity<SingleResponseDto<DiaryCommentDto.UpdateCommentResponse>> createComment(
            @PathVariable UUID diaryId, @PathVariable UUID commentId, @RequestBody DiaryCommentDto.UpdateCommentRequest request) {
        DiaryCommentDto.UpdateCommentResponse response = diaryCommentService.updateComment(diaryId, commentId, request);
        return new ResponseEntity<>(new SingleResponseDto<>(HttpStatus.CREATED.value(), response), HttpStatus.CREATED);
    }

}

