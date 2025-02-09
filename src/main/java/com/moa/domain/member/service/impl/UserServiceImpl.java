package com.moa.domain.member.service.impl;

import com.moa.domain.follow.entity.Follow;
import com.moa.domain.follow.repository.FollowRepository;
import com.moa.domain.member.dto.UserDto;
import com.moa.domain.member.entity.User;
import com.moa.domain.member.exception.UserException;
import com.moa.domain.member.exception.UserExceptionCode;
import com.moa.domain.member.mapper.UserMapper;
import com.moa.domain.member.repository.UserRepository;
import com.moa.domain.member.service.UserService;
import com.moa.global.security.service.AuthService;
import com.moa.global.storage.service.ObjectStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final AuthService authService;
    private final UserMapper userMapper;
    private final ObjectStorageService objectStorageService;

    public User findUserOrThrow(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_EXISTS));
    }

    @Override
    public UserDto.GetUserMyPageResponse getUserMyPage(UUID userId) {
        User loginUser = authService.getLoginUser();

        Optional<User> optionalUser = userRepository.findById(userId);

        User user = optionalUser.orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_EXISTS));

        Boolean isFollowing = false;

        if (followRepository.findFollowByFollowerAndFollowing(loginUser, user).isPresent()) {
            isFollowing = true;
        }

        return userMapper.toUserMyPageResponse(loginUser, user, isFollowing);
    }

    @Override
    public void updateProfileImage(MultipartFile multipartFile) throws IOException {
        User loginUser = authService.getLoginUser();

        String imageUrl = objectStorageService.uploadProfileImage(loginUser.getUserId(), multipartFile);

        loginUser.updateProfileImage(imageUrl);
    }

}
