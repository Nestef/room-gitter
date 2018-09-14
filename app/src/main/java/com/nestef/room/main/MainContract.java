package com.nestef.room.main;

import com.nestef.room.base.BaseView;
import com.nestef.room.model.Group;
import com.nestef.room.model.Room;

import java.util.List;

/**
 * Created by Noah Steffes on 3/23/18.
 */

interface MainContract {
    interface RoomViewActions {
        void fetchRooms();
    }

    interface RoomView extends BaseView {
        void showRooms(List<Room> rooms);
    }

    interface SearchViewActions {
        void fetchSuggestions();

        void searchRooms(String query);
    }

    interface SearchView extends BaseView {
        void showSuggestions(List<Room> suggestions);

        void showResults(List<Room> results);
    }

    interface PeopleViewActions {
        void fetchChats();
    }

    interface PeopleView extends BaseView {
        void showChats(List<Room> chats);
    }

    interface GroupsViewActions {
        void fetchGroups();
    }

    interface GroupsView extends BaseView {
        void showGroups(List<Group> groups);
    }

    interface CommunityViewActions {
        void fetchRooms(String groupId);
    }

    interface CommunityView extends BaseView {
        void showRooms(List<Room> joinedRooms);

        void networkError();
    }

}
