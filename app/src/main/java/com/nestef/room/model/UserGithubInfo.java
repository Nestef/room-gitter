package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

/**
 * Created by Noah Steffes on 4/18/18.
 */
@Parcel
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserGithubInfo {
    int followers;
    int public_repos;
    int following;
}
