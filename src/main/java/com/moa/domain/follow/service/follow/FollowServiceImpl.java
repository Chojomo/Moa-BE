package com.moa.domain.follow.service.follow;

import com.moa.domain.follow.FollowMapper;
import com.moa.domain.follow.dto.FollowDto;
import com.moa.domain.follow.entity.Follow;
import com.moa.domain.follow.repository.FollowRepository;
import com.moa.domain.follow.service.FollowService;
import com.moa.domain.member.entity.User;
import com.moa.domain.member.service.UserService;
import com.moa.global.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FollowServiceImpl implements FollowService {

    private final AuthService authService;
    private final UserService userService;
    private final FollowRepository followRepository;
    private final FollowMapper followMapper;

    @Override
    public void sendFollowRequest(UUID userId) {
        User loginUser = authService.getLoginUser();

        User user = userService.findUserOrThrow(userId);

        followRepository.findFollowByFollowerAndFollowing(loginUser, user)
                .ifPresentOrElse(followRepository::delete,
                        () -> followRepository.save(
                                Follow.builder()
                                        .follower(loginUser)
                                        .following(user)
                                        .followedAt(LocalDateTime.now())
                                        .build()
                        ));
    }

    @Override
    public FollowDto.GetUserFollowsResponse getFollows(UUID userId) {
        User user = userService.findUserOrThrow(userId);

        List<Follow> followers = followRepository.findFollowByFollowing(user);
        List<Follow> followings = followRepository.findFollowByFollower(user);

        List<FollowDto.UserFollowerDto> followerDtoList = followMapper.mapToUserFollowerDtoList(followers);
        List<FollowDto.UserFollowingDto> followingDtoList = followMapper.mapToUserFollowingDtoList(followings);

        return FollowDto.GetUserFollowsResponse.builder()
                .followerCount(followers.size())
                .followingCount(followings.size())
                .followers(followerDtoList)
                .followings(followingDtoList)
                .build();
    }

}
