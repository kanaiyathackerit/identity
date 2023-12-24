package com.kan.identity.util;

import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class Util {

    public static final String COGNITO_GROUPS = "cognito:groups";

    public static List<String> getGroups() {
        return getAuthToken().getToken().getClaim(COGNITO_GROUPS);
    }
    public static JwtAuthenticationToken getAuthToken(){
        return (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }
    public static List<? extends GrantedAuthority> getGrantedAuthorities() {
        return (List<? extends GrantedAuthority>) getAuthToken().getAuthorities();
    }

    public static String getTokenValue() {
        return getAuthToken().getToken().getTokenValue();
    }

}
