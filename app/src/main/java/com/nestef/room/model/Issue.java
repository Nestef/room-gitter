package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

/**
 * Created by Noah Steffes on 4/17/18.
 */
@Parcel
@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {
    public String title;
    public String repo;
    public int number;
}
