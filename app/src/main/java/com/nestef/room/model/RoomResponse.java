package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Noah Steffes on 5/9/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoomResponse {

    public String id;

    public String name;
}
