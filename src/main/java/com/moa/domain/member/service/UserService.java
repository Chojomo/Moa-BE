package com.moa.domain.member.service;

import com.moa.domain.member.dto.UserDto;

public interface UserService {

    void createUser(UserDto.CreateUserReq req);

}
