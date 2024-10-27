package com.moa.domain.member.controller;

import com.moa.domain.diary.diary.dto.DiaryDto;
import com.moa.domain.member.dto.UserDto;
import com.moa.domain.member.service.UserService;
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
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/my-page/{userId}")
    public ResponseEntity<SingleResponseDto<UserDto.GetUserMyPageResponse>> getUserMyPage(@PathVariable UUID userId) {

        UserDto.GetUserMyPageResponse response = userService.getUserMyPage(userId);

        return new ResponseEntity<>(new SingleResponseDto<>(HttpStatus.OK.value(), response), HttpStatus.OK);
    }

    @PutMapping("/profile-image")
    public ResponseEntity<SingleResponseDto<DiaryDto.CreateDiaryImageResponse>> updateProfileImage(@RequestPart(value = "image") MultipartFile multipartFile) throws IOException {
        userService.updateProfileImage(multipartFile);
        return new ResponseEntity<>(new SingleResponseDto<>(HttpStatus.OK.value()), HttpStatus.OK);
    }

}
