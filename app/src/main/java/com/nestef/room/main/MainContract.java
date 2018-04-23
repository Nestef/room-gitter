package com.nestef.room.main;

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
        void showRooms(List<Room> rooms);

    }

    interface SearchViewActions {

    }

    interface SearchView extends BaseView {

    }

    interface PeopleViewActions {

    }

    interface PeopleView extends BaseView {

    }

    interface CommunityViewActions {

    }

    interface CommunityView extends BaseView {

    }
}
