package com.nestef.room.auth;

import android.net.Uri;

import com.nestef.room.BuildConfig;
import com.nestef.room.base.BasePresenter;
import com.nestef.room.data.PrefManager;
import com.nestef.room.model.AuthResponse;
import com.nestef.room.services.GitterAuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.nestef.room.util.Constants.APP_SCHEME;
import static com.nestef.room.util.Constants.GRANT_TYPE;
import static com.nestef.room.util.Constants.RESPONSE_TYPE;
import static com.nestef.room.util.Constants.TOKEN_URL;

/**
 * Created by Noah Steffes on 3/23/18.
 */

public class AuthPresenter extends BasePresenter<AuthContract.AuthView> implements AuthContract.ViewActions {

    private static final String TAG = "AuthPresenter";

    private PrefManager mPrefManager;

    public AuthPresenter(PrefManager prefManager) {
        mPrefManager = prefManager;
    }

    @Override
    public boolean handleUri(Uri uri) {

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
                        public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                            String token = response.body().accessToken;
                            mPrefManager.saveAuthToken(token);
                            mPrefManager.saveUserId();
                            mView.startMainActivity();
                        }

                        @Override
                        public void onFailure(Call<AuthResponse> call, Throwable t) {
                            //Todo: add error handling
                        }
                    });
            return true;
        }
        return false;
    }

    @Override
    public boolean checkUserAuth() {
        return mPrefManager.checkUserAuth();
    }

}
