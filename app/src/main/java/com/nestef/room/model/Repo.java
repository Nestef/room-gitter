package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.parceler.Parcel;

/**
 * Created by Noah Steffes on 5/9/18.
 */
@Parcel
@JsonIgnoreProperties(ignoreUnknown = true)
public class Repo {

    public String id;

    public String name;

    public String uri;

    @JsonProperty("private")
    public boolean isPrivate;

    public Room room;
}
