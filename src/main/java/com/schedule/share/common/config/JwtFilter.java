package com.schedule.share.common.config;

import com.schedule.share.common.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Component
//@RequiredArgsConstructor
public class JwtFilter {
//public class JwtFilter extends OncePerRequestFilter {

//    private final JwtUtil jwtUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String accessToken = getAccessToken(request);
//
//        jwtUtil.checkToken(accessToken);
//
//        long userId = jwtUtil.getUserId(accessToken);
//
//        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userId, null);
//        SecurityContextHolder.getContext().setAuthentication(auth);
//        filterChain.doFilter(request, response);
//
//    }

//    private String getAccessToken(HttpServletRequest request) {
//        String authorization = request.getHeader("Authorization");
//
//        if(authorization.startsWith("Bearer")) {
//            return authorization.substring(7);
//        }
//
//        return null;
//    }
}
