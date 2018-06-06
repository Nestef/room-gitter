package com.nestef.room.data;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.nestef.room.model.User;
import com.nestef.room.services.GitterApiService;
import com.nestef.room.services.GitterServiceFactory;

import java.io.IOException;

import static com.nestef.room.util.Constants.AUTH_TOKEN_PREF;
import static com.nestef.room.util.Constants.USER_ID_PREF;

/**
 * Created by Noah Steffes on 5/22/18.
 */
public class PrefManager {

    private static PrefManager sInstance;

    private SharedPreferences mPreferences;

    private GitterApiService mService;

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

    public void saveUserId() {
        if (mService == null) {
            mService = GitterServiceFactory.makeApiService(getAuthToken());
        }
        new UserIdAsyncTask().execute();
    }

    public String getUserId() {
        return mPreferences.getString(USER_ID_PREF, null);
    }

    class UserIdAsyncTask extends AsyncTask<Void, Void, User> {
        @Override
        protected User doInBackground(Void... voids) {
            try {
                return mService.getPersonalProfile().execute().body().get(0);
            } catch (IOException i) {
                i.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString(USER_ID_PREF, user.id);
            editor.apply();
        }
    }


}
