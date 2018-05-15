package com.nestef.room.util;

import android.net.Uri;

import com.nestef.room.BuildConfig;

import static com.nestef.room.util.Constants.AUTHORIZE_PATH;
import static com.nestef.room.util.Constants.CLIENT_ID_PARAM;
import static com.nestef.room.util.Constants.REDIRCT_PARAM;
import static com.nestef.room.util.Constants.RESPONSE_TYPE;
import static com.nestef.room.util.Constants.RESPONSE_TYPE_PARAM;

/**
 * Created by Noah Steffes on 5/14/18.
 */
public class UriUtils {

    public static String buildAuthRequestUrl() {
        Uri url = Uri.parse(Constants.OAUTH_URL).buildUpon()
                .appendPath(AUTHORIZE_PATH)
                .appendQueryParameter(CLIENT_ID_PARAM, BuildConfig.OAuthId)
                .appendQueryParameter(RESPONSE_TYPE_PARAM, RESPONSE_TYPE)
                .appendQueryParameter(REDIRCT_PARAM, BuildConfig.RedirectUrl)
                .build();
        return url.toString();
    }
}
