package com.nestef.room.services;

import com.nestef.room.model.Ban;
import com.nestef.room.model.Collaborator;
import com.nestef.room.model.Event;
import com.nestef.room.model.Group;
import com.nestef.room.model.Issue;
import com.nestef.room.model.Message;
import com.nestef.room.model.Org;
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
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Noah Steffes on 5/4/18.
 */
public interface GitterApiService {

    @GET("/rooms")
    Call<List<Room>> getRooms();

    @POST("/rooms")
    Call<RoomResponse> getRoomIdByUri(@Field("uri") String roomUri);

    @POST("/user/{userId}/rooms")
    Call joinRoom(@Path("userId") String userId, @Field("id") String roomId);

    @DELETE("rooms/{roomId}/users/{userId}")
    Call leaveRoom(@Path("roomId") String roomId, @Path("userId") String userId);

    @PUT("/rooms/{roomId}")
    Call updateRoom(@Path("roomId") String roomId, @Field("topic") String topic, @Field("noIndex") boolean noIndex, @Field("tags") String tags);

    @DELETE("/rooms/{roomId}")
    Call deleteRoom(@Path("roomId") String roomId);

    @GET("/rooms/{roomId}/meta/welcome-message")
    Call<WelcomeMessage> getWelcomeMessage(@Path("roomId") String roomId);

    @GET("/rooms/{roomId}/collaborators")
    Call<List<Collaborator>> getCollaborators(@Path("roomId") String roomId);

    @GET("/rooms/{roomId}/suggestedRooms")
    Call<List<Room>> getSuggestedRooms(@Path("roomId") String roomId);

    @GET("/rooms/{roomID}/bans")
    Call<List<Ban>> getBanned(@Path("roomId") String roomId);

    @GET("/rooms/{roomId}/events")
    Call<List<Event>> getEvents(@Path("roomId") String roomId);

    @GET("/rooms/{roomId}/issues")
    Call<List<Issue>> getIssues(@Path("roomId") String roomId);

    @GET("/rooms/{roomId}/users")
    Call<List<User>> getUsers(@Path("roomId") String roomId);

    @GET("/rooms/{roomId}/chatMessages")
    Call<List<Message>> getMessages(@Path("roomId") String roomId);

    @GET("/rooms/{roomId}/chatMessages/{messageId}")
    Call<Message> getMessageById(@Path("roomId") String roomID, @Path("messageId") String messageId);

    @POST("/rooms/{roomId}/chatMessages")
    Call sendMessage(@Path("roomId") String roomId, @Field("text") String text);

    @PUT("/rooms/{roomId}/chatMessages/{messageId}")
    Call editMessage(@Path("roomId") String roomId, @Path("messageId") String messageId, @Field("text") String text);

    @GET("/user")
    Call<List<User>> getPersonalProfile();

    @GET("/user/{userId}")
    Call<List<User>> getUserById(@Path("userId") String userId);

    @GET("/user/{userId}/rooms")
    Call<List<Room>> getUserRooms(@Path("userId") String userId);

    @GET("/user/{userId}/rooms/{roomId}/unreadItems")
    Call<UnreadResponse> getUnread(@Path("userId") String userId, @Path("roomId") String roomId);

    @POST("/user/{userId}/rooms/{roomId}/unreadItems")
    Call readMessages(@Path("userId") String userId, @Path("roomId") String roomId, @Field("chat") List<String> messageIds);

    @GET("/user/{userId}/orgs")
    Call<List<Org>> getUserOrgs(@Path("userId") String userId);

    @GET("/user/{userId}/repos")
    Call<List<Repo>> getUserRepos(@Path("userId") String userId);

    @GET("/user/{userId}/channels")
    Call<List<Room>> getUserChannels(@Path("userId") String userId);

    @GET("/users/{username}")
    Call<UserProfile> getUserProfile(@Path("username") String username);

    @GET("/groups")
    Call<List<Group>> getGroups();

    @GET("/groups/{groupId}/rooms")
    Call<List<Room>> getGroupRooms(@Path("groupId") String groupId);

}
