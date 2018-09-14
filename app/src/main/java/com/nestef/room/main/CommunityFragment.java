package com.nestef.room.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nestef.room.R;
import com.nestef.room.data.DataManager;
import com.nestef.room.data.PrefManager;
import com.nestef.room.db.AppDatabase;
import com.nestef.room.model.Group;
import com.nestef.room.model.Room;

import org.parceler.Parcels;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

    private static final String RECYCLER_STATE = "list_state";
    private Unbinder mUnbinder;
    private CommunityPresenter mPresenter;
    private RoomAdapter mJoinedAdapter;
    private RoomFragment.RoomSelectionCallback mCallback;
    private Group mGroup;
    private Parcelable listSaveState;
    private LinearLayoutManager mLayoutManager;

    @BindView(R.id.joined_room_list)
    RecyclerView mRoomList;
    @Nullable
    @BindView(R.id.default_toolbar)
    Toolbar mToolbar;
    @BindInt(R.integer.is_tablet)
    int mIsTablet;
    @BindView(R.id.progress_bar)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.community_empty_text)
    TextView mEmptyText;

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
        AppDatabase db = AppDatabase.getDatabase(getContext());
        mPresenter = new CommunityPresenter(DataManager.getInstance(
                PrefManager.getInstance(getContext().getSharedPreferences(AUTH_SHARED_PREF, Context.MODE_PRIVATE)).getAuthToken(), db.roomDao(), db.groupDao()));
        mGroup = Parcels.unwrap(getArguments().getParcelable(GROUP));
        if (savedInstanceState != null) {
            listSaveState = savedInstanceState.getParcelable(RECYCLER_STATE);
        }
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mRoomList.getLayoutManager() != null) {
            outState.putParcelable(RECYCLER_STATE, mRoomList.getLayoutManager().onSaveInstanceState());
        }
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
        mRoomList.setVisibility(View.GONE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {
        mEmptyText.setVisibility(View.VISIBLE);
        mRoomList.setVisibility(View.GONE);
    }

    @Override
    public void networkError() {
        Toast.makeText(getContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
    }

    private boolean isTablet() {
        return mIsTablet == 1;
    }

    @Override
    public void showRooms(List<Room> joinedRooms) {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRoomList.setLayoutManager(mLayoutManager);
        mJoinedAdapter = new RoomAdapter(joinedRooms, this, true);
        mRoomList.setAdapter(mJoinedAdapter);
        mRoomList.setVisibility(View.VISIBLE);
        if (listSaveState != null) {
            mLayoutManager.onRestoreInstanceState(listSaveState);
        }
    }


    @Override
    public void onRoomClick(Room room) {
        mCallback.onRoomSelected(room);
    }
}
