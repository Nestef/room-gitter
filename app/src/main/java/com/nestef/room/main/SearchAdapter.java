package com.nestef.room.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nestef.room.R;
import com.nestef.room.data.GlideApp;
import com.nestef.room.model.Room;
import com.nestef.room.model.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Noah Steffes on 8/2/18.
 */
class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "RoomAdapter";
    private final int ROOM = 0, USER = 1;
    private List<?> mRooms;
    private RoomAdapter.RoomCallback mCallback;
    private UserCallback mUserCallback;

    public SearchAdapter(List<Room> rooms, RoomAdapter.RoomCallback callback, UserCallback userCallback) {
        mRooms = rooms;
        mCallback = callback;
        mUserCallback = userCallback;
    }

    @Override
    public int getItemViewType(int position) {
        if (mRooms.get(position) instanceof Room) {
            return ROOM;
        } else if (mRooms.get(position) instanceof User) {
            return USER;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.room_list_item, parent, false);
        switch (viewType) {
            case ROOM:
                viewHolder = new RoomViewHolder(view);
                break;
            case USER:
                viewHolder = new UserViewHolder(view);
                break;
            default:
                viewHolder = new UserViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case ROOM:
                Room room = (Room) mRooms.get(position);
                RoomViewHolder viewHolder = (RoomViewHolder) holder;

                viewHolder.title.setText(room.name);
                //holder.description.setText(room.topic);
                //holder.userCount.setText(String.valueOf(room.userCount));
                if (room.unreadItems == 0) {
                    viewHolder.unread.setVisibility(View.GONE);
                } else {
                    viewHolder.unread.setText(String.valueOf(room.unreadItems));
                }
                GlideApp.with(viewHolder.itemView).load(room.avatarUrl).into(viewHolder.avatar);
            case USER:
                User user = (User) mRooms.get(position);
                UserViewHolder viewHolder1 = (UserViewHolder) holder;
                viewHolder1.title.setText(user.getUsername());
                GlideApp.with(viewHolder1.itemView).load(user.avatarUrl).into(viewHolder1.avatar);
            default:
        }
    }

    @Override
    public int getItemCount() {
        if (mRooms == null) return 0;
        return mRooms.size();
    }

    interface UserCallback {
        void onUserClick(User user);
    }

    class RoomViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.room_list_item_title)
        TextView title;
        @BindView(R.id.room_list_avatar)
        ImageView avatar;

        @BindView(R.id.room_item_unread)
        TextView unread;

        RoomViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(v1 -> mCallback.onRoomClick((Room) mRooms.get(getAdapterPosition())));
        }
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.room_list_item_title)
        TextView title;
        @BindView(R.id.room_list_avatar)
        ImageView avatar;

        View view;

        UserViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(v1 -> mUserCallback.onUserClick((User) mRooms.get(getAdapterPosition())));
        }
    }
}
