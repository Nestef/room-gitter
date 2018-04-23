package com.nestef.room.main;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nestef.room.base.BasePresenter;
import com.nestef.room.model.Room;

import java.util.List;

/**
 * Created by Noah Steffes on 4/1/18.
 */

public class PeoplePresenter extends BasePresenter<MainContract.PeopleView> implements MainContract.PeopleViewActions {
    private static final String TAG = PeoplePresenter.class.getName();

    @Override
    public void fetchChats() {
        String json = "[{\"id\":\"5a21c167d73408ce4f814972\",\"name\":\"Grin\",\"topic\":\"\",\"avatarUrl\":\"https://avatars-01.gitter.im/g/u/GrowWithGrin_twitter\",\"oneToOne\":true,\"userCount\":2,\"user\":{\"id\":\"5823a619d73408ce4f34d496\",\"username\":\"GrowWithGrin_twitter\",\"displayName\":\"Grin\",\"url\":\"/GrowWithGrin_twitter\",\"avatarUrl\":\"https://avatars-01.gitter.im/g/u/GrowWithGrin_twitter\",\"avatarUrlSmall\":\"https://pbs.twimg.com/profile_images/775875154035343360/JrAmZBs9_bigger.jpg\",\"avatarUrlMedium\":\"https://pbs.twimg.com/profile_images/775875154035343360/JrAmZBs9.jpg\"},\"unreadItems\":0,\"mentions\":0,\"lastAccessTime\":\"2018-03-06T21:48:32.967Z\",\"lurk\":false,\"url\":\"/GrowWithGrin_twitter\",\"githubType\":\"ONETOONE\",\"noindex\":false,\"tags\":[],\"roomMember\":true,\"groupId\":null,\"public\":false},{\"id\":\"5a21c81bd73408ce4f8149f4\",\"name\":\"Monero Mining\",\"topic\":\"\",\"avatarUrl\":\"https://avatars-03.gitter.im/g/u/moneromining_twitter\",\"oneToOne\":true,\"userCount\":2,\"user\":{\"id\":\"5a157564d73408ce4f7fcb41\",\"username\":\"moneromining_twitter\",\"displayName\":\"Monero Mining\",\"url\":\"/moneromining_twitter\",\"avatarUrl\":\"https://avatars-03.gitter.im/g/u/moneromining_twitter\",\"avatarUrlSmall\":\"https://pbs.twimg.com/profile_images/912277084973752320/OtWQrb0Z_bigger.jpg\",\"avatarUrlMedium\":\"https://pbs.twimg.com/profile_images/912277084973752320/OtWQrb0Z.jpg\"},\"unreadItems\":0,\"mentions\":0,\"lastAccessTime\":\"2018-03-17T17:04:12.813Z\",\"lurk\":false,\"url\":\"/moneromining_twitter\",\"githubType\":\"ONETOONE\",\"noindex\":false,\"tags\":[],\"roomMember\":true,\"groupId\":null,\"public\":false},{\"id\":\"5aa16af3d73408ce4f90782b\",\"name\":\"android-reverser\",\"topic\":\"\",\"avatarUrl\":\"https://avatars-01.gitter.im/gh/uv/3/android-reverser\",\"oneToOne\":true,\"userCount\":2,\"user\":{\"id\":\"57c86a0840f3a6eec062c307\",\"username\":\"android-reverser\",\"displayName\":\"android-reverser\",\"url\":\"/android-reverser\",\"avatarUrl\":\"https://avatars-01.gitter.im/gh/uv/3/android-reverser\",\"avatarUrlSmall\":\"https://avatars2.githubusercontent.com/u/21362404?v=3&s=60\",\"avatarUrlMedium\":\"https://avatars2.githubusercontent.com/u/21362404?v=3&s=128\",\"gv\":\"3\"},\"unreadItems\":0,\"mentions\":0,\"lastAccessTime\":\"2018-03-11T21:41:31.423Z\",\"lurk\":false,\"url\":\"/android-reverser\",\"githubType\":\"ONETOONE\",\"noindex\":false,\"tags\":[],\"roomMember\":true,\"groupId\":null,\"public\":false}]";

        try {
            ObjectMapper mapper = new ObjectMapper();
            Log.d(TAG, "fetchChats: json");
            List<Room> rooms = mapper.readValue(json, new TypeReference<List<Room>>() {
            });
            mView.showChats(rooms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
