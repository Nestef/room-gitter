package com.nestef.room.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.nestef.room.R;
import com.nestef.room.data.PrefManager;
import com.nestef.room.model.Room;
import com.nestef.room.services.GitterServiceFactory;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.nestef.room.util.Constants.AUTH_SHARED_PREF;
import static com.nestef.room.util.Constants.WIDGET_ROOM_ITEM;

/**
 * Created by Noah Steffes on 5/24/18.
 */
public class RoomWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RoomViewFactory(getApplicationContext(), intent);
    }
}

class RoomViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;

    private List<Room> mRooms;

    RoomViewFactory(Context context, Intent intent) {
        mContext = context;
    }

    private void updateData() {
        if (mRooms == null) {
            mRooms = new ArrayList<>();
        }
        mRooms.clear();
    }

    @Override
    public void onCreate() {
        updateData();
    }

    @Override
    public void onDataSetChanged() {
        updateData();
        try {
            mRooms = GitterServiceFactory
                    .makeApiService(PrefManager.getInstance(mContext.getSharedPreferences(AUTH_SHARED_PREF, Context.MODE_PRIVATE)).getAuthToken())
                    .getRooms()
                    .execute()
                    .body();
            List<Room> remove = new ArrayList<>();
            if (mRooms != null) {
                for (Room room : mRooms) {
                    if (room.oneToOne) {
                        remove.add(room);
                    }
                }

                mRooms.removeAll(remove);
                Collections.sort(mRooms);
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        mRooms.clear();
    }

    @Override
    public int getCount() {
        if (mRooms != null) {
            return mRooms.size();
        }
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        Room room = mRooms.get(position);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);

        rv.setTextViewText(R.id.room_list_item_title_widget, room.name);
        if (room.unreadItems != 0) {
            rv.setViewVisibility(R.id.room_item_unread_widget, View.VISIBLE);
            rv.setTextViewText(R.id.room_item_unread_widget, String.valueOf(room.unreadItems));
        } else {
            rv.setViewVisibility(R.id.room_item_unread_widget, View.INVISIBLE);
        }

        try {
            FutureTarget<Bitmap> bitmap = Glide.with(mContext.getApplicationContext()).asBitmap().load(room.avatarUrl).submit(56, 56);
            Bitmap b = bitmap.get();
            rv.setImageViewBitmap(R.id.room_list_avatar_widget, b);
        } catch (InterruptedException | ExecutionException i) {
            i.printStackTrace();
        }
        //Set fillIn intent for Room
        Intent intent = new Intent();
        intent.putExtra(WIDGET_ROOM_ITEM, Parcels.wrap(room));
        rv.setOnClickFillInIntent(R.id.widget_item, intent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
