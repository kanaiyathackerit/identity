package com.kan.identity.model;

import java.util.List;
import org.springframework.security.core.GrantedAuthority;

public record Identity(String message, String name, List<? extends GrantedAuthority> grantedAuthorities, String vid) {
}
