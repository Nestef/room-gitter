package com.nestef.room.model;

import java.util.List;

/**
 * Created by Noah Steffes on 4/16/18.
 */

public class Group {
    //Group ID.
    String id;
    //Group name.
    String name;
    //Group URI on Gitter.
    String uri;
    String homeUri;
    //Security descriptor. Describes the backing object we get permissions from.
    List<String> backedBy;
    //Base avatar URL (add s parameter to size)
    String avatarUrl;
}
