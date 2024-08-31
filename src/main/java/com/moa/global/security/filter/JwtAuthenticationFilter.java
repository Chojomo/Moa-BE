package com.moa.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moa.domain.member.entity.User;
import com.moa.global.security.dto.LoginDto;
import com.moa.global.security.jwt.JwtProvider;
import com.moa.global.security.principaldetails.PrincipalDetailsService;
import com.moa.global.security.utils.UserDataResponder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserDataResponder userDataResponder;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUserEmail(), loginDto.getUserPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {
        PrincipalDetailsService.PrincipalDetails principal = (PrincipalDetailsService.PrincipalDetails) authentication.getPrincipal();

        User user = principal.getUser();

        String accessToken = jwtProvider.createAccessToken(user);

        response.setHeader("Authorization", "Bearer " + accessToken);

        userDataResponder.sendUserDataResponse(user, response);

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authentication);
    }

}
