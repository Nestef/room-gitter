package com.nestef.room.main;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nestef.room.R;
import com.nestef.room.data.PrefManager;
import com.nestef.room.data.SearchManager;
import com.nestef.room.model.Room;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.nestef.room.util.Constants.AUTH_SHARED_PREF;


public class SearchFragment extends Fragment implements MainContract.SearchView, RoomAdapter.RoomCallback {


    private static final String TAG = "SearchFragment";

    private static final String RECYCLER_STATE = "list_state";

    @BindView(R.id.search_toolbar)
    Toolbar toolbar;
    @BindInt(R.integer.is_tablet)
    int isTablet;
    @BindView(R.id.search_suggestion_list)
    RecyclerView mSuggestionList;
    @BindView(R.id.progress_bar)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.search_input)
    SearchView mSearchView;

    Unbinder unbinder;
    SearchPresenter mPresenter;
    RoomAdapter mAdapter;
    RoomFragment.RoomSelectionCallback mCallback;
    Parcelable listSaveState;
    LinearLayoutManager mLayoutManager;

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SearchPresenter(new SearchManager(PrefManager.getInstance(getContext().getSharedPreferences(AUTH_SHARED_PREF, Context.MODE_PRIVATE))));
        if (savedInstanceState != null) {
            listSaveState = savedInstanceState.getParcelable(RECYCLER_STATE);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_fragment, container, false);
        mPresenter.setView(this);
        unbinder = ButterKnife.bind(this, rootView);
        if (!isTablet()) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }
        setHasOptionsMenu(false);
        mPresenter.fetchSuggestions();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.equals("")) {
                    mPresenter.searchRooms(query);
                } else {
                    mPresenter.fetchSuggestions();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.equals("")) {
                    mPresenter.searchRooms(newText);
                } else {
                    mPresenter.fetchSuggestions();
                }
                return false;
            }
        });
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSuggestionList.getLayoutManager() != null) {
            outState.putParcelable(RECYCLER_STATE, mSuggestionList.getLayoutManager().onSaveInstanceState());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (RoomFragment.RoomSelectionCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement RoomFragmentCallback");
        }
    }

    @Override
    public void showLoadingIndicator() {
        mSuggestionList.setVisibility(View.GONE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {

    }

    private boolean isTablet() {
        return isTablet == 1;
    }

    @Override
    public void showSuggestions(List<Room> suggestions) {
        mSuggestionList.setVisibility(View.VISIBLE);
        mLayoutManager = new LinearLayoutManager(getContext());
        mSuggestionList.setLayoutManager(mLayoutManager);
        mAdapter = new RoomAdapter(suggestions, this, false);
        mSuggestionList.setAdapter(mAdapter);
        if (listSaveState != null) {
            mLayoutManager.onRestoreInstanceState(listSaveState);
        }
    }

    @Override
    public void showResults(List<Room> results) {
        //Gitter has some weird behavior with chats between users so
        //for now I won't show User search results.
        //Switch to SearchAdapter when adding User search results
        mSuggestionList.setVisibility(View.VISIBLE);
        mAdapter = new RoomAdapter(results, this, false);
        mSuggestionList.setAdapter(mAdapter);
        if (listSaveState != null) {
            mLayoutManager.onRestoreInstanceState(listSaveState);
        }
    }

    @Override
    public void onRoomClick(Room room) {
        mCallback.onRoomSelected(room);
    }
}
