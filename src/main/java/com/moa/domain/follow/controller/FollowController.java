package com.moa.domain.follow.controller;

import com.moa.domain.follow.dto.FollowDto;
import com.moa.domain.follow.service.FollowService;
import com.moa.global.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{userId}")
    public ResponseEntity<SingleResponseDto<Integer>> sendFollowRequest(@PathVariable UUID userId) {
        followService.sendFollowRequest(userId);
        return new ResponseEntity<>(new SingleResponseDto<>(HttpStatus.OK.value()), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<SingleResponseDto<FollowDto.GetUserFollowsResponse>> getFollows(@PathVariable UUID userId) {
        FollowDto.GetUserFollowsResponse response = followService.getFollows(userId);
        return new ResponseEntity<>(new SingleResponseDto<>(HttpStatus.OK.value(), response), HttpStatus.OK);
    }

}
