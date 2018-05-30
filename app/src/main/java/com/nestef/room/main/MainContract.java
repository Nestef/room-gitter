package com.nestef.room.main;

import android.database.Cursor;

import com.nestef.room.base.BaseView;
import com.nestef.room.model.Room;

import java.util.List;

/**
 * Created by Noah Steffes on 3/23/18.
 */

public interface MainContract {
    interface RoomViewActions {
        void fetchRooms();
    }

    interface RoomView extends BaseView {
        void showRooms(Cursor rooms);

    }

    interface SearchViewActions {

    }

    interface SearchView extends BaseView {

    }

    interface PeopleViewActions {
        void fetchChats();
    }

    interface PeopleView extends BaseView {
        void showChats(Cursor chats);
    }

    interface GroupsViewActions {
        void fetchGroups();
    }

    interface GroupsView extends BaseView {
        void showGroups(Cursor groups);
    }

    interface CommunityViewActions {
        void fetchRooms();

        void fetchJoinedRooms();

        void fetchUnjoinedRooms();
    }

    interface CommunityView extends BaseView {
        void showJoinedRooms(List<Room> joinedRooms);

        void showJoinedRoomsEmpty();

        void showUnjoinedRooms(List<Room> unjoinedRooms);
    }

}
