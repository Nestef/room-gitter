package com.nestef.room.main;

import com.nestef.room.base.BasePresenter;
import com.nestef.room.data.SearchManager;
import com.nestef.room.model.Room;

import java.util.List;

/**
 * Created by Noah Steffes on 3/31/18.
 */

public class SearchPresenter extends BasePresenter<MainContract.SearchView> implements MainContract.SearchViewActions, SearchManager.SearchCallback {

    private SearchManager mManager;

    SearchPresenter(SearchManager manager) {
        mManager = manager;
    }

    @Override
    public void fetchSuggestions() {
        mView.showLoadingIndicator();
        mManager.fetchRoomSuggestions(this);
    }

    @Override
    public void searchRooms(String query) {
        mView.showLoadingIndicator();
        mManager.searchRoomsByQuery(query, this);
    }


    @Override
    public void returnSuggestions(List<Room> rooms) {
        if (mView != null) {
            mView.hideLoadingIndicator();
            mView.showSuggestions(rooms);
        }
    }

    @Override
    public void returnSearchResults(List<Room> results) {
        if (mView != null) {
            mView.hideLoadingIndicator();
            mView.showResults(results);
        }
    }
}
