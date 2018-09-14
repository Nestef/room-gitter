package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by Noah Steffes on 4/16/18.
 */
@Entity(tableName = "group_table")
@Parcel
@JsonIgnoreProperties(ignoreUnknown = true)
public class Group {
    //Group ID.
    @PrimaryKey
    @NonNull
    public String id;
    //Group name.
    public String name;
    //Group URI on Gitter.
    public String uri;
    public String homeUri;
    //Security descriptor. Describes the backing object we get permissions from.
    @Ignore
    public BackedBy backedBy;
    //Base avatar URL (add s parameter to size)
    public String avatarUrl;

    public String forumId;
}
