package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Noah Steffes on 5/9/18.
 */
public class Org {

    public String id;

    public String name;

    @JsonProperty("avatar_url")
    public String avatarUrl;

    public Room room;
}
