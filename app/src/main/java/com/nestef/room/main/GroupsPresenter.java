package com.nestef.room.main;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nestef.room.base.BasePresenter;
import com.nestef.room.model.Group;

import java.util.List;

/**
 * Created by Noah Steffes on 4/11/18.
 */
public class GroupsPresenter extends BasePresenter<MainContract.GroupsView> implements MainContract.GroupsViewActions {
    @Override
    public void fetchGroups() {
        String json = "[{\"id\":\"57542c12c43b8c601976fa66\",\"name\":\"gitterHQ\",\"uri\":\"gitterHQ\",\"homeUri\":\"gitterHQ/home\",\"backedBy\":{\"type\":\"GH_ORG\",\"linkPath\":\"gitterHQ\"},\"avatarUrl\":\"https://avatars-01.gitter.im/group/iv/3/57542c12c43b8c601976fa66\",\"forumId\":\"57f37228a625b4386c9f1de5\"},{\"id\":\"57542c89c43b8c60197742b4\",\"name\":\"ReactiveX\",\"uri\":\"ReactiveX\",\"homeUri\":\"ReactiveX/home\",\"backedBy\":{\"type\":\"GH_ORG\",\"linkPath\":\"ReactiveX\"},\"avatarUrl\":\"https://avatars-02.gitter.im/group/iv/3/57542c89c43b8c60197742b4\"},{\"id\":\"5a9f0d84d73408ce4f902d96\",\"name\":\"Nestef\",\"uri\":\"Nestef\",\"homeUri\":\"Nestef\",\"backedBy\":{\"type\":null},\"avatarUrl\":\"https://avatars-02.gitter.im/group/i/5a9f0d84d73408ce4f902d96\"},{\"id\":\"576c4d75c2f0db084a1f99ad\",\"name\":\"flutter\",\"uri\":\"flutter\",\"homeUri\":\"flutter\",\"backedBy\":{\"type\":\"GH_ORG\",\"linkPath\":\"flutter\"},\"avatarUrl\":\"https://avatars-01.gitter.im/group/iv/3/576c4d75c2f0db084a1f99ad\"},{\"id\":\"58094d7fd73408ce4f2fb63c\",\"name\":\"grin\",\"uri\":\"grin_community\",\"homeUri\":\"grin_community\",\"backedBy\":{\"type\":null},\"avatarUrl\":\"https://avatars-04.gitter.im/group/i/58094d7fd73408ce4f2fb63c\"}]";
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Group> rooms = mapper.readValue(json, new TypeReference<List<Group>>() {
            });
            mView.showGroups(rooms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
