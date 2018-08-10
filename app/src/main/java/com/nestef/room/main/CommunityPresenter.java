package com.nestef.room.main;

import com.nestef.room.base.BasePresenter;
import com.nestef.room.data.DataManager;
import com.nestef.room.model.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noah Steffes on 5/2/18.
 */
public class CommunityPresenter extends BasePresenter<MainContract.CommunityView> implements MainContract.CommunityViewActions, DataManager.DataCallback {

    private DataManager mDataManager;

    CommunityPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void fetchRooms(String groupId) {
        mView.showLoadingIndicator();
        mDataManager.getCommunityRooms(groupId, this);
    }

    @Override
    public void onCall(List<Room> data) {
        List<Room> joined = new ArrayList<>();
        List<Room> unJoined = new ArrayList<>();
        for (Room room : data) {
            if (room.roomMember) joined.add(room);
            else unJoined.add(room);
        }
        if (joined.size() == 0 && unJoined.size() == 0) {
            mView.showEmpty();
        } else {
            if (joined.size() > 0) {
                mView.hideLoadingIndicator();
                mView.showJoinedRooms(joined);
            } else {
                mView.hideLoadingIndicator();
                mView.showJoinedRoomsEmpty();
            }
            if (unJoined.size() > 0) {
                mView.hideLoadingIndicator();
                mView.showUnjoinedRooms(unJoined);
            }
        }
    }
}
