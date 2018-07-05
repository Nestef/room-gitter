package com.nestef.room.main;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nestef.room.R;
import com.nestef.room.model.Room;
import com.nestef.room.provider.RoomProviderContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Noah Steffes on 4/19/18.
 */
public class RoomCursorAdapter extends CursorAdapter {


    private static final String TAG = "RoomCursorAdapter";
    private RoomCallback mCallback;


    public RoomCursorAdapter(Context context, RoomCallback callback) {
        super(context, null, 0);
        mCallback = callback;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.room_list_item, parent, false);

        RoomViewHolder viewHolder = new RoomViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        RoomViewHolder viewHolder = (RoomViewHolder) view.getTag();

        final Room room = new Room();
        room.name = cursor.getString(cursor.getColumnIndexOrThrow(RoomProviderContract.RoomEntry.COLUMN_NAME));
        room.unreadItems = cursor.getInt(cursor.getColumnIndexOrThrow(RoomProviderContract.RoomEntry.COLUMN_UNREAD));
        room.avatarUrl = cursor.getString(cursor.getColumnIndexOrThrow(RoomProviderContract.RoomEntry.COLUMN_AVATARURL));
        room.favourite = cursor.getInt(cursor.getColumnIndexOrThrow(RoomProviderContract.RoomEntry.COLUMN_FAVOURITE));
        room.userCount = cursor.getInt(cursor.getColumnIndexOrThrow(RoomProviderContract.RoomEntry.COLUMN_USER_COUNT));
        room.id = cursor.getString(cursor.getColumnIndexOrThrow(RoomProviderContract.RoomEntry.COLUMN_ID));
        room.roomMember = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(RoomProviderContract.RoomEntry.COLUMN_MEMBER)));

        viewHolder.title.setText(room.name);
        //holder.description.setText(room.topic);
        //holder.userCount.setText(String.valueOf(room.userCount));
        if (room.unreadItems == 0) {
            viewHolder.unread.setVisibility(View.GONE);
        } else {
            viewHolder.unread.setText(String.valueOf(room.unreadItems));
            viewHolder.unread.setVisibility(View.VISIBLE);
        }
        //Todo add error handling
        Glide.with(context).load(room.avatarUrl).into(viewHolder.avatar);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onRoomClick(room);
            }
        });

    }


    public interface RoomCallback {
        void onRoomClick(Room room);
    }

    class RoomViewHolder {

        @BindView(R.id.room_list_item_title)
        TextView title;
        @BindView(R.id.room_list_avatar)
        ImageView avatar;

        @BindView(R.id.room_item_unread)
        TextView unread;

        View view;

        RoomViewHolder(View v) {
            view = v;
            ButterKnife.bind(this, view);

        }
    }
}
