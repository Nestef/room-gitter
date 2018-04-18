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


public class SearchFragment extends Fragment implements MainContract.SearchView {


    private static final String TAG = "SearchFragment";

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
        Toolbar toolbar = rootView.findViewById(R.id.search_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        return rootView;
    }

}
