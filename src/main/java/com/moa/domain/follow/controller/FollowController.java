package com.moa.domain.follow.controller;

import com.moa.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{userId}")
    public ResponseEntity<Void> sendFollowRequest(@PathVariable UUID userId) {
        followService.sendFollowRequest(userId);
        return ResponseEntity.ok().build();
    }

}
