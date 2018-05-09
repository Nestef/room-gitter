package com.nestef.room.model;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by Noah Steffes on 5/8/18.
 */
@Parcel
public class Ban {

    public User user;

    public User bannedBy;

    public Date dateBanned;
}
