package com.moa.domain.diary.controller;

import com.moa.domain.diary.dto.DiaryDto;
import com.moa.domain.diary.service.DiaryService;
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
        return new ResponseEntity<>(new SingleResponseDto<>(diaryService.initializeDiary()), HttpStatus.OK);
    }

    @PostMapping("/{diaryId}/image")
    public ResponseEntity<SingleResponseDto<DiaryDto.CreateDiaryImageResponse>> createDiaryImage(@PathVariable UUID diaryId,
                                                                                                 @RequestPart(value = "image") MultipartFile multipartFile) throws IOException {
        DiaryDto.CreateDiaryImageResponse diaryImage = diaryService.createDiaryImage(diaryId, multipartFile);
        return new ResponseEntity<>(new SingleResponseDto<>(diaryImage), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> updateDiary(@RequestBody DiaryDto.UpdateDiaryRequest req) {
        diaryService.updateDiary(req);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<SingleResponseDto<DiaryDto.GetDiaryResponse>> getDiaryDetails(@PathVariable UUID diaryId) {
        return new ResponseEntity<>(new SingleResponseDto<>(diaryService.getDiaryDetails(diaryId)), HttpStatus.OK);
    }

    @PostMapping("/publish")
    public ResponseEntity<Void> publishDiary(@RequestBody DiaryDto.PublishDiaryRequest req) {
        diaryService.publishDiary(req);
        return ResponseEntity.ok().build();
    }

}
