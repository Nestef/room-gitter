package com.nestef.room.messaging;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.nestef.room.R;
import com.nestef.room.data.GlideApp;
import com.nestef.room.model.Message;
import com.nestef.room.model.User;
import com.nestef.room.util.PatternEditableBuilder;

import java.text.DateFormat;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.noties.markwon.Markwon;

/**
 * Created by Noah Steffes on 4/24/18.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> implements ListPreloader.PreloadModelProvider<Message> {

    private List<Message> mMessages;

    private Context mContext;

    private MentionClickHandler mHandler;

    MessageAdapter(List<Message> messages1, Context context, MentionClickHandler handler) {
        mMessages = messages1;
        mContext = context;
        mHandler = handler;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        final Message message = mMessages.get(position);
        User user = message.fromUser;

        //TODO set placeholder
        GlideApp.with(holder.itemView).load(user.avatarUrlSmall).placeholder(R.drawable.ic_poll_black_24dp).into(holder.avatar);
        holder.displayName.setText(user.getUsername());
        Markwon.setMarkdown(holder.content, message.text);
        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\@(\\w+)"), text -> mHandler.onMentionClick(text))
                .into(holder.content);
        String time = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(message.sent);
        holder.timeStamp.setText(time);
    }

    public String getMessageIdByPosition(int position) {
        return mMessages.get(position).id;
    }

    @Override
    public int getItemCount() {
        if (mMessages == null) return 0;
        return mMessages.size();
    }

    public void addItems(List<Message> newItems) {
        mMessages.addAll(0, newItems);
        notifyItemRangeInserted(0, newItems.size() - 1);
    }

    public void addItem(Message item) {
        mMessages.add(item);
        notifyItemInserted(mMessages.size() - 1);
    }

    @NonNull
    @Override
    public List<Message> getPreloadItems(int position) {
        return Collections.singletonList(mMessages.get(position));
    }

    @Nullable
    @Override
    public RequestBuilder<?> getPreloadRequestBuilder(@NonNull Message item) {
        return GlideApp.with(mContext).load(item.fromUser.avatarUrlSmall).placeholder(R.drawable.ic_poll_black_24dp);
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.message_user_display_name)
        TextView displayName;
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

    public interface MentionClickHandler {
        void onMentionClick(String mention);
    }

}
