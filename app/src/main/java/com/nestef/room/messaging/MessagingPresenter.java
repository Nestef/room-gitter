package com.nestef.room.messaging;

import android.util.Log;

import com.nestef.room.base.BasePresenter;
import com.nestef.room.data.MessageManager;
import com.nestef.room.model.Event;
import com.nestef.room.model.Message;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Noah Steffes on 3/23/18.
 */

public class MessagingPresenter extends BasePresenter<MessagingContract.MessagingView> implements MessagingContract.ViewActions, MessageManager.Callback {

    private static final String TAG = "MessagingPresenter";

    private String mUserId;

    private String mRoomId;

    private MessageManager mManager;

    private Disposable mMessageStream;

    private Disposable mEventStream;

    public MessagingPresenter(MessageManager messageManager) {
        mManager = messageManager;
    }

    //TODO
    //IMPORTANT TO CHECK IF VIEW IS NULL

    @Override
    public void setUserId(String userId) {
        mUserId = userId;
    }

    @Override
    public void setRoomId(String roomId) {
        mRoomId = roomId;
    }

    @Override
    public void fetchMessages() {
        mManager.getMessages(mRoomId, this);
    }

    @Override
    public void fetchOlderMessages() {

    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void markRead(String messageId) {

    }

    @Override
    public void joinRoom() {

    }

    @Override
    public void leaveRoom() {

    }

    @Override
    public void unsetView() {
        super.unsetView();
        //make sure to end network tasks
        mEventStream.dispose();
        mMessageStream.dispose();
    }

    @Override
    public void setView(MessagingContract.MessagingView view) {
        super.setView(view);
        //start network streaming messages
        mMessageStream = mManager.getMessageStream(mRoomId).subscribe(new Consumer<Message>() {
            @Override
            public void accept(Message message) throws Exception {
                if (mView != null) mView.addMessage(message);
            }
        });
        mEventStream = mManager.getEventStream(mRoomId).subscribe(new Consumer<Event>() {
            @Override
            public void accept(Event event) throws Exception {
                if (mView != null) mView.addEvent(event);
            }
        });
    }

    @Override
    public void returnMessages(List<Message> messages) {

        if (mView != null) {
            Log.d(TAG, "returnMessages: notnull");
            mView.showMessages(messages);
        } else {
            Log.d(TAG, "returnMessages: null");
        }
    }

    @Override
    public void fetchMessageError() {
        if (mView != null) {
            mView.showEmpty();
        }
    }
}
