package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

/**
 * Created by Noah Steffes on 4/17/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class BackedBy {
    String type;
    String linkPath;
}
