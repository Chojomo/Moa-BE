package com.moa.domain.member.service;

import com.moa.domain.member.dto.UserDto;
import com.moa.domain.member.entity.User;

import java.util.UUID;

public interface UserService {

    User findUserOrThrow(UUID userId);

    UserDto.GetUserMyPageResponse getUserMyPage(UUID userId);

}
