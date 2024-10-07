package com.moa.domain.follow;

import com.moa.domain.follow.dto.FollowDto;
import com.moa.domain.follow.entity.Follow;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FollowMapper {

    public List<FollowDto.UserFollowerDto> mapToUserFollowerDtoList(List<Follow> followers) {
        return followers.stream()
                .map(follow -> FollowDto.UserFollowerDto.builder()
                        .userId(follow.getFollower().getUserId())
                        .userNickname(follow.getFollower().getUserNickname())
                        .build())
                .toList();
    }

    public List<FollowDto.UserFollowingDto> mapToUserFollowingDtoList(List<Follow> followings) {
        return followings.stream()
                .map(follow -> FollowDto.UserFollowingDto.builder()
                        .userId(follow.getFollowing().getUserId())
                        .userNickname(follow.getFollowing().getUserNickname())
                        .build())
                .toList();
    }


}
