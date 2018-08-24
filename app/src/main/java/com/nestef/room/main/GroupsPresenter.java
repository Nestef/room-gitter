package com.nestef.room.main;


import android.database.Cursor;
import android.os.Bundle;

import com.nestef.room.base.BasePresenter;
import com.nestef.room.data.DataManager;
import com.nestef.room.data.LoaderProvider;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

/**
 * Created by Noah Steffes on 4/11/18.
 */
public class GroupsPresenter extends BasePresenter<MainContract.GroupsView> implements MainContract.GroupsViewActions, LoaderManager.LoaderCallbacks<Cursor> {

    private final static String TAG = "GroupsPresenter";
    private static final int GROUP_LOADER = 143;
    private DataManager mDataManager;
    private LoaderProvider mLoaderProvider;
    private LoaderManager mLoaderManager;


    GroupsPresenter(DataManager dataManager, LoaderProvider loaderProvider, LoaderManager loaderManager) {
        mDataManager = dataManager;
        mLoaderProvider = loaderProvider;
        mLoaderManager = loaderManager;
    }

    @Override
    public void fetchGroups() {
        mView.showLoadingIndicator();
        mDataManager.getGroups();
        if (mLoaderManager.getLoader(GROUP_LOADER) == null) {
            mLoaderManager.initLoader(GROUP_LOADER, null, this);
        } else {
            mLoaderManager.restartLoader(GROUP_LOADER, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mLoaderProvider.createGroupLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            if (data.moveToLast()) {
                mView.hideLoadingIndicator();
                mView.showGroups(data);
            } else {
                mView.hideLoadingIndicator();
                mView.showEmpty();
            }
        } else {
            mView.hideLoadingIndicator();
            mView.showEmpty();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
