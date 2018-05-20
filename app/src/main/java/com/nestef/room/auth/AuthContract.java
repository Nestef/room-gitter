package com.nestef.room.auth;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.nestef.room.base.BaseView;

/**
 * Created by Noah Steffes on 3/23/18.
 */

public interface AuthContract {
    interface ViewActions {

        boolean checkUserAuth(SharedPreferences sharedPreferences);

        void startMainActivity(Context context);

        boolean handleUri(Uri uri, Context context);

    }

    interface AuthView extends BaseView {

    }
}
