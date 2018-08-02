package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

/**
 * Created by Noah Steffes on 4/16/18.
 */
@Parcel
@JsonIgnoreProperties(ignoreUnknown = true)
public class Group {
    //Group ID.
    public String id;
    //Group name.
    public String name;
    //Group URI on Gitter.
    public String uri;
    public String homeUri;
    //Security descriptor. Describes the backing object we get permissions from.
    public BackedBy backedBy;
    //Base avatar URL (add s parameter to size)
    public String avatarUrl;

    public String forumId;
}
