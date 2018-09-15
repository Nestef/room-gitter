package com.nestef.room.main;


import com.nestef.room.base.BasePresenter;
import com.nestef.room.data.DataManager;
import com.nestef.room.model.Group;

import java.util.List;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

/**
 * Created by Noah Steffes on 4/11/18.
 */
public class GroupsPresenter extends BasePresenter<MainContract.GroupsView> implements MainContract.GroupsViewActions, LifecycleOwner {

    private final static String TAG = "GroupsPresenter";
    private DataManager mDataManager;

    private Observer<List<Group>> mObserver = groups -> {
        if (groups != null) {
            mView.hideLoadingIndicator();
            mView.showGroups(groups);
        } else {
            mView.hideLoadingIndicator();
            mView.showEmpty();
        }
    };

    private LifecycleRegistry mLifecycleRegistry;


    public GroupsPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void fetchGroups() {
        mView.showLoadingIndicator();
        //need to add databinding, viewmodel,
        LiveData<List<Group>> groups = mDataManager.getGroups();
        if (groups != null) {
            groups.observe(this, mObserver);
        }
    }

    @Override
    public void setView(MainContract.GroupsView view) {
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
