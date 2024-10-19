package com.moa.global.security.utils;

import com.google.gson.Gson;
import com.moa.domain.member.entity.User;
import com.moa.global.dto.SingleResponseDto;
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

        SingleResponseDto<UserDataResponse> userData = SingleResponseDto.<UserDataResponse>builder()
                .status(HttpStatus.OK.value())
                .data(
                UserDataResponse.builder()
                        .userId(user.getUserId())
                        .userNickname(user.getUserNickname())
                        .build()
        ).build();


        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(gson.toJson(userData, SingleResponseDto.class));
    }

}
