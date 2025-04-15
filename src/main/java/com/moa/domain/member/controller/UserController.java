package com.moa.domain.member.controller;

import com.moa.domain.diary.diary.dto.DiaryDto;
import com.moa.domain.member.dto.UserDto;
import com.moa.domain.member.service.UserService;
import com.moa.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/my-page/{userId}")
    public ResponseEntity<ApiResponse<UserDto.GetUserMyPageResponse>> getUserMyPage(@PathVariable UUID userId) {
        UserDto.GetUserMyPageResponse response = userService.getUserMyPage(userId);

        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @PutMapping("/profile-image")
    public ResponseEntity<ApiResponse<DiaryDto.CreateDiaryImageResponse>> updateProfileImage(@RequestPart(value = "image") MultipartFile multipartFile) throws IOException {
        userService.updateProfileImage(multipartFile);

        return ResponseEntity.ok(ApiResponse.ok());
    }

}
