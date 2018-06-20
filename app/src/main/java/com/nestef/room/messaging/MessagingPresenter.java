package com.nestef.room.messaging;

import com.nestef.room.base.BasePresenter;
import com.nestef.room.data.MessageManager;
import com.nestef.room.model.Message;
import com.nestef.room.model.Room;

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
        if (mView != null) {
            mView.showLoadingIndicator();
        }
    }

    @Override
    public void olderMessages(List<Message> messages) {
        if (mView != null) mView.showOlderMessages(messages);
    }

    @Override
    public void fetchOlderMessages(String beforeId) {
        mManager.getOlderMessages(mRoomId, beforeId, this);
    }

    @Override
    public void sendMessage(String message) {
        mManager.sendMessage(mRoomId, message);
    }

    @Override
    public void markRead(String messageId) {

    }

    @Override
    public void joinRoom() {
        mManager.joinRoom(mUserId, mRoomId);
    }

    @Override
    public void leaveRoom() {
        mManager.leaveRoom(mRoomId, mUserId);
    }

    @Override
    public void unsetView() {
        super.unsetView();
        //make sure to end network tasks
        //mEventStream.dispose();
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
        // mEventStream = mManager.getEventStream(mRoomId).subscribe(new Consumer<Event>() {
        //      @Override
        //     public void accept(Event event) throws Exception {
        //         if (mView != null) mView.addEvent(event);
        //     }
        // });
    }

    @Override
    public void returnMessages(List<Message> messages) {
        if (mView != null) {
            mView.showMessages(messages);
            mView.hideLoadingIndicator();
        }
    }

    @Override
    public void returnRoom(Room room) {
        if (mView != null) {
            mView.updateRoom(room);
            if (room.roomMember) {
                mView.showInputUi();
            } else {
                mView.showJoinUi();
            }
        }
    }

    @Override
    public void checkRoomMembership(Room room) {
        mManager.getRoom(this, room.id);
    }

    @Override
    public void fetchMessageError() {
        if (mView != null) {
            //TODO Placeholder
            mView.showEmpty();
        }
    }
}
