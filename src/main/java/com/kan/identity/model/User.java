package com.kan.identity.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("given_name")
    private String givenName;
    @JsonProperty("family_name")
    private String familyName;
    @JsonProperty("custom:VID")
    private String vid;

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getVid() {
        return vid;
    }

}
