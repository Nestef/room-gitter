package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Noah Steffes on 5/8/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnreadResponse {
    public List<String> chat;
    public List<Mention> mention;
}
