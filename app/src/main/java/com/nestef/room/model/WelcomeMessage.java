package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

/**
 * Created by Noah Steffes on 4/17/18.
 */
@Parcel
@JsonIgnoreProperties(ignoreUnknown = true)
public class WelcomeMessage {
    public String html;
    public String text;
}
