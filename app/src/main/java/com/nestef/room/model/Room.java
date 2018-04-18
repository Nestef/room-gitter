package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * Created by Noah Steffes on 4/16/18.
 */
public class Room {
    //Room ID.
    String id;
    //Room name.
    String name;
    //Room topic. (default: GitHub repo description)
    String topic;
    String avatarUrl;
    //Room URI on Gitter.
    String uri;
    //Indicates if the room is a one-to-one chat.
    boolean oneToOne;
    //Count of users in the room.
    int userCount;
    User user;
    //Number of unread messages for the current user.
    int unreadItems;
    // Number of unread mentions for the current user.
    int mentions;
    //Last time the current user accessed the room in ISO format.
    Date lastAccessTime;
    //Indicates if the room is on of your favourites.
    int favourite;
    //Indicates if the current user has disabled notifications.
    boolean lurk;
    //Path to the room on gitter.
    String url;
    //Type of the room.
    String githubType;
    String security;
    boolean noIndex;
    //Tags that define the room.
    List<String> tags;
    //Temporary
    Permission permissions;
    boolean roomMember;
    String groupId;
    Group group;
    //Backend?
    Backend backend;
    @JsonProperty("public")
    boolean isPublic;
    //Room version.
    int v;
}
