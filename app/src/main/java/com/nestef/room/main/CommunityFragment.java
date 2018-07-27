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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nestef.room.R;
import com.nestef.room.data.DataManager;
import com.nestef.room.data.PrefManager;
import com.nestef.room.model.Group;
import com.nestef.room.model.Room;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.nestef.room.util.Constants.AUTH_SHARED_PREF;

/**
 * Created by Noah Steffes on 5/2/18.
 */
public class CommunityFragment extends Fragment implements MainContract.CommunityView, RoomAdapter.RoomCallback {


    private static final String GROUP = "Group";
    Unbinder mUnbinder;
    CommunityPresenter mPresenter;
    RoomAdapter mJoinedAdapter;
    RoomAdapter mCommunityAdapter;
    private RoomFragment.RoomSelectionCallback mCallback;
    Group mGroup;


    @BindView(R.id.community_room_list)
    RecyclerView mCommunityRoomList;
    @BindView(R.id.joined_room_list)
    RecyclerView mJoinedRoomList;
    @Nullable
    @BindView(R.id.default_toolbar)
    Toolbar mToolbar;
    @BindInt(R.integer.is_tablet)
    int mIsTablet;
    @BindView(R.id.progress_bar)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.community_joined_title)
    TextView mJoinedTitle;
    @BindView(R.id.community_other_title)
    TextView mListTitle;

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
        mPresenter = new CommunityPresenter(DataManager.getInstance(getContext().getContentResolver(),
                PrefManager.getInstance(getContext().getSharedPreferences(AUTH_SHARED_PREF, Context.MODE_PRIVATE))));
        mGroup = Parcels.unwrap(getArguments().getParcelable(GROUP));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.community_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mPresenter.setView(this);


        if (!isTablet()) {
            setHasOptionsMenu(true);
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mGroup.name);
        }

        mPresenter.fetchRooms(mGroup.id);
        return view;
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
    public void showLoadingIndicator() {
        mJoinedTitle.setVisibility(View.GONE);
        mListTitle.setVisibility(View.GONE);
        mJoinedRoomList.setVisibility(View.GONE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {
        Log.d("", "showEmpty: ");
    }

    private boolean isTablet() {
        return mIsTablet == 1;
    }

    @Override
    public void showJoinedRooms(List<Room> joinedRooms) {
        mJoinedRoomList.setLayoutManager(new LinearLayoutManager(getContext()));
        mJoinedAdapter = new RoomAdapter(joinedRooms, this);
        mJoinedRoomList.setAdapter(mJoinedAdapter);
        mJoinedRoomList.setVisibility(View.VISIBLE);
        mJoinedTitle.setVisibility(View.VISIBLE);
        Log.d("", "showJoinedRooms: ");
    }

    @Override
    public void showJoinedRoomsEmpty() {
        mJoinedTitle.setVisibility(View.VISIBLE);
        mJoinedRoomList.setVisibility(View.GONE);
        Log.d("", "showJoinedRoomsEmpty: ");
    }

    @Override
    public void showUnjoinedRooms(List<Room> unjoinedRooms) {
        mCommunityRoomList.setLayoutManager(new LinearLayoutManager(getContext()));
        mCommunityAdapter = new RoomAdapter(unjoinedRooms, this);
        mCommunityRoomList.setAdapter(mCommunityAdapter);
        mCommunityRoomList.setVisibility(View.VISIBLE);
        mListTitle.setVisibility(View.VISIBLE);
        Log.d("", "showUnjoinedRooms: ");

    }

    @Override
    public void onRoomClick(Room room) {
        mCallback.onRoomSelected(room);
    }
}
