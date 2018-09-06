package com.nestef.room.messaging;

import com.nestef.room.base.BasePresenter;
import com.nestef.room.data.MessageManager;
import com.nestef.room.model.Message;
import com.nestef.room.model.Room;
import com.nestef.room.model.UnreadResponse;

import java.util.List;

import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Noah Steffes on 3/23/18.
 */

public class MessagingPresenter extends BasePresenter<MessagingContract.MessagingView> implements MessagingContract.ViewActions, MessageManager.Callback {

    private static final String TAG = "MessagingPresenter";

    public String mUserId;

    private String mRoomId;

    @Override
    public void returnUnreadIds(List<String> messageIds) {
        markRead(messageIds);
    }

    MessagingPresenter(MessageManager messageManager) {
        mManager = messageManager;
    }

    private MessageManager mManager;

    private Disposable mMessageStream;

    private Disposable mEventStream;

    @Override
    public void fetchUnreadIds() {
        mManager.getUnreadMessages(mUserId, mRoomId, new Callback<UnreadResponse>() {
            @Override
            public void onResponse(Call<UnreadResponse> call, Response<UnreadResponse> response) {
                returnUnreadIds(response.body().chat);
            }

            @Override
            public void onFailure(Call<UnreadResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

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
        if (mView.checkForConnection()) {
            mManager.getMessages(mRoomId, new Callback<List<Message>>() {
                @Override
                public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                    returnMessages(response.body());
                }

                @Override
                public void onFailure(Call<List<Message>> call, Throwable t) {
                    fetchMessageError();
                }
            });
            if (mView != null) {
                mView.showLoadingIndicator();
            }
        } else {
            mView.networkError();
        }
    }

    @Override
    public void olderMessages(List<Message> messages) {
        if (mView != null) {
            mView.showOlderMessages(messages);
            mView.hideLoadingIndicator();
        }
    }

    @Override
    public void fetchOlderMessages(String beforeId) {
        if (mView.checkForConnection()) {
            if (mView != null) {
                mView.showLoadingIndicator();
            }
            mManager.getOlderMessages(mRoomId, beforeId, new Callback<List<Message>>() {
                @Override
                public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                    olderMessages(response.body());
                }

                @Override
                public void onFailure(Call<List<Message>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            mView.networkError();
        }
    }

    @Override
    public void sendMessage(String message) {
        mManager.sendMessage(mRoomId, message);
    }

    @Override
    public void markRead(List<String> messageIds) {
        mManager.markMessagesRead(mUserId, mRoomId, messageIds);
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

        if (mMessageStream != null) {
            mMessageStream.dispose();
        }
    }


    @Override
    public void setView(MessagingContract.MessagingView view) {
        super.setView(view);
        //start network streaming messages

        if (mView.checkForConnection()) {
            mMessageStream = mManager.getMessageStream(mRoomId).subscribe(message -> {
                if (mView != null) mView.addMessage(message);
            });
        } else mView.networkError();
        //mEventStream = mManager.getEventStream(mRoomId).subscribe((Consumer<Event>) event -> {
        // if (mView != null) mView.addEvent(event);
        //});
    }

    @Override
    public void returnMessages(List<Message> messages) {
        if (mView != null) {
            mView.hideLoadingIndicator();
            if (messages == null || messages.size() == 0) {
                mView.showEmpty();
            } else {
                mView.showMessages(messages);
            }
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
        mManager.getRoom(room.id, new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                returnRoom(response.body());
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void fetchMessageError() {
        if (mView != null) {
            mView.showEmpty();
        }
    }
}
