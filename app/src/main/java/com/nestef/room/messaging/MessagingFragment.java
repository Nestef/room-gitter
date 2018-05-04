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

import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Noah Steffes on 4/13/18.
 */
public class MessagingFragment extends Fragment implements MessagingContract.MessagingView {

    Unbinder unbinder;
    MessagingPresenter presenter;
    MessageAdapter messageAdapter;

    @BindView(R.id.message_list)
    RecyclerView messageList;
    @BindView(R.id.message_input)
    EditText inputField;
    @BindView(R.id.message_send_iv)
    ImageView send;
    @BindInt(R.integer.is_tablet)
    int isTablet;
    @Nullable
    @BindView(R.id.message_toolbar)
    Toolbar toolbar;

    @Override
    public void showMessages(List<Message> messages) {
        messageList.setLayoutManager(new LinearLayoutManager(getContext()));
        messageAdapter = new MessageAdapter(messages);
        messageList.setAdapter(messageAdapter);

    }

    public MessagingFragment() {
    }

    public static MessagingFragment newInstance() {

        Bundle args = new Bundle();

        MessagingFragment fragment = new MessagingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MessagingPresenter();
    }

    @Override
    public void addNewMessages(List<Message> newMessages) {
        messageAdapter.addItems(newMessages);
    }

    @Override
    public void showOlderMessages(List<Message> oldMessages) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.messaging_fragment, container);
        unbinder = ButterKnife.bind(this, view);
        presenter.setView(this);
        if (!isTablet()) ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        presenter.fetchMessages();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.tempNew();
            }
        });
        return view;
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
}
