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

        User user = User.builder()
                .userEmail((String) email)
                .userNickname(getUserNicknameFromEmail((String) email))
                .userEmailType(userRequest.getClientRegistration().getRegistrationId())
                // 추후 minio 사용하여 링크 변경
                .userProfileImage("https://gjs-photoday-practice.s3.ap-northeast-2.amazonaws.com/userImage.png")
                .roles(List.of("USER"))
                .build();

        userService.createOrUpdateUser(user);

        return oAuth2User;
    }

    private String getUserNicknameFromEmail(String email) {
        return email.substring(0, email.indexOf('@'));
    }

}
