package com.moa.domain.follow.service.follow;

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
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FollowServiceImpl implements FollowService {

    private final AuthService authService;
    private final UserService userService;
    private final FollowRepository followRepository;

    @Override
    public void sendFollowRequest(UUID userId) {
        User loginUser = authService.getLoginUser();

        User user = userService.findUserOrThrow(userId);

        if (followRepository.findFollowByFollowerAndFollowing(loginUser, user).isEmpty()) {
            followRepository.save(Follow.builder()
                    .follower(loginUser)
                    .following(user)
                    .followedAt(LocalDateTime.now())
                    .build());
        } else {
            log.info("이미 팔로우한 사용자입니다.");
        }
    }

}
