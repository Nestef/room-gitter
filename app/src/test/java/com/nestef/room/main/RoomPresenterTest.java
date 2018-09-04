package com.nestef.room.main;

import android.database.Cursor;

import com.nestef.room.data.DataManager;
import com.nestef.room.data.LoaderProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import static org.mockito.Mockito.verify;

/**
 * Created by Noah Steffes on 8/29/18.
 */
public class RoomPresenterTest {

    private RoomPresenter mRoomPresenter;
    @Mock
    private LoaderProvider mLoaderProvider;
    @Mock
    private LoaderManager mLoaderManager;
    @Mock
    private DataManager mDataManager;
    @Mock
    private MainContract.RoomView mView;
    @Mock
    private Loader<Cursor> mLoader;
    @Mock
    private Cursor mCursor;

    @Before
    public void setUpRoomPresenter() {
        MockitoAnnotations.initMocks(this);
        mRoomPresenter = new RoomPresenter(mDataManager, mLoaderProvider, mLoaderManager);
        mRoomPresenter.setView(mView);
    }

    @Test
    public void fetchRooms() {
        mRoomPresenter.fetchRooms();
        verify(mView).showLoadingIndicator();
        verify(mDataManager).getRooms();
    }

    @Test
    public void onLoadFinished() {
        mRoomPresenter.onLoadFinished(mLoader, mCursor);
        verify(mView).hideLoadingIndicator();
        verify(mView).showEmpty();
    }
}