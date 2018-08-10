package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

/**
 * Created by Noah Steffes on 4/17/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Collaborator {
    public String displayName;
    public String externalId;
    public String avatarUrl;
    public String type;
}
