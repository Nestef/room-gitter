package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Noah Steffes on 5/9/18.
 */
public class Repo {

    public String id;

    public String name;

    public String uri;

    @JsonProperty("private")
    public boolean isPrivate;

    public Room room;
}
