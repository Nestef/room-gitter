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
 * Created by Noah Steffes on 4/1/18.
 */

public class PeoplePresenter extends BasePresenter<MainContract.PeopleView> implements MainContract.PeopleViewActions, LifecycleOwner {

    private static final String TAG = PeoplePresenter.class.getName();

    private DataManager mDataManager;

    private Observer<List<Room>> mObserver = chats -> {
        if (chats != null) {
            mView.hideLoadingIndicator();
            mView.showChats(chats);
        } else {
            mView.hideLoadingIndicator();
            mView.showEmpty();
        }
    };

    private LifecycleRegistry mLifecycleRegistry;

    public PeoplePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void fetchChats() {
        mView.showLoadingIndicator();
        mDataManager.fetchRooms();
        LiveData<List<Room>> chats = mDataManager.getPrivateRooms();
        if (chats != null) {
            chats.observe(this, mObserver);
        }
    }

    @Override
    public void setView(MainContract.PeopleView view) {
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
