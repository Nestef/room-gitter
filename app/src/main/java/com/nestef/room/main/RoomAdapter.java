package com.nestef.room.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nestef.room.R;
import com.nestef.room.model.Room;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Noah Steffes on 4/19/18.
 */
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private static final String TAG = RoomAdapter.class.getName();
    private List<Room> mRooms;

    public RoomAdapter(List<Room> rooms) {
        mRooms = rooms;
    }

    @NonNull
    @Override
    public RoomAdapter.RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.room_list_item, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.RoomViewHolder holder, int position) {
        //todo we need proper data validation here
        Room room = mRooms.get(position);
        Log.d(TAG, "onBindViewHolder: " + room.name + room.userCount);
        Log.d(TAG, "onBindViewHolder: " + room.lastAccessTime.toInstant());
        holder.title.setText(room.name);
        //holder.description.setText(room.topic);
        //holder.userCount.setText(String.valueOf(room.userCount));
        if (room.unreadItems == 0) {
            holder.unread.setVisibility(View.GONE);
        } else {
            holder.unread.setText(String.valueOf(room.unreadItems));
        }
        //Todo add error handling
        Glide.with(holder.itemView).load(room.avatarUrl).into(holder.avatar);

    }

    @Override
    public int getItemCount() {
        if (mRooms == null) return 0;
        return mRooms.size();
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
        }
    }
}
