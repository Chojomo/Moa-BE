package com.moa.global.security.handler;

import com.moa.domain.member.entity.User;
import com.moa.domain.member.repository.UserRepository;
import com.moa.global.security.jwt.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String clientRegistrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();

        String email = extractEmail(oAuth2User, clientRegistrationId);

        Optional<User> user = userRepository.findUserByUserEmail(email);

        if (user.isPresent()) {
            redirect(request, response, user.get());
        }
    }

    private String extractEmail(OAuth2User oAuth2User, String provider) {
        return switch (provider) {
            case "google" -> String.valueOf(oAuth2User.getAttributes().get("email"));
            case "kakao" -> {
                Map<String, Object> attributes = oAuth2User.getAttribute("kakao_account");
                yield (String) Objects.requireNonNull(attributes).get("email");
            }
            case "naver" -> {
                Map<String, Object> attributes = oAuth2User.getAttribute("response");
                yield (String) Objects.requireNonNull(attributes).get("email");
            }
            default -> null;
        };
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException {
        String accessToken = jwtProvider.createAccessToken(user);

        String uri = createURI(user, accessToken).toString();

        getRedirectStrategy().sendRedirect(request, response, uri);
    }

    private URI createURI(User user, String accessToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("userNickName", user.getUserNickname());
        queryParams.add("accessToken", accessToken);

        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .port(3000)
                .queryParams(queryParams)
                .build()
                .toUri();
    }

}
