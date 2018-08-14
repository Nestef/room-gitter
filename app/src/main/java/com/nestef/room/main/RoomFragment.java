package com.nestef.room.main;

import android.content.Context;
import android.database.Cursor;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nestef.room.R;
import com.nestef.room.data.DataManager;
import com.nestef.room.data.LoaderProvider;
import com.nestef.room.data.PrefManager;
import com.nestef.room.model.Room;
import com.nestef.room.provider.RoomProviderContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.nestef.room.util.Constants.AUTH_SHARED_PREF;

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
    @BindView(R.id.room_empty_text)
    TextView mEmptyMessage;
    @BindView(R.id.progress_bar)
    ProgressBar mLoadingIndicator;

    private Unbinder mUnbinder;
    private RoomAdapter mRoomCursorAdapter;
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
        mPresenter = new RoomPresenter(DataManager.getInstance(getContext().getContentResolver(),
                PrefManager.getInstance(getContext().getSharedPreferences(AUTH_SHARED_PREF, Context.MODE_PRIVATE))),
                new LoaderProvider(getContext()), getLoaderManager());
        if (!isTablet()) setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.room_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        mPresenter.setView(this);
        //initialize actionbar in oncreateview only in first fragment
        //in all fragments set actionbar in oncreateoptionsmenu
        //this prevents a bug where the action menu disapears in hidden fragments on rotation
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
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
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        inflater.inflate(R.menu.main_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void showRooms(Cursor rooms) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        List<Room> rooms1 = new ArrayList<>();
        for (int i = 0; i < rooms.getCount(); i++) {
            rooms.moveToPosition(i);
            Room room = getRoomsFromCursor(rooms);
            rooms1.add(room);
        }
        mEmptyMessage.setVisibility(View.GONE);
        mRoomList.setLayoutManager(linearLayoutManager);
        mRoomList.setVisibility(View.VISIBLE);
        mRoomCursorAdapter = new RoomAdapter(rooms1, this, false);
        mRoomList.setAdapter(mRoomCursorAdapter);
    }

    private Room getRoomsFromCursor(Cursor cursor) {
        Room room = new Room();
        room.id = cursor.getString(cursor.getColumnIndexOrThrow(RoomProviderContract.RoomEntry.COLUMN_ID));
        room.unreadItems = cursor.getInt(cursor.getColumnIndexOrThrow(RoomProviderContract.RoomEntry.COLUMN_UNREAD));
        room.name = cursor.getString(cursor.getColumnIndexOrThrow(RoomProviderContract.RoomEntry.COLUMN_NAME));
        room.avatarUrl = cursor.getString(cursor.getColumnIndexOrThrow(RoomProviderContract.RoomEntry.COLUMN_AVATARURL));
        return room;
    }
    @Override
    public void showLoadingIndicator() {
        mEmptyMessage.setVisibility(View.GONE);
        mRoomList.setVisibility(View.GONE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {
        mRoomList.setVisibility(View.INVISIBLE);
        mEmptyMessage.setVisibility(View.VISIBLE);
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

}
