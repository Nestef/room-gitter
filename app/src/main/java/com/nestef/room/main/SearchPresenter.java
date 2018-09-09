package com.nestef.room.main;

import com.nestef.room.base.BasePresenter;
import com.nestef.room.data.SearchManager;
import com.nestef.room.model.QueryResponse;
import com.nestef.room.model.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        mManager.fetchRoomSuggestions(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                returnSuggestions(response.body());
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void searchRooms(String query) {
        mView.showLoadingIndicator();
        mManager.searchRoomsByQuery(query, new Callback<QueryResponse>() {
            @Override
            public void onResponse(Call<QueryResponse> call, Response<QueryResponse> response) {
                returnSearchResults(response.body().results);
            }

            @Override
            public void onFailure(Call<QueryResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
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
