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
import com.nestef.room.model.Group;

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
    ListView groupList;

    @Nullable
    @BindView(R.id.default_toolbar)
    Toolbar toolbar;
    @BindInt(R.integer.is_tablet)
    int isTablet;
    GroupsFragment.OnCommunitySelection mCallback;
    private Unbinder unbinder;
    private GroupAdapter groupAdapter;
    private GroupsPresenter presenter;

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
        presenter = new GroupsPresenter(DataManager.getInstance(getContext().getContentResolver(),
                PrefManager.getInstance(getContext().getSharedPreferences(AUTH_SHARED_PREF, Context.MODE_PRIVATE))),
                new LoaderProvider(getContext()), getLoaderManager());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.groups_fragment, container, false);
        Log.d(TAG, "onCreateView: ");
        unbinder = ButterKnife.bind(this, rootView);
        presenter.setView(this);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (!isTablet()) setHasOptionsMenu(true);

        presenter.fetchGroups();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_actions, menu);
    }

    @Override
    public void showGroups(Cursor groups) {

        groupAdapter = new GroupAdapter(getContext(), this);
        groupList.setAdapter(groupAdapter);
        groupList.setDivider(null);
        groupAdapter.swapCursor(groups);
        groupList.setVisibility(View.VISIBLE);

    }

    @Override
    public void showLoadingIndicator() {

    }

    @Override
    public void hideLoadingIndicator() {

    }

    @Override
    public void showEmpty() {
        groupList.setVisibility(View.GONE);
    }

    private boolean isTablet() {
        return isTablet == 1;
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
