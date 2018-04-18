package com.nestef.room.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Noah Steffes on 4/17/18.
 */
public class Message {
    //ID of the message.
    String id;
    //Original message in plain-text/markdown.
    String text;
    //HTML formatted message.
    String html;
    //ISO formatted date of the message.
    Date sent;
    //ISO formatted date of the message if edited.
    Date editedAt;
    // (User)[user-resource] that sent the message.
    User fromUser;
    //Boolean that indicates if the current user has read the message.
    boolean unread;
    //Number of users that have read the message.
    int readBy;
    //todo add type
    //List of URLs present in the message.
    List<Url> urls;
    //List of @Mentions in the message.
    List<Mention> mentions;
    //List of #Issues referenced in the message.
    List<Issue> issues;
    //Metadata. This is currently not used for anything.
    List meta;
    //Version.
    int v;
    //Stands for "Gravatar version" and is used for cache busting.
    String gv;


}
