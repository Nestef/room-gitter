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
import com.nestef.room.model.Group;
import com.nestef.room.provider.RoomProviderContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.nestef.room.util.Constants.AUTH_SHARED_PREF;

/**
 * Created by Noah Steffes on 4/1/18.
 */

public class GroupsFragment extends Fragment implements MainContract.GroupsView, GroupAdapter.GroupCallback {

    private static final String TAG = "GroupsFragment";

    @BindView(R.id.group_list)
    RecyclerView mGroupList;

    @Nullable
    @BindView(R.id.default_toolbar)
    Toolbar mToolbar;
    @BindInt(R.integer.is_tablet)
    int mIsTablet;
    @BindView(R.id.progress_bar)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.group_empty_text)
    TextView mEmptyText;

    GroupsFragment.OnCommunitySelection mCallback;
    private Unbinder mUnbinder;
    private GroupAdapter mGroupAdapter;
    private GroupsPresenter mPresenter;

    public GroupsFragment() {
    }

    public static GroupsFragment newInstance() {
        Bundle args = new Bundle();
        GroupsFragment fragment = new GroupsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new GroupsPresenter(DataManager.getInstance(getContext().getContentResolver(),
                PrefManager.getInstance(getContext().getSharedPreferences(AUTH_SHARED_PREF, Context.MODE_PRIVATE))),
                new LoaderProvider(getContext()), getLoaderManager());
        if (!isTablet()) setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.groups_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        mPresenter.setView(this);
        mPresenter.fetchGroups();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        inflater.inflate(R.menu.main_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void showGroups(Cursor groups) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        List<Group> groups1 = new ArrayList<>();
        for (int i = 0; i < groups.getCount(); i++) {
            groups.moveToPosition(i);
            Group group = getGroupFromCursor(groups);
            groups1.add(group);
        }
        mEmptyText.setVisibility(View.INVISIBLE);
        mGroupList.setVisibility(View.VISIBLE);
        mGroupList.setLayoutManager(linearLayoutManager);
        mGroupAdapter = new GroupAdapter(groups1, this);
        mGroupList.setAdapter(mGroupAdapter);
    }

    private Group getGroupFromCursor(Cursor cursor) {
        Group group = new Group();
        group.name = cursor.getString(cursor.getColumnIndexOrThrow(RoomProviderContract.GroupEntry.COLUMN_NAME));
        group.avatarUrl = cursor.getString(cursor.getColumnIndexOrThrow(RoomProviderContract.GroupEntry.COLUMN_AVATARURL));
        group.uri = cursor.getString(cursor.getColumnIndexOrThrow(RoomProviderContract.GroupEntry.COLUMN_URI));
        group.homeUri = cursor.getString(cursor.getColumnIndexOrThrow(RoomProviderContract.GroupEntry.COLUMN_HOMEURI));
        group.id = cursor.getString(cursor.getColumnIndexOrThrow(RoomProviderContract.GroupEntry.COLUMN_ID));
        return group;
    }

    @Override
    public void showLoadingIndicator() {
        mGroupList.setVisibility(View.GONE);
        mEmptyText.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {
        mGroupList.setVisibility(View.GONE);
        mEmptyText.setVisibility(View.VISIBLE);
    }

    private boolean isTablet() {
        return mIsTablet == 1;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (GroupsFragment.OnCommunitySelection) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnCommunitySelection");
        }
    }

    @Override
    public void onClick(Group selectedGroup) {
        mCallback.onCommunitySelect(selectedGroup);
    }

    public interface OnCommunitySelection {
        void onCommunitySelect(Group group);
    }
}
