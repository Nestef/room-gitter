package com.nestef.room.model;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by Noah Steffes on 4/16/18.
 */
@Parcel
public class User {
    //Gitter User ID.
    public String id;
    // Gitter/GitHub username.
    public String username;
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
}
