package com.nestef.room.messaging;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.nestef.room.model.Message;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Noah Steffes on 4/24/18.
 */
public class MessageAdapter extends RecyclerView.Adapter {

    private List<Message> messages;

    MessageAdapter() {
        messages = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addItems(List<Message> newItems) {
        messages.addAll(newItems);
        notifyItemRangeInserted(getItemCount(), messages.size() - 1);
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        MessageViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
