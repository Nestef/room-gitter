package com.nestef.room.data;

import android.content.SharedPreferences;

import static com.nestef.room.util.Constants.AUTH_TOKEN_PREF;

/**
 * Created by Noah Steffes on 5/22/18.
 */
public class PrefManager {

    private static PrefManager sInstance;

    private SharedPreferences mPreferences;

    public static PrefManager getInstance(SharedPreferences sharedPreferences) {
        if (sInstance == null) {
            sInstance = new PrefManager();
        }
        sInstance.mPreferences = sharedPreferences;
        return sInstance;
    }

    public boolean checkUserAuth() {
        return mPreferences.getString(AUTH_TOKEN_PREF, null) != null;
    }

    public void saveAuthToken(String token) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(AUTH_TOKEN_PREF, token);
        editor.apply();
    }

    public String getAuthToken() {
        return mPreferences.getString(AUTH_TOKEN_PREF, null);
    }
}
