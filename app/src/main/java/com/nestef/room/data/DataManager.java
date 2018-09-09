package com.nestef.room.data;

import android.content.ContentResolver;
import android.content.ContentValues;

import com.nestef.room.model.Group;
import com.nestef.room.model.Room;
import com.nestef.room.provider.RoomProviderContract;
import com.nestef.room.services.GitterApiService;
import com.nestef.room.services.GitterServiceFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Noah Steffes on 5/22/18.
 */
public class DataManager {

    private static DataManager sInstance;

    private final GitterApiService mApiService;

    private ContentResolver mContentResolver;

    private DataCallback mCallback;

    private DataManager(ContentResolver contentResolver, PrefManager prefManager) {
        mContentResolver = contentResolver;
        mApiService = GitterServiceFactory.makeApiService(prefManager.getAuthToken());
    }

    public static DataManager getInstance(ContentResolver contentResolver, PrefManager prefManager) {
        if (sInstance == null) {
            sInstance = new DataManager(contentResolver, prefManager);
        }
        return sInstance;
    }

    public void getRooms() {
        mApiService.getRooms().enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                List<Room> rooms = response.body();
                if (rooms != null) {
                    saveRooms(rooms);
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getGroups() {
        mApiService.getGroups().enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                List<Group> groups = response.body();
                if (groups != null) {
                    saveGroups(groups);
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getCommunityRooms(String groupId, DataCallback callback) {
        mApiService.getGroupRooms(groupId).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                callback.onCall(response.body());
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    private void saveRooms(List<Room> rooms) {
        //Clear cached rooms
        mContentResolver.delete(RoomProviderContract.RoomEntry.CONTENT_URI, null, null);
        mContentResolver.delete(RoomProviderContract.PrivateRoomEntry.CONTENT_URI, null, null);

        //Separate private rooms
        Map<Boolean, List<Room>> split = new HashMap<>();
        for (Room room : rooms) {
            List<Room> list = split.get(room.oneToOne);
            if (list == null) {
                list = new ArrayList<>();
                split.put(room.oneToOne, list);
            }
            list.add(room);
        }

        //Add rooms to database
        if (split.get(false) != null) {
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
        }
        if (split.get(true) != null) {
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

    public interface DataCallback {
        void onCall(List<Room> data);
    }
}
