package com.nestef.room.services;

import com.nestef.room.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

/**
 * Created by Noah Steffes on 6/1/18.
 */
public interface GitterStreamingApi {

    @GET("/v1/rooms/{roomId}/chatMessages")
    @Streaming
    Call<List<Message>> getMessageStream(@Path("roomId") String roomId);

    @GET("v1/rooms/{roomId}/events")
    @Streaming
    Call<List<Message>> getEventStream(@Path("roomId") String roomId);
}
