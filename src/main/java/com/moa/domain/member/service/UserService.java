package com.moa.domain.member.service;

import com.moa.domain.member.entity.User;

import java.util.UUID;

public interface UserService {

    User findUserOrThrow(UUID userId);

}
