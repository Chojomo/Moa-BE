package com.moa.domain.follow.service;

import com.moa.domain.follow.dto.query.UserFollowDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FollowService {

    void sendFollowRequest(UUID userId);

    Page<UserFollowDto> getFollowers(UUID userId, Pageable pageable);

    Page<UserFollowDto> getFollowings(UUID userId, Pageable pageable);

}
