package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Noah Steffes on 5/11/18.
 */
public class AuthResponse {
    @JsonProperty("access_token")
    public String accessToken;


    @JsonProperty("token_type")
    String tokenType;

}
