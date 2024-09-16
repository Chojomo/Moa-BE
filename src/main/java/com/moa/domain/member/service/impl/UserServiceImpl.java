package com.moa.domain.member.service.impl;

import com.moa.domain.member.entity.User;
import com.moa.domain.member.exception.UserException;
import com.moa.domain.member.exception.UserExceptionCode;
import com.moa.domain.member.repository.UserRepository;
import com.moa.domain.member.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User findUserOrThrow(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_EXISTS));
    }

}
