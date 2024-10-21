package com.schedule.share.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${API_GATEWAY_IP}")
    private String GATEWAY_IP;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        permitSwagger(httpSecurity);
        setHttpConfig(httpSecurity);

        return httpSecurity.build();
    }

    private void permitSwagger(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests( auth -> auth
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/dev/**")).permitAll()
        );
    }

    private void setHttpConfig(HttpSecurity httpSecurity) throws Exception {
        IpAddressMatcher hasIpAddress = new IpAddressMatcher(GATEWAY_IP);
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers((new AntPathRequestMatcher("/**")))
                        .access(((authentication, context) ->
                                new AuthorizationDecision(hasIpAddress.matches(context.getRequest()))
                        ))
                        .anyRequest().denyAll()
                );
    }
}
