package com.nestef.room.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nestef.room.R;
import com.nestef.room.model.Room;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Noah Steffes on 3/25/18.
 */

public class RoomFragment extends Fragment implements MainContract.RoomView {

    private static final String TAG = "RoomFragment";

    @BindView(R.id.default_toolbar)
    Toolbar toolbar;
    @BindView(R.id.room_list)
    RecyclerView roomList;

    private Unbinder unbinder;
    private RoomAdapter roomAdapter;
    private RoomPresenter presenter;

    public RoomFragment() {
    }

    public static RoomFragment newInstance() {
        Bundle args = new Bundle();
        RoomFragment fragment = new RoomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RoomPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.room_fragment, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        presenter.setView(this);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        Log.d(TAG, "onCreateView: ");
        presenter.fetchRooms();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showRooms(List<Room> rooms) {
        roomList.setLayoutManager(new LinearLayoutManager(getContext()));
        roomAdapter = new RoomAdapter(rooms);
        roomList.setAdapter(roomAdapter);
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
}
