package com.nestef.room.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.AsyncTask;

import com.nestef.room.model.Room;
import com.nestef.room.provider.RoomProviderContract;
import com.nestef.room.services.GitterApiService;
import com.nestef.room.services.GitterServiceFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Noah Steffes on 5/22/18.
 */
public class DataManager {

    private static DataManager sInstance;

    private final GitterApiService mApiService;

    private ContentResolver mContentResolver;

    private PrefManager mPrefManager;

    private DataManager(ContentResolver contentResolver, PrefManager prefManager) {

        mApiService = GitterServiceFactory.makeApiService(prefManager.getAuthToken());
        mContentResolver = contentResolver;
    }

    public static DataManager getInstance(ContentResolver contentResolver, PrefManager prefManager) {
        if (sInstance == null) {
            sInstance = new DataManager(contentResolver, prefManager);
        }
        return sInstance;
    }

    public void getRooms() {
        new RoomAsyncTask().execute();
    }

    void saveRooms(List<Room> rooms) {
        mContentResolver.delete(RoomProviderContract.RoomEntry.CONTENT_URI, null, null);
        mContentResolver.delete(RoomProviderContract.PrivateRoomEntry.CONTENT_URI, null, null);

        Map<Boolean, List<Room>> split = new HashMap<Boolean, List<Room>>();
        for (Room room : rooms) {
            List<Room> list = split.get(room.oneToOne);
            if (list == null) {
                list = new ArrayList<Room>();
                split.put(room.oneToOne, list);
            }
            list.add(room);
        }

        for (Room room : split.get(false)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(RoomProviderContract.RoomEntry.COLUMN_ID,
                    room.id);
            contentValues.put(RoomProviderContract.RoomEntry.COLUMN_NAME,
                    room.name);
            contentValues.put(RoomProviderContract.RoomEntry.COLUMN_AVATARURL,
                    room.avatarUrl);
            contentValues.put(RoomProviderContract.RoomEntry.COLUMN_UNREAD,
                    room.unreadItems);
            contentValues.put(RoomProviderContract.RoomEntry.COLUMN_USER_COUNT,
                    room.userCount);
            contentValues.put(RoomProviderContract.RoomEntry.COLUMN_FAVOURITE,
                    room.favourite);
            mContentResolver
                    .insert(RoomProviderContract.RoomEntry.CONTENT_URI, contentValues);
        }
        for (Room room : split.get(true)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(RoomProviderContract.PrivateRoomEntry.COLUMN_ID,
                    room.id);
            contentValues.put(RoomProviderContract.PrivateRoomEntry.COLUMN_NAME,
                    room.name);
            contentValues.put(RoomProviderContract.PrivateRoomEntry.COLUMN_AVATARURL,
                    room.avatarUrl);
            contentValues.put(RoomProviderContract.PrivateRoomEntry.COLUMN_UNREAD,
                    room.unreadItems);
            contentValues.put(RoomProviderContract.PrivateRoomEntry.COLUMN_USER_COUNT,
                    room.userCount);
            contentValues.put(RoomProviderContract.PrivateRoomEntry.COLUMN_FAVOURITE,
                    room.favourite);
            mContentResolver
                    .insert(RoomProviderContract.PrivateRoomEntry.CONTENT_URI, contentValues);
        }

    }


    class RoomAsyncTask extends AsyncTask<Void, Void, List<Room>> {
        @Override
        protected List<Room> doInBackground(Void... voids) {
            try {
                return mApiService.getRooms().execute().body();
            } catch (IOException io) {
                io.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Room> rooms) {
            super.onPostExecute(rooms);
            if (rooms != null) {
                saveRooms(rooms);
            }
        }
    }

}
