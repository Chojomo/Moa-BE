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
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        User user = null;

        switch (userRequest.getClientRegistration().getRegistrationId()) {
            case "google" -> {
                Object email = oAuth2User.getAttribute("email");

                user = User.builder()
                        .userEmail((String) email)
                        .userNickname(getUserNicknameFromEmail((String) Objects.requireNonNull(email)))
                        .userEmailType(userRequest.getClientRegistration().getRegistrationId())
                        // 추후 minio 사용하여 링크 변경
                        .userProfileImage("https://gjs-photoday-practice.s3.ap-northeast-2.amazonaws.com/userImage.png")
                        .roles(List.of("USER"))
                        .build();
            }
            case "kakao" -> {
                Map<String, Object> attributes = oAuth2User.getAttribute("kakao_account");

                String email = (String) Objects.requireNonNull(attributes).get("email");

                user = User.builder()
                        .userEmail(email)
                        .userNickname(getUserNicknameFromEmail(email))
                        .userEmailType(userRequest.getClientRegistration().getRegistrationId())
                        // 추후 minio 사용하여 링크 변경
                        .userProfileImage("https://gjs-photoday-practice.s3.ap-northeast-2.amazonaws.com/userImage.png")
                        .roles(List.of("USER"))
                        .build();
            }
        }

        userService.createOrUpdateUser(user);

        return oAuth2User;
    }

    private String getUserNicknameFromEmail(String email) {
        return email.substring(0, email.indexOf('@'));
    }

}
