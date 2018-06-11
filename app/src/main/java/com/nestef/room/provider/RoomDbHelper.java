package com.nestef.room.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Noah Steffes on 5/21/18.
 */
public class RoomDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "roomDb.db";

    private static final int VERSION = 3;

    RoomDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                RoomProviderContract.ROOM_TABLE + " (" +
                RoomProviderContract.RoomEntry._ID + " INTEGER PRIMARY KEY, " +
                RoomProviderContract.RoomEntry.COLUMN_ID + " TEXT NOT NULL, " +
                RoomProviderContract.RoomEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                RoomProviderContract.RoomEntry.COLUMN_AVATARURL + " TEXT NOT NULL, " +
                RoomProviderContract.RoomEntry.COLUMN_FAVOURITE + " INTEGER NOT NULL, " +
                RoomProviderContract.RoomEntry.COLUMN_UNREAD + " INTEGER NOT NULL, " +
                RoomProviderContract.RoomEntry.COLUMN_USER_COUNT + " INTEGER NOT NULL, " +
                RoomProviderContract.RoomEntry.COLUMN_MEMBER + " TEXT NOT NULL);";

        final String CREATE_PRIVATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                RoomProviderContract.PRIVATE_ROOM_TABLE + " (" +
                RoomProviderContract.PrivateRoomEntry._ID + " INTEGER PRIMARY KEY, " +
                RoomProviderContract.PrivateRoomEntry.COLUMN_ID + " TEXT NOT NULL, " +
                RoomProviderContract.PrivateRoomEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                RoomProviderContract.PrivateRoomEntry.COLUMN_AVATARURL + " TEXT NOT NULL, " +
                RoomProviderContract.PrivateRoomEntry.COLUMN_FAVOURITE + " INTEGER NOT NULL, " +
                RoomProviderContract.PrivateRoomEntry.COLUMN_UNREAD + " INTEGER NOT NULL, " +
                RoomProviderContract.PrivateRoomEntry.COLUMN_USER_COUNT + " INTEGER NOT NULL, " +
                RoomProviderContract.PrivateRoomEntry.COLUMN_MEMBER + " TEXT NOT NULL);";

        final String CREATE_GROUP_TABLE = "CREATE TABLE IF NOT EXISTS " +
                RoomProviderContract.GROUP_TABLE + " (" +
                RoomProviderContract.GroupEntry._ID + " INTEGER PRIMARY KEY, " +
                RoomProviderContract.GroupEntry.COLUMN_ID + " TEXT NOT NULL, " +
                RoomProviderContract.GroupEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                RoomProviderContract.GroupEntry.COLUMN_AVATARURL + " TEXT NOT NULL, " +
                RoomProviderContract.GroupEntry.COLUMN_URI + " TEXT NOT NULL, " +
                RoomProviderContract.GroupEntry.COLUMN_HOMEURI + " TEXT NOT NULL);";


        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_PRIVATE_TABLE);
        db.execSQL(CREATE_GROUP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + RoomProviderContract.ROOM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RoomProviderContract.PRIVATE_ROOM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RoomProviderContract.GROUP_TABLE);
        onCreate(db);
    }
}
