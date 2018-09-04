package com.nestef.room.main;

import android.database.Cursor;

import com.nestef.room.data.DataManager;
import com.nestef.room.data.LoaderProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import static org.mockito.Mockito.verify;

/**
 * Created by Noah Steffes on 8/28/18.
 */
public class GroupsPresenterTest {

    private GroupsPresenter mGroupsPresenter;
    @Mock
    private LoaderProvider mLoaderProvider;
    @Mock
    private LoaderManager mLoaderManager;
    @Mock
    private DataManager mDataManager;
    @Mock
    private MainContract.GroupsView mView;
    @Mock
    private Loader<Cursor> mLoader;
    @Mock
    private Cursor cursor;

    @Before
    public void setupGroupsPresenter() {
        MockitoAnnotations.initMocks(this);
        mGroupsPresenter = new GroupsPresenter(mDataManager, mLoaderProvider, mLoaderManager);
        mGroupsPresenter.setView(mView);
    }

    @Test
    public void fetchGroups() {
        mGroupsPresenter.fetchGroups();
        verify(mView).showLoadingIndicator();
        verify(mDataManager).getGroups();
    }

    @Test
    public void onLoadFinished() {
        mGroupsPresenter.onLoadFinished(mLoader, cursor);
        verify(mView).hideLoadingIndicator();
        verify(mView).showEmpty();
    }

    @Test
    public void testUnsetView() {
        mGroupsPresenter.unsetView();
        Assert.assertNull(mGroupsPresenter.);
    }
}