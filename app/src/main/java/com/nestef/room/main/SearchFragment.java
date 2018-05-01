package com.nestef.room.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nestef.room.R;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SearchFragment extends Fragment implements MainContract.SearchView {


    private static final String TAG = "SearchFragment";

    @BindView(R.id.search_toolbar)
    Toolbar toolbar;
    @BindInt(R.integer.is_tablet)
    int isTablet;

    Unbinder unbinder;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_fragment, container, false);
        Log.d(TAG, "onCreateView: ");
        unbinder = ButterKnife.bind(this, rootView);
        if (isTablet()) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }

        return rootView;
    }

    @Override
    public void showLoadingIndicator() {

    }

    @Override
    public void hideLoadingIndicator() {

    }

    @Override
    public void showEmpty() {

    }

    private boolean isTablet() {
        return isTablet == 1;
    }
}
