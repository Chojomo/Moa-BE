package com.moa.domain.follow.controller;

import com.moa.domain.follow.dto.query.UserFollowDto;
import com.moa.domain.follow.service.FollowService;
import com.moa.global.dto.ApiResponse;
import com.moa.global.dto.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<MultiResponseDto<List<UserFollowDto>>> getFollowers(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        Page<UserFollowDto> response = followService.getFollowers(userId, pageNumber, pageSize);

        return ResponseEntity.ok(new MultiResponseDto<>(HttpStatus.OK.value(), response.getContent(), response));
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<MultiResponseDto<List<UserFollowDto>>> getFollowings(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        Page<UserFollowDto> response = followService.getFollowings(userId, pageNumber, pageSize);

        return ResponseEntity.ok(new MultiResponseDto<>(HttpStatus.OK.value(), response.getContent(), response));
    }

}
