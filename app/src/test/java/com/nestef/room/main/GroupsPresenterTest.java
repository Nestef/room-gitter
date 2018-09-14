package com.nestef.room.main;

import com.nestef.room.data.DataManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Created by Noah Steffes on 8/28/18.
 */
public class GroupsPresenterTest {

    private GroupsPresenter mGroupsPresenter;

    @Mock
    private DataManager mDataManager;
    @Mock
    private MainContract.GroupsView mView;

    @Before
    public void setupGroupsPresenter() {
        MockitoAnnotations.initMocks(this);
        mGroupsPresenter = new GroupsPresenter(mDataManager);
        mGroupsPresenter.setView(mView);
    }


    @Test
    public void fetchGroups() {
        mGroupsPresenter.fetchGroups();
        verify(mView).showLoadingIndicator();
        verify(mDataManager).getGroups();
    }


}