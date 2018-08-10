package com.nestef.room;

import android.app.Application;
import android.os.Build;

import com.facebook.stetho.Stetho;

/**
 * Created by Noah Steffes on 4/12/18.
 */
public class RoomApplication extends Application {
    public static boolean isRoboUnitTest() {
        return "robolectric".equals(Build.FINGERPRINT);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (!isRoboUnitTest()) {
            Stetho.initializeWithDefaults(this);
        }
    }
}
