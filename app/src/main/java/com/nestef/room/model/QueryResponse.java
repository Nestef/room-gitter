package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Noah Steffes on 7/3/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryResponse {
    public List<Room> results;
}
