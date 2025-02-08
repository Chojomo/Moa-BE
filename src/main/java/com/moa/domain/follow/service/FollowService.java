package com.moa.domain.follow.service;

import com.moa.domain.follow.dto.FollowDto;
import com.moa.domain.follow.dto.query.UserFollowerDto;
import com.moa.domain.follow.entity.Follow;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface FollowService {

    void sendFollowRequest(UUID userId);

    Page<UserFollowerDto> getFollowers(UUID userId, Integer pageNumber, Integer pageSize);

}
