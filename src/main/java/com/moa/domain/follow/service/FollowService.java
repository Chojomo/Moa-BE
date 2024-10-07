package com.moa.domain.follow.service;

import com.moa.domain.follow.dto.FollowDto;

import java.util.UUID;

public interface FollowService {

    void sendFollowRequest(UUID userId);

    FollowDto.GetUserFollowsResponse getFollows(UUID userId);

}
