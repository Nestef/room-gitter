package com.nestef.room.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.AsyncTask;

import com.nestef.room.model.Group;
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

    private DataCallback mCallback;

    private DataManager(ContentResolver contentResolver, PrefManager prefManager) {
        mContentResolver = contentResolver;
        mPrefManager = prefManager;
        mApiService = GitterServiceFactory.makeApiService(mPrefManager.getAuthToken());
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

    public void getGroups() {
        new GroupAsyncTask().execute();
    }

    public void getCommunityRooms(String groupId, DataCallback callback) {
        mCallback = callback;
        new CommunityAsyncTask().execute(groupId);
    }


    private void saveRooms(List<Room> rooms) {
        //Clear cached rooms
        mContentResolver.delete(RoomProviderContract.RoomEntry.CONTENT_URI, null, null);
        mContentResolver.delete(RoomProviderContract.PrivateRoomEntry.CONTENT_URI, null, null);

        //Separate private rooms
        Map<Boolean, List<Room>> split = new HashMap<Boolean, List<Room>>();
        for (Room room : rooms) {
            List<Room> list = split.get(room.oneToOne);
            if (list == null) {
                list = new ArrayList<Room>();
                split.put(room.oneToOne, list);
            }
            list.add(room);
        }

        //Add rooms to database
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
            contentValues.put(RoomProviderContract.RoomEntry.COLUMN_MEMBER, String.valueOf(room.roomMember));
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
            contentValues.put(RoomProviderContract.PrivateRoomEntry.COLUMN_MEMBER, String.valueOf(room.roomMember));
            mContentResolver
                    .insert(RoomProviderContract.PrivateRoomEntry.CONTENT_URI, contentValues);
        }

    }

    private void saveGroups(List<Group> groups) {
        mContentResolver.delete(RoomProviderContract.GroupEntry.CONTENT_URI, null, null);
        for (Group group : groups) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(RoomProviderContract.GroupEntry.COLUMN_ID,
                    group.id);
            contentValues.put(RoomProviderContract.GroupEntry.COLUMN_NAME,
                    group.name);
            contentValues.put(RoomProviderContract.GroupEntry.COLUMN_AVATARURL,
                    group.avatarUrl);
            contentValues.put(RoomProviderContract.GroupEntry.COLUMN_URI,
                    group.uri);
            contentValues.put(RoomProviderContract.GroupEntry.COLUMN_HOMEURI,
                    group.homeUri);
            mContentResolver.insert(RoomProviderContract.GroupEntry.CONTENT_URI, contentValues);

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

    class GroupAsyncTask extends AsyncTask<Void, Void, List<Group>> {
        @Override
        protected List<Group> doInBackground(Void... voids) {
            try {
                return mApiService.getGroups().execute().body();
            } catch (IOException io) {
                io.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Group> groups) {
            super.onPostExecute(groups);
            if (groups != null) {
                saveGroups(groups);
            }
        }
    }

    public interface DataCallback {
        void onCall(List<Room> data);
    }

    class CommunityAsyncTask extends AsyncTask<String, Void, List<Room>> {

        @Override
        protected List<Room> doInBackground(String... strings) {
            try {
                return mApiService.getGroupRooms(strings[0]).execute().body();
            } catch (IOException i) {
                i.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Room> rooms) {
            super.onPostExecute(rooms);
            mCallback.onCall(rooms);
        }
    }

}
