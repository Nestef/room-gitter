package com.nestef.room.services;

import com.nestef.room.model.Ban;
import com.nestef.room.model.Collaborator;
import com.nestef.room.model.Event;
import com.nestef.room.model.Group;
import com.nestef.room.model.Issue;
import com.nestef.room.model.Message;
import com.nestef.room.model.Org;
import com.nestef.room.model.QueryResponse;
import com.nestef.room.model.Repo;
import com.nestef.room.model.Room;
import com.nestef.room.model.RoomResponse;
import com.nestef.room.model.UnreadResponse;
import com.nestef.room.model.User;
import com.nestef.room.model.UserProfile;
import com.nestef.room.model.WelcomeMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Noah Steffes on 5/4/18.
 */
public interface GitterApiService {

    @GET("/v1/rooms")
    Call<List<Room>> getRooms();

    @GET("v1/rooms")
    Call<QueryResponse> searchRooms(@Query("q") String query);

    @GET("/v1/rooms/{roomId}")
    Call<Room> getRoomById(@Path("roomId") String roomId);

    @FormUrlEncoded
    @POST("/v1/rooms")
    Call<RoomResponse> getRoomIdByUri(@Field("uri") String roomUri);

    @FormUrlEncoded
    @POST("/v1/user/{userId}/rooms")
    Call<Void> joinRoom(@Path("userId") String userId, @Field("id") String roomId);

    @DELETE("/v1/rooms/{roomId}/users/{userId}")
    Call<Void> leaveRoom(@Path("roomId") String roomId, @Path("userId") String userId);

    @FormUrlEncoded
    @PUT("/v1/rooms/{roomId}")
    Call<Void> updateRoom(@Path("roomId") String roomId, @Field("topic") String topic, @Field("noIndex") boolean noIndex, @Field("tags") String tags);

    @DELETE("/v1/rooms/{roomId}")
    Call<Void> deleteRoom(@Path("roomId") String roomId);

    @GET("/v1/rooms/{roomId}/meta/welcome-message")
    Call<WelcomeMessage> getWelcomeMessage(@Path("roomId") String roomId);

    @GET("/v1/rooms/{roomId}/collaborators")
    Call<List<Collaborator>> getCollaborators(@Path("roomId") String roomId);

    @GET("/v1/rooms/{roomId}/suggestedRooms")
    Call<List<Room>> getSuggestedRooms(@Path("roomId") String roomId);

    @GET("/v1/rooms/{roomID}/bans")
    Call<List<Ban>> getBanned(@Path("roomId") String roomId);

    @GET("/v1/rooms/{roomId}/events")
    Call<List<Event>> getEvents(@Path("roomId") String roomId);

    @GET("/v1/rooms/{roomId}/issues")
    Call<List<Issue>> getIssues(@Path("roomId") String roomId);

    @GET("/v1/rooms/{roomId}/users")
    Call<List<User>> getUsers(@Path("roomId") String roomId);

    @GET("/v1/rooms/{roomId}/chatMessages")
    Call<List<Message>> getMessages(@Path("roomId") String roomId);

    @GET("/v1/rooms/{roomId}/chatMessages")
    Call<List<Message>> getMessagesBeforeMessage(@Path("roomId") String roomId, @Query("beforeId") String beforeId);

    @GET("/v1/rooms/{roomId}/chatMessages/{messageId}")
    Call<Message> getMessageById(@Path("roomId") String roomID, @Path("messageId") String messageId);

    @FormUrlEncoded
    @POST("/v1/rooms/{roomId}/chatMessages")
    Call<Message> sendMessage(@Path("roomId") String roomId, @Field("text") String text);

    @FormUrlEncoded
    @PUT("/v1/rooms/{roomId}/chatMessages/{messageId}")
    Call<Message> editMessage(@Path("roomId") String roomId, @Path("messageId") String messageId, @Field("text") String text);

    @GET("/v1/user")
    Call<List<User>> getPersonalProfile();

    @GET("/v1/user/me/suggestedRooms")
    Call<List<Room>> getSuggestedRooms();

    @GET("/v1/user/{userId}")
    Call<List<User>> getUserById(@Path("userId") String userId);

    @GET("/v1/user/{userId}/rooms")
    Call<List<Room>> getUserRooms(@Path("userId") String userId);

    @GET("/v1/user/{userId}/rooms/{roomId}/unreadItems")
    Call<UnreadResponse> getUnread(@Path("userId") String userId, @Path("roomId") String roomId);

    @FormUrlEncoded
    @POST("/v1/user/{userId}/rooms/{roomId}/unreadItems")
    Call<Void> readMessages(@Path("userId") String userId, @Path("roomId") String roomId, @Field("chat") List<String> messageIds);

    @GET("/v1/user/{userId}/orgs")
    Call<List<Org>> getUserOrgs(@Path("userId") String userId);

    @GET("/v1/user/{userId}/repos")
    Call<List<Repo>> getUserRepos(@Path("userId") String userId);

    @GET("/v1/user/{userId}/channels")
    Call<List<Room>> getUserChannels(@Path("userId") String userId);

    @GET("/v1/users/{username}")
    Call<UserProfile> getUserProfile(@Path("username") String username);

    @GET("/v1/groups")
    Call<List<Group>> getGroups();

    @GET("/v1/groups/{groupId}/rooms")
    Call<List<Room>> getGroupRooms(@Path("groupId") String groupId);

}
