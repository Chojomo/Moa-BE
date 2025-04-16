package com.moa.global.security.utils;

import com.google.gson.Gson;
import com.moa.domain.member.entity.User;
import com.moa.global.dto.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class UserDataResponder {

    public void sendUserDataResponse(User user, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();

        ApiResponse<UserDataResponse> userData = ApiResponse.<UserDataResponse>builder()
                .status(HttpStatus.OK.value())
                .data(
                UserDataResponse.builder()
                        .userId(user.getUserId())
                        .userNickname(user.getUserNickname())
                        .userProfileImage(user.getUserProfileImage())
                        .build()
        ).build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(gson.toJson(userData, ApiResponse.class));
    }

}
