package com.nestef.room.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nestef.room.R;

/**
 * Created by Noah Steffes on 3/25/18.
 */

public class RoomFragment extends Fragment implements MainContract.RoomView {

    private static final String TAG = "RoomFragment";

    public RoomFragment() {
    }

    public static RoomFragment newInstance() {
        return new RoomFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.room_fragment, container, false);
        Log.d(TAG, "onCreateView: ");
        Toolbar toolbar = rootView.findViewById(R.id.default_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return rootView;
    }
}
