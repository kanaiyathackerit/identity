package com.kan.identity.security;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/unauthenticated").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer((oauth2) -> {
                    oauth2.jwt(Customizer.withDefaults());
                });
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter customJwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(this::converter);
        return converter;
    }

    public List<GrantedAuthority> converter(Jwt jwt) {
        List<GrantedAuthority> collect = jwt.getClaimAsStringList("cognito:groups").stream()
                .map(val -> new SimpleGrantedAuthority("CGROUP_" + val))
                .collect(Collectors.toList());
        collect.add(new SimpleGrantedAuthority("SCOPE_"+ jwt.getClaimAsString("scope")));
        return collect;
    }
}
