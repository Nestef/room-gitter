package com.nestef.room.data;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.nestef.room.provider.RoomProviderContract;

/**
 * Created by Noah Steffes on 5/22/18.
 */
public class LoaderProvider {

    Context mContext;

    public LoaderProvider(Context context) {
        mContext = context;
    }

    public Loader<Cursor> createRoomLoader() {
        return new CursorLoader(mContext,
                RoomProviderContract.RoomEntry.CONTENT_URI,
                null, null, null,
                RoomProviderContract.RoomEntry.COLUMN_UNREAD + " DESC");
    }

    public Loader<Cursor> createPrivateRoomLoader() {
        return new CursorLoader(mContext,
                RoomProviderContract.PrivateRoomEntry.CONTENT_URI,
                null, null, null,
                RoomProviderContract.PrivateRoomEntry.COLUMN_UNREAD + " DESC");
    }

    public Loader<Cursor> createGroupLoader() {
        return new CursorLoader(mContext,
                RoomProviderContract.GroupEntry.CONTENT_URI,
                null, null, null,
                null);
    }
}
