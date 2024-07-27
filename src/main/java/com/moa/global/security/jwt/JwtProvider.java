package com.moa.global.security.jwt;

import com.moa.domain.member.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtProvider {

    @Value("${jwt.key}")
    private String secretKey;

    @Value("${jwt.access-token-expiration-minutes}")
    private Integer accessTokenExpirationMinutes;

//    @Value("{jwt.refresh-token-expiration-minutes}")
//    private Integer refreshTokenExpirationMinutes;

    public String createAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());

        Date expiration = getTokenExpiration(accessTokenExpirationMinutes);
        Key key = getKeyFromSecretKey(getSecretKey());

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setSubject("accessToken")
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> getClaims(String jws) {
        Key key = getKeyFromSecretKey(secretKey);

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }

    private Date getTokenExpiration(Integer expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);

        return calendar.getTime();
    }

    private Key getKeyFromSecretKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);

        return Keys.hmacShaKeyFor(keyBytes);
    }

}
