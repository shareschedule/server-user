package com.schedule.share.common.config;

import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
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

    private final EurekaClient discoveryClient;

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
                .requestMatchers(new AntPathRequestMatcher("/login/**")).permitAll()
        );
    }

    private void setHttpConfig(HttpSecurity httpSecurity) throws Exception {
        IpAddressMatcher hasIpAddress = new IpAddressMatcher(
                discoveryClient.getNextServerFromEureka("GATEWAY", false).getIPAddr()
        );

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
