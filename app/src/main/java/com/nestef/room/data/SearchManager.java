package com.nestef.room.data;

import com.nestef.room.model.QueryResponse;
import com.nestef.room.model.Room;
import com.nestef.room.services.GitterApiService;
import com.nestef.room.services.GitterServiceFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Noah Steffes on 7/3/18.
 */
public class SearchManager {

    private GitterApiService mService;

    public SearchManager(PrefManager prefManager) {
        mService = GitterServiceFactory.makeApiService(prefManager.getAuthToken());
    }

    public void fetchRoomSuggestions(SearchCallback callback) {
        mService.getSuggestedRooms().enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                callback.returnSuggestions(response.body());
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void searchRoomsByQuery(String query, SearchCallback callback) {
        mService.searchRooms(query).enqueue(new Callback<QueryResponse>() {
            @Override
            public void onResponse(Call<QueryResponse> call, Response<QueryResponse> response) {
                callback.returnSearchResults(response.body().results);
            }

            @Override
            public void onFailure(Call<QueryResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public interface SearchCallback {
        void returnSuggestions(List<Room> rooms);

        void returnSearchResults(List<Room> results);
    }

}
