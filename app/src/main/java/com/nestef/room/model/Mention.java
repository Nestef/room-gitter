package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by Noah Steffes on 4/17/18.
 */
@Parcel
@JsonIgnoreProperties(ignoreUnknown = true)
public class Mention {
    public String screenName;
    public String userId;
    public List<String> userIds;
}
