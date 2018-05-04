package com.nestef.room.messaging;

import com.nestef.room.base.BaseView;
import com.nestef.room.model.Message;

import java.util.List;

/**
 * Created by Noah Steffes on 3/23/18.
 */

public interface MessagingContract {
    interface ViewActions{
        void fetchMessages();

        void fetchOlderMessages();

        void sendMessage(String message);

        void markRead(String messageId);
    }
    interface MessagingView extends BaseView{
        void showMessages(List<Message> messages);

        void addNewMessages(List<Message> newMessages);

        void showOlderMessages(List<Message> oldMessages);
    }
}
