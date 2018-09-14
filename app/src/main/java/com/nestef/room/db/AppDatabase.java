package com.nestef.room.db;

import android.content.Context;

import com.nestef.room.model.Group;
import com.nestef.room.model.Room;

import androidx.room.Database;

/**
 * Created by Noah Steffes on 9/11/18.
 */
@Database(entities = {Room.class, Group.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {

    private static AppDatabase sINSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (sINSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = androidx.room.Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "App-Database").build();
                }
            }
        }
        return sINSTANCE;
    }

    public abstract RoomDao roomDao();

    public abstract GroupDao groupDao();
}
