package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

/**
 * Created by Noah Steffes on 4/17/18.
 * Todo Temporary
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Permission {
    boolean admin;
}
