package com.nestef.room.data;

import android.os.AsyncTask;

import com.nestef.room.model.Room;
import com.nestef.room.services.GitterApiService;
import com.nestef.room.services.GitterServiceFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by Noah Steffes on 7/3/18.
 */
public class SearchManager {

    GitterApiService mService;

    public SearchManager(PrefManager prefManager) {
        mService = GitterServiceFactory.makeApiService(prefManager.getAuthToken());
    }

    public void fetchRoomSuggestions(SearchCallback callback) {
        new SuggestionAsyncTask(callback).execute();
    }

    public void searchRoomsByQuery(String query, SearchCallback callback) {
        new SearchAsyncTask(query, callback).execute();
    }

    public interface SearchCallback {
        void returnSuggestions(List<Room> rooms);

        void returnSearchResults(List<Room> results);
    }

    class SuggestionAsyncTask extends AsyncTask<Void, Void, List<Room>> {
        SearchCallback mCallback;

        SuggestionAsyncTask(SearchCallback callback) {
            mCallback = callback;
        }

        @Override
        protected List<Room> doInBackground(Void... voids) {
            try {
                return mService.getSuggestedRooms().execute().body();
            } catch (IOException i) {
                i.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Room> rooms) {
            mCallback.returnSuggestions(rooms);
        }
    }

    class SearchAsyncTask extends AsyncTask<Void, Void, List<Room>> {
        String mQueryParam;
        SearchCallback mCallback;

        SearchAsyncTask(String queryParam, SearchCallback callback) {
            mQueryParam = queryParam;
            mCallback = callback;
        }

        @Override
        protected List<Room> doInBackground(Void... voids) {
            try {
                return mService.searchRooms(mQueryParam).execute().body().results;
            } catch (IOException i) {
                i.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Room> rooms) {
            super.onPostExecute(rooms);
            mCallback.returnSearchResults(rooms);
        }
    }
}
