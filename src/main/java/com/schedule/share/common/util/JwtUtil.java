package com.schedule.share.common.util;
import com.schedule.share.common.exception.Common401Exception;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.SECRET}")
    private String SECRET;
    @Value("${jwt.ACCESS_EXP_MS}")
    private int ACCESS_EXP_MS;
    @Value("${jwt.REFRESH_EXP_MS}")
    private int REFRESH_EXP_MS;

    public String generateAccessToken(long userId) {
        Date now = new Date();

        return Jwts.builder()
                .claim("userId", userId)
                .subject("accessToken")
                .issuedAt(now)
                .expiration(new Date(now.getTime() + ACCESS_EXP_MS))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(long userId) {
        Date now = new Date();

        return Jwts.builder()
                .claim("userId", userId)
                .subject("refreshToken")
                .issuedAt(now)
                .expiration(new Date(now.getTime() + REFRESH_EXP_MS))
                .signWith(getSigningKey())
                .compact();
    }

    public long getUserId (String accessToken) {
        Claims payload;

        try{
            payload = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new Common401Exception();
        }

        return (long)((int) payload.get("userId"));
    }

    public void checkToken(String accessToken) {
        try{
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new Common401Exception();
        }
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }
}