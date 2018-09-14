package com.nestef.room.db;

import com.nestef.room.model.Room;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by Noah Steffes on 9/11/18.
 */
@Dao
public interface RoomDao {

    @Insert
    void insertRooms(List<Room> rooms);

    @Update
    void updateRoom(Room... rooms);

    @Query("SELECT * from room_table WHERE oneToOne = 0 ORDER BY unreadItems DESC")
    LiveData<List<Room>> getRooms();

    @Query("SELECT * from room_table WHERE oneToOne = 1 ORDER BY unreadItems DESC")
    LiveData<List<Room>> getPrivateRooms();

    @Query("DELETE FROM room_table")
    void deleteAllRooms();

}
