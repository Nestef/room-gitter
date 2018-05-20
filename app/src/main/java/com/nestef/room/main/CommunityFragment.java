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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nestef.room.R;
import com.nestef.room.model.Group;
import com.nestef.room.model.Room;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Noah Steffes on 5/2/18.
 */
public class CommunityFragment extends Fragment implements MainContract.CommunityView, RoomAdapter.RoomCallback {


    private static final String GROUP = "Group";
    Unbinder unbinder;
    CommunityPresenter presenter;
    RoomAdapter joinedAdapter;
    RoomAdapter communityAdapter;
    private RoomFragment.RoomSelectionCallback mCallback;


    @BindView(R.id.community_room_list)
    RecyclerView communityRoomList;
    @BindView(R.id.joined_room_list)
    RecyclerView joinedRoomList;
    @Nullable
    @BindView(R.id.default_toolbar)
    Toolbar toolbar;
    @BindInt(R.integer.is_tablet)
    int isTablet;

    public static CommunityFragment newInstance(Group group) {

        Bundle args = new Bundle();
        args.putParcelable(GROUP, Parcels.wrap(group));
        CommunityFragment fragment = new CommunityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CommunityPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.community_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.setView(this);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle();

        if (!isTablet()) setHasOptionsMenu(true);

        presenter.fetchRooms();
        return view;
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_actions, menu);
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
    public void showJoinedRooms(List<Room> joinedRooms) {
        joinedRoomList.setLayoutManager(new LinearLayoutManager(getContext()));
        joinedAdapter = new RoomAdapter(joinedRooms, this);
        joinedRoomList.setAdapter(joinedAdapter);
    }

    @Override
    public void showJoinedRoomsEmpty() {

    }

    @Override
    public void showUnjoinedRooms(List<Room> unjoinedRooms) {
        communityRoomList.setLayoutManager(new LinearLayoutManager(getContext()));
        communityAdapter = new RoomAdapter(unjoinedRooms, this);
        communityRoomList.setAdapter(communityAdapter);

    }

    @Override
    public void onRoomClick(Room room) {
        mCallback.onRoomSelected(room);
    }
}
