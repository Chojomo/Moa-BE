package com.moa.global.security.controller;

import com.moa.domain.member.dto.UserDto;
import com.moa.global.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody UserDto.CreateUserReq req) {
        authService.registerUser(req);

        return ResponseEntity.ok().build();
    }

}
