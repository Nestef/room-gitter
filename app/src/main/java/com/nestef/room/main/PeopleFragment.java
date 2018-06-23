package com.nestef.room.main;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nestef.room.R;
import com.nestef.room.data.DataManager;
import com.nestef.room.data.LoaderProvider;
import com.nestef.room.data.PrefManager;
import com.nestef.room.model.Room;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.nestef.room.util.Constants.AUTH_SHARED_PREF;

/**
 * Created by Noah Steffes on 3/31/18.
 */

public class PeopleFragment extends Fragment implements MainContract.PeopleView, RoomAdapter.RoomCallback {

    private static final String TAG = "PeopleFragment";

    @BindView(R.id.people_list)
    ListView peopleList;
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
        Bundle args = new Bundle();
        PeopleFragment fragment = new PeopleFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PeoplePresenter(DataManager.getInstance(getContext().getContentResolver(),
                PrefManager.getInstance(getContext().getSharedPreferences(AUTH_SHARED_PREF, Context.MODE_PRIVATE))),
                new LoaderProvider(getContext()), getLoaderManager());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.people_fragment, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        Log.d(TAG, "onCreateView: ");
        presenter.setView(this);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (!isTablet()) setHasOptionsMenu(true);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_actions, menu);
    }

    @Override
    public void showChats(Cursor chats) {
        roomAdapter = new RoomAdapter(getContext(), this);
        peopleList.setAdapter(roomAdapter);
        roomAdapter.changeCursor(chats);
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
