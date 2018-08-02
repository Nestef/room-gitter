package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.parceler.Parcel;

/**
 * Created by Noah Steffes on 5/9/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Org {

    public String id;

    public String name;

    @JsonProperty("avatar_url")
    public String avatarUrl;

    public Room room;
}
