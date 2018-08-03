package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by Noah Steffes on 4/16/18.
 */
@Parcel
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends GitterDataType {
    //Gitter User ID.
    public String id;
    // Gitter/GitHub username.
    @JsonProperty("username")
    public String name;
    //Gitter/GitHub user real name.
    public String displayName;
    //Path to the user on Gitter.
    public String url;
    public String avatarUrl;
    //User avatar URI (small).
    public String avatarUrlSmall;
    //User avatar URI (medium).
    public String avatarUrlMedium;
    public boolean staff;
    public List<String> providers;
    public int v;
    public String gv;

    public String getUsername() {
        if (displayName == null) {
            return name;
        } else {
            return displayName;
        }
    }
}
