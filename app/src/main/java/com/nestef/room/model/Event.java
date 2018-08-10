package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by Noah Steffes on 4/17/18.
 */
@Parcel
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    public String id;
    public String text;
    public String html;
    public Date sent;
    public Date editedAt;
    public int v;
}
