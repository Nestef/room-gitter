package com.nestef.room.main;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.nestef.room.base.BasePresenter;
import com.nestef.room.data.DataManager;
import com.nestef.room.data.LoaderProvider;

/**
 * Created by Noah Steffes on 4/1/18.
 */

public class PeoplePresenter extends BasePresenter<MainContract.PeopleView> implements MainContract.PeopleViewActions, LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = PeoplePresenter.class.getName();

    private static final int PEOPLE_LOADER = 1;

    private DataManager mDataManager;

    private LoaderProvider mLoaderProvider;

    private LoaderManager mLoaderManager;

    PeoplePresenter(DataManager dataManager, LoaderProvider loaderProvider, LoaderManager loaderManager) {
        mDataManager = dataManager;
        mLoaderProvider = loaderProvider;
        mLoaderManager = loaderManager;
    }

    @Override
    public void fetchChats() {
        mView.showLoadingIndicator();
        mDataManager.getRooms();
        if (mLoaderManager.getLoader(PEOPLE_LOADER) == null) {
            mLoaderManager.initLoader(PEOPLE_LOADER, null, this);
        } else {
            mLoaderManager.restartLoader(PEOPLE_LOADER, null, this);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mLoaderProvider.createPrivateRoomLoader();
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Cursor data) {
        if (data != null) {
            if (data.moveToLast()) {
                mView.hideLoadingIndicator();
                mView.showChats(data);
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
    public void onLoaderReset(@NonNull Loader loader) {

    }


}
