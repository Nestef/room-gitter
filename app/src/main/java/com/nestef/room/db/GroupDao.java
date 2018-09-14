package com.nestef.room.db;

import com.nestef.room.model.Group;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * Created by Noah Steffes on 9/11/18.
 */
@Dao
public interface GroupDao {

    @Insert
    void insertGroups(List<Group> groups);

    @Query("SELECT * from group_table")
    LiveData<List<Group>> getGroups();

    @Query("DELETE FROM group_table")
    void deleteAllGroups();
}
