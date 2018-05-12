package com.nestef.room.services;

import com.nestef.room.model.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Noah Steffes on 5/11/18.
 */
public interface GitterAuthService {

    @FormUrlEncoded
    @POST("/login/oauth/token")
    Call<AuthResponse> postAuthCode(@Field("client_id") String clientId,
                                    @Field("client_secret") String clientSecret,
                                    @Field("code") String code,
                                    @Field("redirect_uri") String redirectUri,
                                    @Field("grant_type") String grant_type);
}
