package com.nestef.room.main;

import com.google.common.collect.Lists;
import com.nestef.room.data.DataManager;
import com.nestef.room.model.Room;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by Noah Steffes on 8/28/18.
 */
public class CommunityPresenterTest {

    private static List<Room> ROOMS = Lists.newArrayList(new Room(), new Room());
    private CommunityPresenter mCommunityPresenter;
    @Mock
    private DataManager mDataManager;
    @Mock
    private MainContract.CommunityView mView;

    @Captor
    private ArgumentCaptor<DataManager.DataCallback> mDataCallbackArgumentCaptor;

    @Before
    public void setupPresenter() {
        MockitoAnnotations.initMocks(this);
        mCommunityPresenter = new CommunityPresenter(mDataManager);
        mCommunityPresenter.setView(mView);
    }

    @Test
    public void fetchRooms() {
        mCommunityPresenter.fetchRooms("");
        verify(mView).showLoadingIndicator();
        verify(mDataManager).getCommunityRooms(any(String.class), mDataCallbackArgumentCaptor.capture());

        mDataCallbackArgumentCaptor.getValue().onCall(ROOMS);
        verify(mView).hideLoadingIndicator();
        verify(mView).showRooms(ROOMS);

    }

    @Test
    public void onCallNullData() {
        mCommunityPresenter.onCall(null);
        verify(mView).hideLoadingIndicator();
        verify(mView).networkError();
    }

    @Test
    public void onCallEmptyData() {
        mCommunityPresenter.onCall(new ArrayList<>());
        verify(mView).hideLoadingIndicator();
        verify(mView).showEmpty();
    }

}