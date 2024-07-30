package com.moa.domain.member.controller;

import com.moa.domain.member.dto.UserDto;
import com.moa.domain.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserDto.CreateUserReq req) {
        userService.createUser(req);

        return ResponseEntity.ok().build();
    }

}
