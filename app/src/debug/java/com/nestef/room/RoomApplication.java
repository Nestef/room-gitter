package com.nestef.room;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Noah Steffes on 4/12/18.
 */
public class RoomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
