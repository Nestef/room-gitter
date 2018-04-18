package com.nestef.room.model.event;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Noah Steffes on 4/17/18.
 */
public class Event {
    String id;
    String text;
    String html;
    Date sent;
    Date editedAt;
    JSONObject meta;
    JSONObject payload;
    int v;
}
