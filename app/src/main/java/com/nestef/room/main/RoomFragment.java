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
import android.view.Menu;
import android.view.MenuInflater;
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
 * Created by Noah Steffes on 3/25/18.
 */

public class RoomFragment extends Fragment implements MainContract.RoomView, RoomAdapter.RoomCallback {

    private static final String TAG = "RoomFragment";

    @BindView(R.id.room_list)
    RecyclerView mRoomList;
    @Nullable
    @BindView(R.id.default_toolbar)
    Toolbar mToolbar;
    @BindInt(R.integer.is_tablet)
    int mIsTablet;

    private Unbinder mUnbinder;
    private RoomAdapter mRoomAdapter;
    private RoomPresenter mPresenter;
    private RoomSelectionCallback mCallback;

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
        mPresenter = new RoomPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.room_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        mPresenter.setView(this);
        Log.d(TAG, "onCreateView: ");
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        if (!isTablet()) setHasOptionsMenu(true);
        mPresenter.fetchRooms();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsetView();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_actions, menu);
    }

    @Override
    public void showRooms(List<Room> rooms) {
        mRoomList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRoomAdapter = new RoomAdapter(rooms, this);
        mRoomList.setAdapter(mRoomAdapter);
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
        return mIsTablet == 1;
    }

    @Override
    public void onRoomClick(Room room) {
        mCallback.onRoomSelected(room);
    }

    public interface RoomSelectionCallback {
        void onRoomSelected(Room room);
    }

    ;
}
