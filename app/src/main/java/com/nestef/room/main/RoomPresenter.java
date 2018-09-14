package com.nestef.room.main;


import com.nestef.room.base.BasePresenter;
import com.nestef.room.data.DataManager;
import com.nestef.room.model.Room;

import java.util.List;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

/**
 * Created by Noah Steffes on 3/31/18.
 */

public class RoomPresenter extends BasePresenter<MainContract.RoomView> implements MainContract.RoomViewActions, LifecycleOwner {

    private static final String TAG = RoomPresenter.class.getName();

    private DataManager mDataManager;

    private LifecycleRegistry mLifecycleRegistry;

    private Observer<List<Room>> mObserver = rooms -> {
        if (rooms != null) {
            mView.hideLoadingIndicator();
            mView.showRooms(rooms);
        } else {
            mView.hideLoadingIndicator();
            mView.showEmpty();
        }
    };

    RoomPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void fetchRooms() {
        mView.showLoadingIndicator();
        mDataManager.getRooms();
        LiveData<List<Room>> rooms = mDataManager.getRooms();
        if (rooms != null) {
            rooms.observe(this, mObserver);
        }
    }

    @Override
    public void setView(MainContract.RoomView view) {
        super.setView(view);
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @Override
    public void unsetView() {
        super.unsetView();
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED);
    }

    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }
}
