package com.moa.domain.diary.diary.controller;

import com.moa.domain.diary.diary.dto.DiaryDto;
import com.moa.domain.diary.diary.dto.query.UserDiaryDto;
import com.moa.domain.diary.diary.dto.query.UserLikedDiaryDto;
import com.moa.domain.diary.diary.service.DiaryService;
import com.moa.domain.diary.diarylike.dto.DiaryLikeDto;
import com.moa.global.dto.ApiResponse;
import com.moa.global.dto.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/diaries")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/initialize")
    public ResponseEntity<ApiResponse<DiaryDto.InitializeDiaryResponse>> initializeDiary() {
        DiaryDto.InitializeDiaryResponse response = diaryService.initializeDiary();

        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PostMapping("/{diaryId}/image")
    public ResponseEntity<ApiResponse<DiaryDto.CreateDiaryImageResponse>> createDiaryImage(
            @PathVariable UUID diaryId,
            @RequestPart(value = "image") MultipartFile multipartFile
    ) throws IOException {
        DiaryDto.CreateDiaryImageResponse response = diaryService.createDiaryImage(diaryId, multipartFile);

        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Integer>> updateDiary(@RequestBody DiaryDto.UpdateDiaryRequest req) {
        diaryService.updateDiary(req);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created());
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<ApiResponse<DiaryDto.GetDiaryResponse>> getDiaryDetails(@PathVariable UUID diaryId) {
        DiaryDto.GetDiaryResponse response = diaryService.getDiaryDetails(diaryId);

        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PostMapping("/publish")
    public ResponseEntity<ApiResponse<Integer>> publishDiary(@RequestBody DiaryDto.PublishDiaryRequest req) {
        diaryService.publishDiary(req);

        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/list")
    public ResponseEntity<MultiResponseDto> getDiaryList(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "publishedAt") String sortType
    ) {
        MultiResponseDto<?> diaryList = diaryService.getDiaryList(pageNumber, pageSize, sortType);

        return new ResponseEntity<>(diaryList, HttpStatus.OK);
    }

    @PostMapping("/like/{diaryId}")
    public ResponseEntity<ApiResponse<Integer>> toggleLikeOnDiary(@PathVariable UUID diaryId) {
        diaryService.toggleLikeOnDiary(diaryId);

        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/like/{diaryId}")
    public ResponseEntity<ApiResponse<DiaryLikeDto.GetDiaryLikesResponse>> getDiaryLikes(@PathVariable UUID diaryId) {
        DiaryLikeDto.GetDiaryLikesResponse response = diaryService.getDiaryLikes(diaryId);

        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PostMapping("/{diaryId}/thumbnail")
    public ResponseEntity<ApiResponse<DiaryDto.UploadThumbnailResponse>> uploadThumbnail(
            @PathVariable UUID diaryId,
            @RequestPart(value = "image") MultipartFile multipartFile
    ) throws IOException {
        DiaryDto.UploadThumbnailResponse response = diaryService.uploadThumbnail(diaryId, multipartFile);

        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<ApiResponse<Integer>> deleteDiary(@PathVariable UUID diaryId) {
        diaryService.deleteDiary(diaryId);

        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<MultiResponseDto<?>> getUserDiaryList(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        Page<UserDiaryDto> response = diaryService.getUserDiaryList(userId, pageNumber, pageSize);

        return new ResponseEntity<>(new MultiResponseDto<>(HttpStatus.OK.value(), response.getContent(), response), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/likes")
    public ResponseEntity<MultiResponseDto<?>> getUserLikedDiaries(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        Page<UserLikedDiaryDto> response = diaryService.getUserLikedDiaries(userId, pageNumber, pageSize);

        return new ResponseEntity<>(new MultiResponseDto<>(HttpStatus.OK.value(), response.getContent(), response), HttpStatus.OK);
    }

}
