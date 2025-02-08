package com.moa.domain.follow.service;

import com.moa.domain.follow.dto.query.UserFollowDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface FollowService {

    void sendFollowRequest(UUID userId);

    Page<UserFollowDto> getFollowers(UUID userId, Integer pageNumber, Integer pageSize);

    Page<UserFollowDto> getFollowings(UUID userId, Integer pageNumber, Integer pageSize);

}
