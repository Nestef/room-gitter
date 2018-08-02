package com.nestef.room.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

/**
 * Created by Noah Steffes on 4/17/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Parcel
public class Message {
    //ID of the message.
    public String id;
    //Original message in plain-text/markdown.
    public String text;
    //HTML formatted message.
    public String html;
    //ISO formatted date of the message.
    public Date sent;
    //ISO formatted date of the message if edited.
    public Date editedAt;
    // (User)[user-resource] that sent the message.
    public User fromUser;
    //Boolean that indicates if the current user has read the message.
    public boolean unread;
    //Number of users that have read the message.
    public int readBy;
    //todo add type
    //List of URLs present in the message.
    public List<Url> urls;
    //List of @Mentions in the message.
    public List<Mention> mentions;
    //List of #Issues referenced in the message.
    public List<Issue> issues;
    //Metadata. This is currently not used for anything.
    //Version.
    public int v;
    //Stands for "Gravatar version" and is used for cache busting.
    public String gv;


}
