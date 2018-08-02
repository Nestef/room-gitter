package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

/**
 * Created by Noah Steffes on 4/18/18.
 */
@Parcel
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfile {
    String id;
    String username;
    String displayname;
    boolean invited;
    boolean has_gitter_login;
    String company;
    String location;
    String email;
    String website;
    String profile;
    UserGithubInfo github;
    String gravatarImageUrl;
}
