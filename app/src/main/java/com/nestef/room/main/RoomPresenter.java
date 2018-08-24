package com.nestef.room.main;


import android.database.Cursor;
import android.os.Bundle;

import com.nestef.room.base.BasePresenter;
import com.nestef.room.data.DataManager;
import com.nestef.room.data.LoaderProvider;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

/**
 * Created by Noah Steffes on 3/31/18.
 */

public class RoomPresenter extends BasePresenter<MainContract.RoomView> implements MainContract.RoomViewActions, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = RoomPresenter.class.getName();

    private static final int ROOM_LOADER = 1;

    private DataManager mDataManager;

    private LoaderProvider mLoaderProvider;

    private LoaderManager mLoaderManager;

    RoomPresenter(DataManager dataManager, LoaderProvider loaderProvider, LoaderManager loaderManager) {
        mDataManager = dataManager;
        mLoaderProvider = loaderProvider;
        mLoaderManager = loaderManager;
    }

    @Override
    public void fetchRooms() {
        mView.showLoadingIndicator();
        mDataManager.getRooms();
        if (mLoaderManager.getLoader(ROOM_LOADER) == null) {
            mLoaderManager.initLoader(ROOM_LOADER, null, this);
        } else {
            mLoaderManager.restartLoader(ROOM_LOADER, null, this);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mLoaderProvider.createRoomLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            if (data.moveToLast()) {
                mView.hideLoadingIndicator();
                mView.showRooms(data);
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
