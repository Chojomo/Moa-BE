package com.moa.domain.member.mapper;

import com.moa.domain.member.dto.UserDto;
import com.moa.domain.member.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User createUserReqToUser(UserDto.CreateUserReq req, String encodePassword) {
        return User.builder()
                .userEmail(req.getUserEmail())
                .userPw(encodePassword)
                .userNickname(getUserNicknameFromEmail(req.getUserEmail()))
                .userEmailType((byte) 0)
                // 추후 minio 사용하여 링크 변경
                .userProfileImage("https://gjs-photoday-practice.s3.ap-northeast-2.amazonaws.com/userImage.png")
                .build();
    }

    public String getUserNicknameFromEmail(String email) {
        return email.substring(0, email.indexOf('@'));
    }

}
