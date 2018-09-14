package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by Noah Steffes on 4/16/18.
 */
@Entity(tableName = "room_table")
@Parcel
@JsonIgnoreProperties(ignoreUnknown = true)
public class Room implements Comparable<Room> {
    //Room ID.
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "room_id")
    public String id;
    //Room name.
    public String name;
    //Room topic. (default: GitHub repo description)
    public String topic;
    public String description;
    public String avatarUrl;
    //Room URI on Gitter.
    public String uri;
    //Indicates if the room is a one-to-one chat.
    public boolean oneToOne;
    //Count of users in the room.
    public int userCount;
    @Ignore
    public User user;
    //Number of unread messages for the current user.
    public int unreadItems;
    // Number of unread mentions for the current user.
    public int mentions;
    //Last time the current user accessed the room in ISO format.
    @Ignore
    public Date lastAccessTime;
    //Indicates if the room is on of your favourites.
    public int favourite;
    //Indicates if the current user has disabled notifications.
    public boolean lurk;
    //Path to the room on gitter.
    public String url;
    //Type of the room.
    public String githubType;
    public String associatedRepo;
    public String security;
    public boolean premium;
    public boolean noindex;
    //Tags that define the room.
    @Ignore
    public List<String> tags;
    @Ignore
    public List<String> providers;
    //Temporary
    @Ignore
    public Permission permissions;
    public boolean roomMember;
    public String groupId;
    @Ignore
    public Group group;
    //Backend?
    @Ignore
    public Backend backend;
    @JsonProperty("public")
    public boolean isPublic;
    public boolean exists;
    //Room version.
    public int v;

    @Override
    public int compareTo(@NonNull Room o) {
        return Integer.compare(o.unreadItems, unreadItems);
    }
}
