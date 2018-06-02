package com.nestef.room.messaging;

import com.nestef.room.base.BasePresenter;

/**
 * Created by Noah Steffes on 3/23/18.
 */

public class MessagingPresenter extends BasePresenter<MessagingContract.MessagingView> implements MessagingContract.ViewActions {
    @Override
    public void fetchMessages() {

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

    public void tempNew() {

    }

    @Override
    public void unsetView() {
        super.unsetView();
        //make sure to end network tasks
    }

    @Override
    public void setView(MessagingContract.MessagingView view) {
        super.setView(view);
        //start network streaming messages
    }
}
