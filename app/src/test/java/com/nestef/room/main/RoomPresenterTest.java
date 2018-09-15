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
public class RoomPresenterTest {

    private RoomPresenter mRoomPresenter;

    @Mock
    private DataManager mDataManager;
    @Mock
    private MainContract.RoomView mView;

    @Before
    public void setUpRoomPresenter() {
        MockitoAnnotations.initMocks(this);
        mRoomPresenter = new RoomPresenter(mDataManager);
        mRoomPresenter.setView(mView);
    }

    @Test
    public void fetchRooms() {
        mRoomPresenter.fetchRooms();
        verify(mView).showLoadingIndicator();
        verify(mDataManager).fetchRooms();
        verify(mDataManager).getRooms();
    }
}