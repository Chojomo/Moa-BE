package com.moa.global.security.service;

import com.moa.domain.member.dto.UserDto;
import com.moa.domain.member.entity.User;

public interface AuthService {

    void registerUser(UserDto.CreateUserReq req);

    void registerOrUpdateUser(User user);

}
