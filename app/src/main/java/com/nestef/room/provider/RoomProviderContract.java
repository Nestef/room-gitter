package com.nestef.room.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Noah Steffes on 5/21/18.
 */
public class RoomProviderContract {
    public static final String AUTHORITY = "com.nestef.room.roomProvider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PRIVATE_ROOM_TABLE = "private_room";
    public static final String ROOM_TABLE = "room";

    public static final class RoomEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(ROOM_TABLE).build();

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AVATARURL = "avatar_url";
        public static final String COLUMN_USER_COUNT = "user_count";
        public static final String COLUMN_UNREAD = "unread_items";
        public static final String COLUMN_FAVOURITE = "favourite";
    }

    public static final class PrivateRoomEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PRIVATE_ROOM_TABLE).build();

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AVATARURL = "avatar_url";
        public static final String COLUMN_USER_COUNT = "user_count";
        public static final String COLUMN_UNREAD = "unread_items";
        public static final String COLUMN_FAVOURITE = "favourite";
    }
}
