package com.moa.domain.diary.diarycomment.controller;

import com.moa.domain.diary.diarycomment.dto.DiaryCommentDto;
import com.moa.domain.diary.diarycomment.dto.query.UserCommentDto;
import com.moa.domain.diary.diarycomment.service.DiaryCommentService;
import com.moa.global.dto.ApiResponse;
import com.moa.global.dto.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/diaries")
public class DiaryCommentController {

    private final DiaryCommentService diaryCommentService;

    @PostMapping("/{diaryId}/comments")
    public ResponseEntity<ApiResponse<DiaryCommentDto.CreateCommentResponse>> createComment(
            @PathVariable UUID diaryId,
            @RequestBody DiaryCommentDto.CreateCommentRequest request
    ) {
        DiaryCommentDto.CreateCommentResponse response = diaryCommentService.createComment(diaryId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(response));
    }

    @PostMapping("/{diaryId}/comments/{commentId}/replies")
    public ResponseEntity<ApiResponse<DiaryCommentDto.CreateReplyResponse>> createReply(
            @PathVariable UUID diaryId,
            @PathVariable UUID commentId,
            @RequestBody DiaryCommentDto.CreateReplyRequest request
    ) {
        DiaryCommentDto.CreateReplyResponse response = diaryCommentService.createReply(diaryId, commentId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(response));
    }

    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<ApiResponse<Integer>> toggleLikeOnComment(@PathVariable UUID commentId) {
        diaryCommentService.toggleLikeOnDiary(commentId);

        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PatchMapping("/{diaryId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<DiaryCommentDto.UpdateCommentResponse>> updateComment(
            @PathVariable UUID diaryId,
            @PathVariable UUID commentId,
            @RequestBody DiaryCommentDto.UpdateCommentRequest request
    ) {
        DiaryCommentDto.UpdateCommentResponse response = diaryCommentService.updateComment(diaryId, commentId, request);

        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PatchMapping("/{diaryId}/reply/{replyId}")
    public ResponseEntity<ApiResponse<DiaryCommentDto.UpdateReplyResponse>> updateReply(
            @PathVariable UUID diaryId,
            @PathVariable UUID replyId,
            @RequestBody DiaryCommentDto.UpdateReplyRequest request
    ) {
        DiaryCommentDto.UpdateReplyResponse response = diaryCommentService.updateReply(diaryId, replyId, request);

        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @DeleteMapping("/{diaryId}/comments/{commentId}")
    public ResponseEntity<ApiResponse<Integer>> deleteComment(
            @PathVariable UUID diaryId,
            @PathVariable UUID commentId
    ) {
        diaryCommentService.deleteComment(diaryId, commentId);

        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/comments/users/{userId}")
    public ResponseEntity<MultiResponseDto<List<UserCommentDto>>> getUserDiaryComments(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        Page<UserCommentDto> response = diaryCommentService.getUserComments(userId, pageNumber, pageSize);

        return ResponseEntity.ok(new MultiResponseDto<>(HttpStatus.OK.value(), response.getContent(), response));
    }

}

