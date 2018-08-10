package com.nestef.room.auth;


import android.net.Uri;

import com.nestef.room.base.BaseView;

/**
 * Created by Noah Steffes on 3/23/18.
 */

public interface AuthContract {
    interface ViewActions {

        boolean checkUserAuth();

        boolean handleUri(Uri uri);

    }

    interface AuthView extends BaseView {

        void startMainActivity();

        void showNetworkError();
    }
}
