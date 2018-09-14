package com.nestef.room.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nestef.room.R;
import com.nestef.room.data.GlideApp;
import com.nestef.room.model.Room;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Noah Steffes on 5/22/18.
 */
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {
    private static final String TAG = "RoomAdapter";
    private List<Room> mRooms;
    private RoomAdapter.RoomCallback mCallback;
    private boolean mShowJoined = false;

    public RoomAdapter(List<Room> rooms) {
        mRooms = rooms;
    }

    public RoomAdapter(List<Room> rooms, RoomAdapter.RoomCallback callback, boolean showRoomsJoined) {
        mRooms = rooms;
        mCallback = callback;
        mShowJoined = showRoomsJoined;
    }

    @NonNull
    @Override
    public RoomAdapter.RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.room_list_item, parent, false);
        return new RoomAdapter.RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.RoomViewHolder holder, int position) {
        Room room = mRooms.get(position);
        holder.title.setText(room.name);
        //holder.description.setText(room.topic);
        //holder.userCount.setText(String.valueOf(room.userCount));
        if (room.unreadItems == 0) {
            holder.unread.setVisibility(View.GONE);
        } else {
            holder.unread.setText(String.valueOf(room.unreadItems));
        }
        if (mShowJoined) {
            if (room.roomMember) {
                holder.joined.setText(R.string.room_joined);
                holder.joined.setVisibility(View.VISIBLE);
            } else {
                holder.joined.setVisibility(View.GONE);
            }
        } else {
            holder.joined.setVisibility(View.GONE);
        }
        GlideApp.with(holder.itemView).load(room.avatarUrl).into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        if (mRooms == null) return 0;
        return mRooms.size();
    }

    public interface RoomCallback {
        void onRoomClick(Room room);
    }

    class RoomViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.room_list_item_title)
        TextView title;
        @BindView(R.id.room_list_avatar)
        ImageView avatar;
        @BindView(R.id.room_item_unread)
        TextView unread;
        @BindView(R.id.room_joined_text)
        TextView joined;

        RoomViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(v1 -> mCallback.onRoomClick(mRooms.get(getAdapterPosition())));
        }
    }
}
