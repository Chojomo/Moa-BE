package com.moa.domain.diary.diary.controller;

import com.moa.domain.diary.diary.dto.DiaryDto;
import com.moa.domain.diary.diary.service.DiaryService;
import com.moa.domain.diary.diarylike.dto.DiaryLikeDto;
import com.moa.global.dto.MultiResponseDto;
import com.moa.global.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<SingleResponseDto<DiaryDto.InitializeDiaryResponse>> initializeDiary() {
        return new ResponseEntity<>(new SingleResponseDto<>(HttpStatus.OK.value(), diaryService.initializeDiary()), HttpStatus.OK);
    }

    @PostMapping("/{diaryId}/image")
    public ResponseEntity<SingleResponseDto<DiaryDto.CreateDiaryImageResponse>> createDiaryImage(@PathVariable UUID diaryId,
                                                                                                 @RequestPart(value = "image") MultipartFile multipartFile) throws IOException {
        DiaryDto.CreateDiaryImageResponse diaryImage = diaryService.createDiaryImage(diaryId, multipartFile);
        return new ResponseEntity<>(new SingleResponseDto<>(HttpStatus.OK.value(), diaryImage), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<SingleResponseDto<Integer>> updateDiary(@RequestBody DiaryDto.UpdateDiaryRequest req) {
        diaryService.updateDiary(req);
        return new ResponseEntity<>(new SingleResponseDto<>(HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<SingleResponseDto<DiaryDto.GetDiaryResponse>> getDiaryDetails(@PathVariable UUID diaryId) {
        return new ResponseEntity<>(new SingleResponseDto<>(HttpStatus.OK.value(), diaryService.getDiaryDetails(diaryId)), HttpStatus.OK);
    }

    @PostMapping("/publish")
    public ResponseEntity<SingleResponseDto<Integer>> publishDiary(@RequestBody DiaryDto.PublishDiaryRequest req) {
        diaryService.publishDiary(req);
        return new ResponseEntity<>(new SingleResponseDto<>(HttpStatus.OK.value()), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<MultiResponseDto> getDiaryList(@RequestParam(defaultValue = "0") Integer pageNumber,
                                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                                         @RequestParam(defaultValue = "publishedAt") String sortType) {
        MultiResponseDto<?> diaryList = diaryService.getDiaryList(pageNumber, pageSize, sortType);

        return new ResponseEntity<>(diaryList, HttpStatus.OK);
    }

    @PostMapping("/like/{diaryId}")
    public ResponseEntity<SingleResponseDto> toggleLikeOnDiary(@PathVariable UUID diaryId) {
        diaryService.toggleLikeOnDiary(diaryId);
        return new ResponseEntity<>(new SingleResponseDto<>(HttpStatus.OK.value()), HttpStatus.OK);
    }

    @GetMapping("/like/{diaryId}")
    public ResponseEntity<SingleResponseDto<DiaryLikeDto.GetDiaryLikesResponse>> getDiaryLikes(@PathVariable UUID diaryId) {
        return new ResponseEntity<>(new SingleResponseDto<>(HttpStatus.OK.value(), diaryService.getDiaryLikes(diaryId)), HttpStatus.OK);
    }

    @PostMapping("/{diaryId}/thumbnail")
    public ResponseEntity<SingleResponseDto<DiaryDto.UploadThumbnailResponse>> uploadThumbnail(@PathVariable UUID diaryId,
                                                                                                 @RequestPart(value = "image") MultipartFile multipartFile) throws IOException {
        return new ResponseEntity<>(new SingleResponseDto<>(HttpStatus.OK.value(), diaryService.uploadThumbnail(diaryId, multipartFile)), HttpStatus.OK);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<SingleResponseDto<Integer>> deleteDiary(@PathVariable UUID diaryId) {
        diaryService.deleteDiary(diaryId);
        return new ResponseEntity<>(new SingleResponseDto<>(HttpStatus.OK.value()), HttpStatus.OK);
    }

}
