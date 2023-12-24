package com.kan.identity.service;


import static com.kan.identity.util.Util.*;
import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

import com.kan.identity.config.UserProperties;
import com.kan.identity.exception.NotFoundException;
import com.kan.identity.model.Identity;
import com.kan.identity.model.User;
import com.kan.identity.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserProperties properties;

    public Identity unauthenticated() {
        return new Identity("Free for all to see", "Anonymous", null, null);
    }

    public Identity authenticated() {
        User user = getUserInfo();
        return new Identity("Looks like you have been authenticated",
                user.getGivenName() + " " + user.getFamilyName(),
                getGrantedAuthorities(), null);
    }

    public Identity getVendors() {
        User user = getUserInfo();
        return new Identity(format("Welcome to the %s group", getGroupName()),
                user.getGivenName() + " " + user.getFamilyName(),
                getGrantedAuthorities(), null);
    }

    private String getGroupName() {
        List<String> group = getGroups();
        return group
                .stream()
                .filter(val -> val.equals("VENDOR"))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("User not in VENDOR group"));
    }

    public Identity getVendorsDetail(String vid) {
        User user = getUserInfo();
        Optional.ofNullable(vid)
                .filter(val -> val.equals(user.getVid()))
                .orElseThrow(() -> new NotFoundException("Invalid VID"));
        return new Identity("Looks like you’re a specific vendor!",
                user.getGivenName() + " " + user.getFamilyName(),
                getGrantedAuthorities(), user.getVid());
    }

    public User getUserInfo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getTokenValue());
        return restTemplate.exchange(properties.getUserInfoUrl(),
                HttpMethod.GET, new HttpEntity<>("body", headers), User.class).getBody();
    }
}
