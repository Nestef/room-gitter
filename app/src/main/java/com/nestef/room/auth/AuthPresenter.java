package com.nestef.room.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

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

import static com.nestef.room.util.Constants.APP_SCHEME;
import static com.nestef.room.util.Constants.AUTH_SHARED_PREF;
import static com.nestef.room.util.Constants.AUTH_TOKEN_PREF;
import static com.nestef.room.util.Constants.GRANT_TYPE;
import static com.nestef.room.util.Constants.RESPONSE_TYPE;
import static com.nestef.room.util.Constants.TOKEN_URL;

/**
 * Created by Noah Steffes on 3/23/18.
 */

public class AuthPresenter extends BasePresenter<AuthContract.AuthView> implements AuthContract.ViewActions {

    private static final String TAG = "AuthPresenter";

    @Override
    public void startMainActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        context.startActivity(intent);
        ((AppCompatActivity) context).finish();
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
                            saveToken(token, context.getSharedPreferences(AUTH_SHARED_PREF, Context.MODE_PRIVATE));
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

    private void saveToken(String token, SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AUTH_TOKEN_PREF, token);
        editor.apply();
    }

    @Override
    public boolean checkUserAuth(SharedPreferences sharedPreferences) {
        return sharedPreferences.getString(AUTH_TOKEN_PREF, null) != null;
    }

}
