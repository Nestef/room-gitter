package com.nestef.room.main;

import com.nestef.room.data.DataManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Created by Noah Steffes on 8/29/18.
 */
public class PeoplePresenterTest {

    private PeoplePresenter mPeoplePresenter;

    @Mock
    private DataManager mDataManager;
    @Mock
    private MainContract.PeopleView mView;

    @Before
    public void setupPeoplePresenter() {
        MockitoAnnotations.initMocks(this);
        mPeoplePresenter = new PeoplePresenter(mDataManager);
        mPeoplePresenter.setView(mView);
    }

    @Test
    public void fetchChats() {
        mPeoplePresenter.fetchChats();
        verify(mView).showLoadingIndicator();
        verify(mDataManager).fetchRooms();
        verify(mDataManager).getPrivateRooms();
    }

}