package com.moa.global.security.controller;

import com.moa.domain.member.dto.UserDto;
import com.moa.global.dto.SingleResponseDto;
import com.moa.global.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<SingleResponseDto<Integer>> registerUser(@RequestBody UserDto.CreateUserReq req) {
        authService.registerUser(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(SingleResponseDto.created());
    }

    @PostMapping("/change/password")
    public ResponseEntity<SingleResponseDto<Integer>> changePassword(@RequestBody UserDto.ChangePasswordRequest req) {
        authService.changePassword(req);
        return ResponseEntity.ok().body(SingleResponseDto.ok());
    }

    @GetMapping("/email/check")
    public ResponseEntity<SingleResponseDto<Integer>> checkEmailAvailability(@RequestParam String email) {
        authService.checkEmailAvailability(email);
        return ResponseEntity.ok().body(SingleResponseDto.ok());
    }

}
