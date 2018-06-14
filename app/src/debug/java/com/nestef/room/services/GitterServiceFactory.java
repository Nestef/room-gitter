package com.nestef.room.services;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.nestef.room.util.Constants.API_BASE_URL;
import static com.nestef.room.util.Constants.STREAMING_BASE_URL;
import static com.nestef.room.util.Constants.TOKEN_URL;

/**
 * Created by Noah Steffes on 5/22/18.
 */
public class GitterServiceFactory {

    public static GitterApiService makeApiService(String token) {
        return new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(makeOkhttpClient(token))
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(GitterApiService.class);
    }

    public static GitterAuthService makeAuthService() {
        return new Retrofit.Builder()
                .baseUrl(TOKEN_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(GitterAuthService.class);

    }

    public static GitterStreamingService makeStreamingService(final String token) {
        return new Retrofit.Builder()
                .baseUrl(STREAMING_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient.Builder()
                        .readTimeout(0, TimeUnit.SECONDS)
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request original = chain.request();

                                Request request = original.newBuilder()
                                        .header("Authorization", "Bearer " + token)
                                        .build();
                                return chain.proceed(request);
                            }
                        })
                        .addNetworkInterceptor(new StethoInterceptor())
                        .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                        .build())
                .build()
                .create(GitterStreamingService.class);

    }


    private static OkHttpClient makeOkhttpClient(final String token) {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request = original.newBuilder()
                                .header("Authorization", "Bearer " + token)
                                .build();
                        return chain.proceed(request);
                    }
                })
                .addNetworkInterceptor(new StethoInterceptor())
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();

    }
}
