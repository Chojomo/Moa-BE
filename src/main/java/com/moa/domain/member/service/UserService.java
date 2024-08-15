package com.moa.domain.member.service;

import com.moa.domain.member.dto.UserDto;
import com.moa.domain.member.entity.User;

public interface UserService {

    void createUser(UserDto.CreateUserReq req);

    void createOrUpdateUser(User user);

}
