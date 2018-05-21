package com.nestef.room.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Noah Steffes on 5/21/18.
 */
public class RoomProvider extends ContentProvider {

    private static final int ROOM_MATCH = 261;
    private static final int PRIVATE_ROOM_MATCH = 972;
    UriMatcher mUriMatcher = buildUriMatcher();
    SQLiteOpenHelper mHelper;

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(RoomProviderContract.AUTHORITY, RoomProviderContract.ROOM_TABLE, ROOM_MATCH);
        uriMatcher.addURI(RoomProviderContract.AUTHORITY,
                RoomProviderContract.PRIVATE_ROOM_TABLE, PRIVATE_ROOM_MATCH);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mHelper = new RoomDbHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();

        int match = mUriMatcher.match(uri);
        Uri returnUri;
        long id;
        switch (match) {
            case ROOM_MATCH:
                id = db.insert(RoomProviderContract.ROOM_TABLE, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(
                            RoomProviderContract.RoomEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            case PRIVATE_ROOM_MATCH:
                id = db.insert(RoomProviderContract.PRIVATE_ROOM_TABLE, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(
                            RoomProviderContract.PrivateRoomEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Wrong uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        int numUpdated = 0;

        if (values == null) {
            throw new IllegalArgumentException("Cannot have null content values");
        }

        switch (mUriMatcher.match(uri)) {
            case ROOM_MATCH: {
                numUpdated = db.update(RoomProviderContract.ROOM_TABLE,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            case PRIVATE_ROOM_MATCH: {
                numUpdated = db.update(RoomProviderContract.PRIVATE_ROOM_TABLE,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        if (numUpdated > 0) {
            //   getContext().getContentResolver().notifyChange(uri, null);
        }

        return numUpdated;
    }
}
