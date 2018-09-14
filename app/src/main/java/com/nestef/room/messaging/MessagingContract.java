package com.nestef.room.messaging;

import com.nestef.room.base.BaseView;
import com.nestef.room.model.Event;
import com.nestef.room.model.Message;
import com.nestef.room.model.Room;

import java.util.List;

/**
 * Created by Noah Steffes on 3/23/18.
 */

interface MessagingContract {
    interface ViewActions {

        void setUserId(String userId);

        void setRoomId(String roomId);

        void fetchMessages();

        void fetchOlderMessages(String beforeId);

        void fetchUnreadIds();

        void sendMessage(String message);

        void markRead(List<String> messageIds);

        void checkRoomMembership(Room room);

        void joinRoom();

        void leaveRoom();

    }

    interface MessagingView extends BaseView {
        void showMessages(List<Message> messages);

        void addNewMessages(List<Message> newMessages);

        void addMessage(Message message);

        void addEvent(Event event);

        void showOlderMessages(List<Message> oldMessages);

        void showJoinUi();

        void showInputUi();

        void updateRoom(Room room);

        void networkError();

        boolean checkForConnection();
    }
}
