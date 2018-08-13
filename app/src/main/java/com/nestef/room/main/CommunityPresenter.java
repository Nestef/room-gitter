package com.nestef.room.main;

import com.nestef.room.base.BasePresenter;
import com.nestef.room.data.DataManager;
import com.nestef.room.model.Room;

import java.util.Collections;
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
        if (data != null) {
            Collections.sort(data, (o1, o2) -> {
                if (o1.roomMember && o2.roomMember) return 0;
                else if (!o1.roomMember && !o2.roomMember) return 0;
                else if (o1.roomMember && !o2.roomMember) return -1;
                else if (!o1.roomMember && o2.roomMember) return 1;
                return 0;
            });
            if (data.size() > 0) {
                mView.hideLoadingIndicator();
                mView.showRooms(data);
            } else {
                mView.hideLoadingIndicator();
                mView.showEmpty();
            }

        } else {
            mView.hideLoadingIndicator();
            mView.networkError();
        }
    }
}

