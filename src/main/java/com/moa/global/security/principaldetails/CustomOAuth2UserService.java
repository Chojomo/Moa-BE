package com.moa.global.security.principaldetails;

import com.moa.domain.member.entity.User;
import com.moa.domain.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        Object email = oAuth2User.getAttribute("email");
        Object picture = oAuth2User.getAttribute("picture");

        User user = User.builder()
                .userEmail((String) email)
                .userNickname(getUserNicknameFromEmail((String) email))
                .userEmailType((byte) 2)
                .userProfileImage((String) picture) // 프로필 이미지 minio 에 저장 후 해당 url DB 저장
                .roles(List.of("USER"))
                .build();

        userService.createOrUpdateUser(user);

        return oAuth2User;
    }

    private String getUserNicknameFromEmail(String email) {
        return email.substring(0, email.indexOf('@'));
    }

}
