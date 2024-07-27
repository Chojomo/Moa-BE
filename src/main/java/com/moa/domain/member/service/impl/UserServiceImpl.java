package com.moa.domain.member.service.impl;

import com.moa.domain.member.dto.UserDto;
import com.moa.domain.member.entity.User;
import com.moa.domain.member.mapper.UserMapper;
import com.moa.domain.member.repository.UserRepository;
import com.moa.domain.member.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserDto.CreateUserReq req) {
        String encodePassword = passwordEncoder.encode(req.getUserPw());
        User user = userMapper.createUserReqToUser(req, encodePassword);

        userRepository.save(user);
    }

}
