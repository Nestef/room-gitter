package com.nestef.room.data;

import com.nestef.room.model.QueryResponse;
import com.nestef.room.model.Room;
import com.nestef.room.services.GitterApiService;
import com.nestef.room.services.GitterServiceFactory;

import java.util.List;

import retrofit2.Callback;

/**
 * Created by Noah Steffes on 7/3/18.
 */
public class SearchManager {

    private GitterApiService mService;

    public SearchManager(PrefManager prefManager) {
        mService = GitterServiceFactory.makeApiService(prefManager.getAuthToken());
    }

    public void fetchRoomSuggestions(Callback<List<Room>> callback) {
        mService.getSuggestedRooms().enqueue(callback);
    }

    public void searchRoomsByQuery(String query, Callback<QueryResponse> callback) {
        mService.searchRooms(query).enqueue(callback);
    }

    public interface SearchCallback {
        void returnSuggestions(List<Room> rooms);

        void returnSearchResults(List<Room> results);
    }

}
