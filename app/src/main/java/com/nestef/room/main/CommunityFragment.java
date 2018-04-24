package com.nestef.room.main;

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
import com.nestef.room.model.Group;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Noah Steffes on 4/1/18.
 */

public class CommunityFragment extends Fragment implements MainContract.CommunityView {

    private static final String TAG = "CommunityFragment";
    @BindView(R.id.default_toolbar)
    Toolbar toolbar;
    @BindView(R.id.community_list)
    RecyclerView groupList;

    private Unbinder unbinder;
    private GroupAdapter groupAdapter;
    private CommunityPresenter presenter;

    public CommunityFragment() {

    }

    public static CommunityFragment newInstance() {
        Bundle args = new Bundle();
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
        View rootView = inflater.inflate(R.layout.community_fragment, container, false);
        Log.d(TAG, "onCreateView: ");
        unbinder = ButterKnife.bind(this, rootView);
        presenter.setView(this);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
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
    public void showGroups(List<Group> groups) {
        groupList.setLayoutManager(new LinearLayoutManager(getContext()));
        groupAdapter = new GroupAdapter(groups);
        groupList.setAdapter(groupAdapter);

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


}
