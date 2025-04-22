package com.moa.domain.follow.controller;

import com.moa.domain.follow.dto.query.UserFollowDto;
import com.moa.domain.follow.service.FollowService;
import com.moa.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<Integer>> sendFollowRequest(@PathVariable UUID userId) {
        followService.sendFollowRequest(userId);

        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/{userId}/follower")
    public ResponseEntity<ApiResponse<List<UserFollowDto>>> getFollowers(
            @PathVariable UUID userId,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<UserFollowDto> response = followService.getFollowers(userId, pageable);

        return ResponseEntity.ok(ApiResponse.okPage(response));
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<ApiResponse<List<UserFollowDto>>> getFollowings(
            @PathVariable UUID userId,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<UserFollowDto> response = followService.getFollowings(userId, pageable);

        return ResponseEntity.ok(ApiResponse.okPage(response));
    }

}
