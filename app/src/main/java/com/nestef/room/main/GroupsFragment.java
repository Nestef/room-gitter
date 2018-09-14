package com.nestef.room.main;

import android.content.Context;
import android.os.Bundle;
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
import com.nestef.room.db.AppDatabase;
import com.nestef.room.model.Group;

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

    private GroupsFragment.OnCommunitySelection mCallback;
    private Unbinder mUnbinder;
    private GroupAdapter mGroupAdapter;
    private GroupsPresenter mPresenter;

    GroupsFragment() {
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
        AppDatabase database = AppDatabase.getDatabase(getContext());
        mPresenter = new GroupsPresenter(DataManager.getInstance(PrefManager.getInstance(getContext().getSharedPreferences(AUTH_SHARED_PREF, Context.MODE_PRIVATE)).getAuthToken(),
                database.roomDao(), database.groupDao()));
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
    public void showGroups(List<Group> groups) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mEmptyText.setVisibility(View.INVISIBLE);
        mGroupList.setVisibility(View.VISIBLE);
        mGroupList.setLayoutManager(linearLayoutManager);
        mGroupAdapter = new GroupAdapter(groups, this);
        mGroupList.setAdapter(mGroupAdapter);
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
