package com.nestef.room.main;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nestef.room.R;
import com.nestef.room.model.Group;
import com.nestef.room.provider.RoomProviderContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Noah Steffes on 4/24/18.
 */
public class GroupAdapter extends CursorAdapter {
    private static final String TAG = "GroupAdapter";

    private GroupCallback mCallback;

    //Todo change to empty constructor
    public GroupAdapter(Context context, GroupCallback callback) {
        super(context, null, 0);
        mCallback = callback;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_list_item, parent, false);

        GroupViewHolder viewHolder = new GroupAdapter.GroupViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        GroupViewHolder viewHolder = (GroupViewHolder) view.getTag();

        final Group group = new Group();
        group.name = cursor.getString(cursor.getColumnIndexOrThrow(RoomProviderContract.GroupEntry.COLUMN_NAME));
        group.avatarUrl = cursor.getString(cursor.getColumnIndexOrThrow(RoomProviderContract.GroupEntry.COLUMN_AVATARURL));
        group.uri = cursor.getString(cursor.getColumnIndexOrThrow(RoomProviderContract.GroupEntry.COLUMN_URI));
        group.homeUri = cursor.getString(cursor.getColumnIndexOrThrow(RoomProviderContract.GroupEntry.COLUMN_HOMEURI));
        group.id = cursor.getString(cursor.getColumnIndexOrThrow(RoomProviderContract.GroupEntry.COLUMN_ID));

        viewHolder.title.setText(group.name);
        Glide.with(viewHolder.view).load(group.avatarUrl).into(viewHolder.avatar);
        viewHolder.view.setOnClickListener(v -> mCallback.onClick(group));
    }

    public interface GroupCallback {
        void onClick(Group selectedGroup);
    }

    class GroupViewHolder {
        @BindView(R.id.group_list_item_avatar)
        ImageView avatar;
        @BindView(R.id.group_list_item_title)
        TextView title;

        View view;

        GroupViewHolder(View v) {
            view = v;
            ButterKnife.bind(this, view);
        }
    }
}
