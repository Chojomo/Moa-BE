package com.moa.global.security.filter;

import com.moa.domain.member.entity.User;
import com.moa.domain.member.repository.UserRepository;
import com.moa.global.security.jwt.JwtProvider;
import com.moa.global.security.principaldetails.PrincipalDetailsService;
import com.moa.global.security.utils.CustomAuthorityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final CustomAuthorityUtils customAuthorityUtils;
    private final PrincipalDetailsService principalDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("Authorization");

        try {
            setAuthenticationToContext(accessToken);
        } catch (Exception exception) {
            request.setAttribute("exception", exception);
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthenticationToContext(String accessToken) {
        String jws = accessToken.replace("Bearer ", "");

        Map<String, Object> claims = jwtProvider.getClaims(jws).getBody();

        User user = userRepository.findUserByUserId(UUID.fromString((String) claims.get("userId")))
                .orElseThrow(() -> new UsernameNotFoundException("사용자 정보가 없습니다."));

        List<GrantedAuthority> authorities = customAuthorityUtils.createAuthorities(user.getRoles());

        UserDetails userDetails = principalDetailsService.loadUserByUsername(user.getUserEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
