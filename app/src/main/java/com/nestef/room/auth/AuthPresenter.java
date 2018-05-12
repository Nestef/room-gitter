package com.nestef.room.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.nestef.room.BuildConfig;
import com.nestef.room.base.BasePresenter;
import com.nestef.room.main.MainActivity;
import com.nestef.room.model.AuthResponse;
import com.nestef.room.services.GitterAuthService;

import javax.annotation.ParametersAreNonnullByDefault;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Noah Steffes on 3/23/18.
 */

public class AuthPresenter extends BasePresenter<AuthContract.AuthView> implements AuthContract.ViewActions {

    private static final String AUTH_SHARED_PREF = "room_auth";
    private static final String AUTH_TOKEN_PREF = "auth_token";
    private static final String OAUTH_URL = "https://gitter.im/login/oauth/";
    private static final String TOKEN_URL = "https://gitter.im/";
    private static final String AUTHORIZE_PATH = "authorize";
    private static final String CLIENT_ID_PARAM = "client_id";
    private static final String RESPONSE_TYPE_PARAM = "response_type";
    private static final String RESPONSE_TYPE = "code";
    private static final String REDIRCT_PARAM = "redirect_uri";
    private static final String TAG = "AuthPresenter";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String APP_SCHEME = "app";

    @Override
    public void startMainActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String buildAuthRequestUrl() {
        Uri url = Uri.parse(OAUTH_URL).buildUpon()
                .appendPath(AUTHORIZE_PATH)
                .appendQueryParameter(CLIENT_ID_PARAM, BuildConfig.OAuthId)
                .appendQueryParameter(RESPONSE_TYPE_PARAM, RESPONSE_TYPE)
                .appendQueryParameter(REDIRCT_PARAM, BuildConfig.RedirectUrl)
                .build();
        return url.toString();
    }

    @Override
    public boolean handleUri(Uri uri, final Context context) {

        if (uri.getScheme().equals(APP_SCHEME)) {

            final String code = uri.getQueryParameter(RESPONSE_TYPE);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(TOKEN_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            GitterAuthService authService = retrofit.create(GitterAuthService.class);

            authService
                    .postAuthCode(BuildConfig.OAuthId, BuildConfig.OAuthSecret, code, BuildConfig.RedirectUrl, GRANT_TYPE)
                    .enqueue(new Callback<AuthResponse>() {

                        @Override
                        @ParametersAreNonnullByDefault
                        public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                            String token = response.body().accessToken;
                            SharedPreferences sharedPref = context.getSharedPreferences(AUTH_SHARED_PREF, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(AUTH_TOKEN_PREF, token);
                            editor.apply();
                            startMainActivity(context);
                        }

                        @Override
                        @ParametersAreNonnullByDefault
                        public void onFailure(Call<AuthResponse> call, Throwable t) {
                            //Todo: add error handling
                        }
                    });

            return true;
        }
        return false;
    }

    @Override
    public boolean checkUserAuth(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AUTH_SHARED_PREF, Context.MODE_PRIVATE);
        return sharedPreferences.getString(AUTH_TOKEN_PREF, null) != null;
    }

}
