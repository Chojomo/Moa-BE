package com.moa.global.security.service.impl;

import com.moa.domain.member.dto.UserDto;
import com.moa.domain.member.entity.User;
import com.moa.domain.member.exception.UserException;
import com.moa.domain.member.exception.UserExceptionCode;
import com.moa.domain.member.mapper.UserMapper;
import com.moa.domain.member.repository.UserRepository;
import com.moa.global.security.principaldetails.PrincipalDetailsService;
import com.moa.global.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserDto.CreateUserReq req) {
        String encodePassword = passwordEncoder.encode(req.getUserPassword());
        User user = userMapper.createUserReqToUser(req, encodePassword);

        userRepository.save(user);
    }

    @Override
    public void registerOrUpdateUser(User user) {
        Optional<User> findUser = userRepository.findUserByUserEmail(user.getUserEmail());

        if (findUser.isPresent()) {
            findUser.get().setUserProfileImage(user.getUserProfileImage());
        }  else {
            userRepository.save(user);
        }
    }

    @Override
    public User getLoginUser() {
        if (getPrincipalDetails() == null) {
             return null;
        }
        return getPrincipalDetails().getUser();
    }

    @Override
    public void changePassword(UserDto.ChangePasswordRequest req) {
        User loginUser = getLoginUser();

        String storedPassword = loginUser.getUserPassword();

        if (!passwordEncoder.matches(req.getCurrentPassword(), storedPassword)) {
            throw new UserException(UserExceptionCode.PASSWORD_NOT_MATCH);
        }

        if (!req.getNewPassword().equals(req.getConfirmNewPassword())) {
            throw new UserException(UserExceptionCode.PASSWORD_MISMATCH);
        }

        String encodedInputPassword = passwordEncoder.encode(req.getNewPassword());

        loginUser.setUserPassword(encodedInputPassword);

        userRepository.save(loginUser);
    }

    @Override
    public void checkEmailAvailability(String email) {
        Optional<User> findUser = userRepository.findUserByUserEmail(email);

        if (findUser.isPresent()) {
            throw new UserException(UserExceptionCode.EMAIL_EXISTS);
        }
    }

    private PrincipalDetailsService.PrincipalDetails getPrincipalDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof PrincipalDetailsService.PrincipalDetails) {
            return (PrincipalDetailsService.PrincipalDetails) principal;
        } else {
            return null;
        }
    }

}
