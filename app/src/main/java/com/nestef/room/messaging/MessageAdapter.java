package com.nestef.room.messaging;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nestef.room.R;
import com.nestef.room.model.Message;
import com.nestef.room.model.User;

import java.text.DateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.noties.markwon.Markwon;

/**
 * Created by Noah Steffes on 4/24/18.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messages;

    MessageAdapter(List<Message> messages1) {
        messages = messages1;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        User user = message.fromUser;

        Glide.with(holder.itemView).load(user.avatarUrlSmall).into(holder.avatar);
        String name = user.displayName;
        if (name == null) {
            holder.displayName.setText(user.username);
        } else {
            holder.displayName.setText(user.displayName);
        }
        holder.userName.setText(user.username);
        Markwon.setMarkdown(holder.content, message.text);
        String time = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(message.sent);
        holder.timeStamp.setText(time);
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
        @BindView(R.id.message_user_display_name)
        TextView displayName;
        @BindView(R.id.message_user_name)
        TextView userName;
        @BindView(R.id.message_timestamp)
        TextView timeStamp;
        @BindView(R.id.message_content)
        TextView content;
        @BindView(R.id.message_user_avatar)
        ImageView avatar;

        MessageViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
