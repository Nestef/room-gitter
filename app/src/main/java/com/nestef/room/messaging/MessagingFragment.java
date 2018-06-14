package com.nestef.room.messaging;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.FixedPreloadSizeProvider;
import com.nestef.room.R;
import com.nestef.room.data.MessageManager;
import com.nestef.room.data.PrefManager;
import com.nestef.room.model.Event;
import com.nestef.room.model.Message;
import com.nestef.room.model.Room;
import com.nestef.room.util.Constants;

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

    private static final String TAG = "MessagingFragment";

    private static final String ROOM_KEY = "room";

    Unbinder mUnbinder;
    MessagingPresenter mPresenter;
    MessageAdapter mMessageAdapter;
    Room mRoom;

    boolean loading;

    @BindView(R.id.message_list)
    RecyclerView mMessageList;
    @BindView(R.id.message_input)
    EditText mInputField;
    @BindView(R.id.message_send_iv)
    ImageView mSend;
    @BindView(R.id.message_layout_ll)
    LinearLayout mInputLayout;
    @BindView(R.id.room_join_layout)
    LinearLayout mJoinLayout;
    @BindInt(R.integer.is_tablet)
    int mIsTablet;
    @BindView(R.id.message_loading_pb)
    ProgressBar mProgress;
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
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mMessageList.setLayoutManager(layoutManager);
        layoutManager.setSmoothScrollbarEnabled(true);
        mMessageAdapter = new MessageAdapter(messages, getContext());
        mMessageList.setAdapter(mMessageAdapter);
        mMessageList.scrollToPosition(mMessageAdapter.getItemCount() - 1);

        FixedPreloadSizeProvider<Message> sizeProvider = new FixedPreloadSizeProvider<>(40, 40);
        RecyclerViewPreloader<Message> preloader = new RecyclerViewPreloader<>(this, mMessageAdapter, sizeProvider, 5);
        mMessageList.addOnScrollListener(preloader);

        mMessageList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItem == 5 && !loading) {
                    mPresenter.fetchOlderMessages(mMessageAdapter.getMessageIdByPosition(0));
                    showLoadingIndicator();
                    loading = true;
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MessagingPresenter(MessageManager.getInstance(PrefManager.getInstance(getContext().getSharedPreferences(Constants.AUTH_SHARED_PREF, Context.MODE_PRIVATE))));
        if (getArguments() != null) {
            mRoom = Parcels.unwrap(getArguments().getParcelable(ROOM_KEY));
            mPresenter.setRoomId(mRoom.id);
            mPresenter.setUserId(PrefManager.getInstance(getContext().getSharedPreferences(Constants.AUTH_SHARED_PREF, Context.MODE_PRIVATE)).getUserId());

        }
    }

    @Override
    public void addNewMessages(List<Message> newMessages) {
        mMessageAdapter.addItems(newMessages);
    }

    @Override
    public void addMessage(Message message) {
        if (mMessageAdapter != null) {
            mMessageAdapter.addItem(message);   
        }
        Log.d(TAG, "addMessage: " + message.text);
    }

    @Override
    public void addEvent(Event event) {

    }

    @Override
    public void showOlderMessages(List<Message> oldMessages) {
        hideLoadingIndicator();
        mMessageAdapter.addItems(oldMessages);
        loading = false;
    }

    @Override
    public void showJoinUi() {
        mInputLayout.setVisibility(View.GONE);
        mJoinLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showInputUi() {
        mJoinLayout.setVisibility(View.GONE);
        mInputLayout.setVisibility(View.VISIBLE);

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
        if (mRoom != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mRoom.name);
            
        }
        mPresenter.checkRoomMembership(mRoom);
        mPresenter.fetchMessages();
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
        mProgress.setIndeterminate(true);
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {

    }

    private boolean isTablet() {
        return mIsTablet == 1;
    }


}
