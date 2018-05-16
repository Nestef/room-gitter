package com.nestef.room.messaging;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.nestef.room.R;
import com.nestef.room.model.Message;
import com.nestef.room.model.Room;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Noah Steffes on 4/13/18.
 */
public class MessagingFragment extends Fragment implements MessagingContract.MessagingView {

    private static final String ROOM_KEY = "room";

    Unbinder mUnbinder;
    MessagingPresenter mPresenter;
    MessageAdapter mMessageAdapter;
    Room mRoom;

    @BindView(R.id.message_list)
    RecyclerView mMessageList;
    @BindView(R.id.message_input)
    EditText mInputField;
    @BindView(R.id.message_send_iv)
    ImageView mSend;
    @BindInt(R.integer.is_tablet)
    int mIsTablet;
    @Nullable
    @BindView(R.id.message_toolbar)
    Toolbar mToolbar;

    public MessagingFragment() {
    }

    public static MessagingFragment newInstance(Room room) {

        Bundle args = new Bundle();
        args.putParcelable(ROOM_KEY, Parcels.wrap(room));
        MessagingFragment fragment = new MessagingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showMessages(List<Message> messages) {
        mMessageList.setLayoutManager(new LinearLayoutManager(getContext()));
        mMessageAdapter = new MessageAdapter(messages);
        mMessageList.setAdapter(mMessageAdapter);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MessagingPresenter();
        mRoom = Parcels.unwrap(getArguments().getParcelable(ROOM_KEY));
    }

    @Override
    public void addNewMessages(List<Message> newMessages) {
        mMessageAdapter.addItems(newMessages);
    }

    @Override
    public void showOlderMessages(List<Message> oldMessages) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.messaging_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mPresenter.setView(this);
        if (!isTablet()) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setTitle(mRoom.name);
        mPresenter.fetchMessages();
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.tempNew();
            }
        });
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
}
