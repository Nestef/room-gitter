package com.nestef.room.services;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

/**
 * Created by Noah Steffes on 6/1/18.
 */
public interface GitterStreamingService {


    @GET("/v1/rooms/{roomId}/chatMessages/")
    @Streaming
    Observable<ResponseBody> getMessageStream(@Path("roomId") String roomId);

    @GET("v1/rooms/{roomId}/events/")
    @Streaming
    Observable<ResponseBody> getEventStream(@Path("roomId") String roomId);
}
