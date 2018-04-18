package com.nestef.room.model;

import java.util.List;

/**
 * Created by Noah Steffes on 4/16/18.
 */
public class User {
    //Gitter User ID.
    String id;
    // Gitter/GitHub username.
    String username;
    //Gitter/GitHub user real name.
    String displayName;
    //Path to the user on Gitter.
    String url;
    String avatarUrl;
    //User avatar URI (small).
    String avatarUrlSmall;
    //User avatar URI (medium).
    String avatarUrlMedium;
    List<String> providers;
    int v;
    String gv;
}
