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
    String screenName;
    String userId;
    List<String> userIds;
}
