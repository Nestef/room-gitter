package com.nestef.room.main;

import com.google.common.collect.Lists;
import com.nestef.room.data.SearchManager;
import com.nestef.room.model.Room;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by Noah Steffes on 8/29/18.
 */
public class SearchPresenterTest {

    private static List<Room> ROOMS = Lists.newArrayList(new Room(), new Room());
    private SearchPresenter mSearchPresenter;
    @Mock
    private SearchManager mSearchManager;
    @Mock
    private MainContract.SearchView mView;

    @Captor
    private ArgumentCaptor<SearchManager.SearchCallback> mSearchCallbackArgumentCaptor;

    @Before
    public void setUpSearchPresenter() {
        MockitoAnnotations.initMocks(this);
        mSearchPresenter = new SearchPresenter(mSearchManager);
        mSearchPresenter.setView(mView);
    }

    @Test
    public void fetchSuggestions() {
        mSearchPresenter.fetchSuggestions();
        verify(mView).showLoadingIndicator();
        verify(mSearchManager).fetchRoomSuggestions(mSearchCallbackArgumentCaptor.capture());

        mSearchCallbackArgumentCaptor.getValue().returnSuggestions(ROOMS);
        verify(mView).hideLoadingIndicator();
        verify(mView).showSuggestions(ROOMS);
    }

    @Test
    public void searchRooms() {
        mSearchPresenter.searchRooms("g");
        verify(mView).showLoadingIndicator();
        verify(mSearchManager).searchRoomsByQuery(anyString(), mSearchCallbackArgumentCaptor.capture());

        mSearchCallbackArgumentCaptor.getValue().returnSearchResults(ROOMS);
        verify(mView).hideLoadingIndicator();
        verify(mView).showResults(ROOMS);
    }

    @Test
    public void returnSuggestions() {
        mSearchPresenter.returnSuggestions(ROOMS);
        verify(mView).hideLoadingIndicator();
        verify(mView).showSuggestions(ROOMS);
    }

    @Test
    public void returnSearchResults() {
        mSearchPresenter.returnSearchResults(ROOMS);
        verify(mView).hideLoadingIndicator();
        verify(mView).showResults(ROOMS);
    }
}