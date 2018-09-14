package com.nestef.room.data;

import com.nestef.room.db.GroupDao;
import com.nestef.room.db.RoomDao;
import com.nestef.room.model.Group;
import com.nestef.room.model.Room;
import com.nestef.room.services.GitterApiService;
import com.nestef.room.services.GitterServiceFactory;

import java.util.List;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Noah Steffes on 5/22/18.
 */
public class DataManager {

    private static DataManager sInstance;

    private final GitterApiService mApiService;

    private DataCallback mCallback;

    private RoomDao mRoomDao;

    private GroupDao mGroupDao;

    private DataManager(String authToken, RoomDao roomDao, GroupDao groupDao) {
        mApiService = GitterServiceFactory.makeApiService(authToken);
        mRoomDao = roomDao;
        mGroupDao = groupDao;
    }

    public static DataManager getInstance(String authToken, RoomDao roomDao, GroupDao groupDao) {
        if (sInstance == null) {
            sInstance = new DataManager(authToken, roomDao, groupDao);
        }
        return sInstance;
    }

    public void fetchRooms() {
        mApiService.getRooms().enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                List<Room> rooms = response.body();
                if (rooms != null) {
                    Executors.newSingleThreadExecutor().execute(() -> saveRooms(rooms));

                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<List<Room>> getRooms() {
        return mRoomDao.getRooms();
    }

    public LiveData<List<Room>> getPrivateRooms() {
        return mRoomDao.getPrivateRooms();
    }

    public LiveData<List<Group>> getGroups() {
        mApiService.getGroups().enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                List<Group> groups = response.body();
                if (groups != null) {
                    Executors.newSingleThreadExecutor().execute(() -> saveGroups(groups));
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return mGroupDao.getGroups();
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
        mRoomDao.deleteAllRooms();
        //Add rooms to database
        mRoomDao.insertRooms(rooms);
    }

    private void saveGroups(List<Group> groups) {
        mGroupDao.deleteAllGroups();
        mGroupDao.insertGroups(groups);
    }

    public interface DataCallback {
        void onCall(List<Room> data);
    }
}
