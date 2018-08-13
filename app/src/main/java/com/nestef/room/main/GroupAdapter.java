package com.nestef.room.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nestef.room.R;
import com.nestef.room.model.Group;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Noah Steffes on 4/24/18.
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private static final String TAG = "GroupAdapter";

    private GroupCallback mCallback;
    private List<Group> mGroups;

    GroupAdapter(List<Group> groups, GroupCallback callback) {
        mGroups = groups;
        mCallback = callback;
    }


    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.group_list_item, parent, false);
        return new GroupAdapter.GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group group = mGroups.get(position);
        holder.title.setText(group.name);
        Glide.with(holder.itemView).load(group.avatarUrl).into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        if (mGroups == null) return 0;
        return mGroups.size();
    }

    public interface GroupCallback {
        void onClick(Group selectedGroup);
    }

    class GroupViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.group_list_item_avatar)
        ImageView avatar;
        @BindView(R.id.group_list_item_title)
        TextView title;

        GroupViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(v1 -> mCallback.onClick(mGroups.get(getAdapterPosition())));
        }
    }

}
