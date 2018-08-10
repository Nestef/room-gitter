package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by Noah Steffes on 5/8/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Ban {
    public User user;
    public User bannedBy;
    public Date dateBanned;
}
