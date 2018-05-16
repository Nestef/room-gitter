package com.nestef.room.main;


import android.content.Context;
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

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Noah Steffes on 3/31/18.
 */

public class PeopleFragment extends Fragment implements MainContract.PeopleView, RoomAdapter.RoomCallback {

    private static final String TAG = "PeopleFragment";

    @BindView(R.id.people_list)
    RecyclerView peopleList;
    @BindInt(R.integer.is_tablet)
    int isTablet;
    @Nullable
    @BindView(R.id.default_toolbar)
    Toolbar toolbar;

    private Unbinder unbinder;
    private RoomAdapter roomAdapter;
    private PeoplePresenter presenter;
    private RoomFragment.RoomSelectionCallback mCallback;

    public PeopleFragment() {
    }

    public static PeopleFragment newInstance() {
        return new PeopleFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PeoplePresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.people_fragment, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        presenter.setView(this);
        if (isTablet()) ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        Log.d(TAG, "onCreateView: ");
        presenter.fetchChats();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsetView();
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
    public void showChats(List<Room> chats) {
        peopleList.setLayoutManager(new LinearLayoutManager(getContext()));
        roomAdapter = new RoomAdapter(chats, this);
        peopleList.setAdapter(roomAdapter);
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

    @Override
    public void onRoomClick(Room room) {
        mCallback.onRoomSelected(room);
    }
}
