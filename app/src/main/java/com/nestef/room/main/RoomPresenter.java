package com.nestef.room.main;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nestef.room.base.BasePresenter;
import com.nestef.room.model.Room;

import java.util.List;

/**
 * Created by Noah Steffes on 3/31/18.
 */

public class RoomPresenter extends BasePresenter<MainContract.RoomView> implements MainContract.RoomViewActions {

    private static final String TAG = RoomPresenter.class.getName();

    @Override
    public void fetchRooms() {
        String json = "[{\"id\":\"58094d7fd73408ce4f2fb63d\",\"name\":\"grin/Lobby\",\"topic\":\"Discuss grin, a minimal implementation of MimbleWimble - http://grin-tech.org\",\"avatarUrl\":\"https://avatars-04.gitter.im/group/i/58094d7fd73408ce4f2fb63c\",\"uri\":\"grin_community/Lobby\",\"oneToOne\":false,\"userCount\":750,\"unreadItems\":100,\"mentions\":0,\"lastAccessTime\":\"2018-04-18T18:41:42.964Z\",\"lurk\":false,\"url\":\"/grin_community/Lobby\",\"githubType\":\"REPO_CHANNEL\",\"security\":\"PUBLIC\",\"noindex\":false,\"tags\":[],\"roomMember\":true,\"groupId\":\"58094d7fd73408ce4f2fb63c\",\"public\":true},\n" +
                "\n" +
                "{\"id\":\"576c4d75c2f0db084a1f99ae\",\"name\":\"flutter/flutter\",\"topic\":\"Framework for building high-performance, high-fidelity iOS and Android apps.\",\"avatarUrl\":\"https://avatars-01.gitter.im/group/iv/3/576c4d75c2f0db084a1f99ad\",\"uri\":\"flutter/flutter\",\"oneToOne\":false,\"userCount\":3042,\"unreadItems\":100,\"mentions\":0,\"lastAccessTime\":\"2018-03-14T20:14:33.677Z\",\"lurk\":false,\"url\":\"/flutter/flutter\",\"githubType\":\"REPO\",\"security\":\"PUBLIC\",\"noindex\":false,\"tags\":[],\"roomMember\":true,\"groupId\":\"576c4d75c2f0db084a1f99ad\",\"public\":true,\"v\":1},\n" +
                "\n" +
                "{\"id\":\"5a9f0d84d73408ce4f902d97\",\"name\":\"Nestef/Lobby\",\"topic\":\"\",\"avatarUrl\":\"https://avatars-02.gitter.im/group/i/5a9f0d84d73408ce4f902d96\",\"uri\":\"Nestef/Lobby\",\"oneToOne\":false,\"userCount\":1,\"unreadItems\":0,\"mentions\":0,\"lastAccessTime\":\"2018-04-18T18:52:36.416Z\",\"lurk\":false,\"url\":\"/Nestef/Lobby\",\"githubType\":\"REPO_CHANNEL\",\"security\":\"PUBLIC\",\"noindex\":false,\"tags\":[],\"roomMember\":true,\"groupId\":\"5a9f0d84d73408ce4f902d96\",\"public\":true},\n" +
                "\n" +
                "{\"id\":\"5a9f0d9dd73408ce4f902d9e\",\"name\":\"Nestef/nestef\",\"topic\":\"This room is used for testing the Gitter Api \",\"avatarUrl\":\"https://avatars-02.gitter.im/group/i/5a9f0d84d73408ce4f902d96\",\"uri\":\"Nestef/nestef\",\"oneToOne\":false,\"userCount\":1,\"unreadItems\":0,\"mentions\":0,\"lastAccessTime\":\"2018-04-18T18:55:26.000Z\",\"favourite\":1,\"lurk\":false,\"url\":\"/Nestef/nestef\",\"githubType\":\"REPO_CHANNEL\",\"security\":\"PRIVATE\",\"noindex\":false,\"tags\":[],\"roomMember\":true,\"groupId\":\"5a9f0d84d73408ce4f902d96\",\"public\":false},\n" +
                "\n" +
                "{\"id\":\"53f3d83c163965c9bc1fefa1\",\"name\":\"ReactiveX/RxJava\",\"topic\":\"RxJava – Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM.\",\"avatarUrl\":\"https://avatars-02.gitter.im/group/iv/3/57542c89c43b8c60197742b4\",\"uri\":\"ReactiveX/RxJava\",\"oneToOne\":false,\"userCount\":1340,\"unreadItems\":34,\"mentions\":0,\"lastAccessTime\":\"2018-03-19T14:20:00.456Z\",\"lurk\":false,\"url\":\"/ReactiveX/RxJava\",\"githubType\":\"REPO\",\"security\":\"PUBLIC\",\"noindex\":false,\"tags\":[\"java\",\"rxjava\",\"reactive\",\"extensions\",\"jvm\",\"library\"],\"roomMember\":true,\"groupId\":\"57542c89c43b8c60197742b4\",\"public\":true,\"v\":201},\n" +
                "{\"id\":\"5790a3a2c2f0db084a24004d\",\"name\":\"gitterHQ/api\",\"topic\":\"Gitter API and Libraries\",\"avatarUrl\":\"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\"uri\":\"gitterHQ/api\",\"oneToOne\":false,\"userCount\":85,\"unreadItems\":0,\"mentions\":0,\"lastAccessTime\":\"2018-04-18T18:52:35.053Z\",\"lurk\":false,\"url\":\"/gitterHQ/api\",\"githubType\":\"REPO_CHANNEL\",\"security\":\"PUBLIC\",\"premium\":true,\"noindex\":false,\"tags\":[],\"roomMember\":true,\"groupId\":\"57542c12c43b8c601976fa66\",\"public\":true}]";
        try {
            ObjectMapper mapper = new ObjectMapper();
            Log.d(TAG, "fetchRooms: json");
            List<Room> rooms = mapper.readValue(json, new TypeReference<List<Room>>() {
            });
            mView.showRooms(rooms);
            mView.showEmpty();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
