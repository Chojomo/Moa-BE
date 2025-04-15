package com.moa.global.security.controller;

import com.moa.domain.member.dto.UserDto;
import com.moa.global.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<Integer>> registerUser(@RequestBody UserDto.CreateUserReq req) {
        authService.registerUser(req);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created());
    }

    @PostMapping("/change/password")
    public ResponseEntity<ApiResponse<Integer>> changePassword(@RequestBody UserDto.ChangePasswordRequest req) {
        authService.changePassword(req);

        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/email/check")
    public ResponseEntity<ApiResponse<Integer>> checkEmailAvailability(@RequestParam String email) {
        authService.checkEmailAvailability(email);

        return ResponseEntity.ok(ApiResponse.ok());
    }

}
