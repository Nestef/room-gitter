package com.nestef.room.main;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nestef.room.R;
import com.nestef.room.data.PrefManager;
import com.nestef.room.data.SearchManager;
import com.nestef.room.model.Room;

import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.nestef.room.util.Constants.AUTH_SHARED_PREF;


public class SearchFragment extends Fragment implements MainContract.SearchView, RoomAdapter.RoomCallback {


    private static final String TAG = "SearchFragment";

    @BindView(R.id.search_toolbar)
    Toolbar toolbar;
    @BindInt(R.integer.is_tablet)
    int isTablet;
    @BindView(R.id.search_suggestion_text)
    TextView mSuggestionTitle;
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
        mSuggestionTitle.setVisibility(View.GONE);
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
        mSuggestionTitle.setVisibility(View.VISIBLE);
        mSuggestionList.setVisibility(View.VISIBLE);
        mSuggestionList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RoomAdapter(suggestions, this);
        mSuggestionList.setAdapter(mAdapter);
    }

    @Override
    public void showResults(List<Room> results) {
        //Gitter has some weird behavior with chats between users so
        //for now I won't show User search results.
        //Switch to SearchAdapter when adding User search results
        mSuggestionTitle.setVisibility(View.GONE);
        mSuggestionList.setVisibility(View.VISIBLE);
        mAdapter = new RoomAdapter(results, this);
        mSuggestionList.setAdapter(mAdapter);
    }

    @Override
    public void onRoomClick(Room room) {
        mCallback.onRoomSelected(room);
    }
}
