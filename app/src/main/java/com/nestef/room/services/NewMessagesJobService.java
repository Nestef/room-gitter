package com.nestef.room.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.MessagingStyle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.preference.PreferenceManager;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.nestef.room.R;
import com.nestef.room.data.PrefManager;
import com.nestef.room.main.MainActivity;
import com.nestef.room.model.Message;
import com.nestef.room.model.Room;
import com.nestef.room.model.UnreadResponse;
import com.nestef.room.provider.RoomProviderContract;
import com.nestef.room.util.Constants;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.noties.markwon.Markwon;

import static android.support.v4.app.NotificationCompat.GROUP_ALERT_SUMMARY;
import static android.text.Html.fromHtml;
import static com.nestef.room.util.Constants.NOTIFICATION_CHANNEL_ID;
import static com.nestef.room.util.Constants.NOTIFICATION_GROUP_ID;
import static com.nestef.room.util.Constants.WIDGET_ROOM_ITEM;

/**
 * Created by Noah Steffes on 7/23/18.
 */
public class NewMessagesJobService extends JobService {

    private static final String TAG = "NewMessagesJobService";

    GitterApiService mApiService;

    boolean mGroupSummary = false;

    @Override
    public boolean onStartJob(final JobParameters job) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!preferences.getBoolean(getString(R.string.notification_pref_key), false)) {
            new Thread(() -> {

                PrefManager prefManager = PrefManager
                        .getInstance(getSharedPreferences(Constants.AUTH_SHARED_PREF, MODE_PRIVATE));
                mApiService = GitterServiceFactory.makeApiService(prefManager.getAuthToken());

                Cursor[] cursors = getRooms();

                List<UnreadResponse> roomResponses = new ArrayList<>();
                List<UnreadResponse> privateRoomResponses = new ArrayList<>();
                List<Room> rooms = new ArrayList<>();
                List<Room> privateRooms = new ArrayList<>();
                String userId = prefManager.getUserId();

                // for subscribed rooms
                // make api call for unreadmessages
                Cursor cursor0 = cursors[0];
                for (int i = 0; i < cursor0.getCount(); i++) {
                    cursor0.moveToPosition(i);
                    Room room = getRoomsFromCursor(cursor0);
                    try {
                        UnreadResponse response = mApiService.getUnread(userId, room.id).execute().body();
                        roomResponses.add(response);
                        rooms.add(room);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Cursor cursor1 = cursors[1];
                for (int i = 0; i < cursor1.getCount(); i++) {

                    cursor1.moveToPosition(i);
                    Room room = getRoomsFromCursor(cursor1);
                    try {
                        UnreadResponse response = mApiService.getUnread(userId, room.id).execute().body();
                        privateRoomResponses.add(response);
                        privateRooms.add(room);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //close cursors
                for (Cursor c : cursors) {
                    c.close();
                }
                processUnread(rooms, roomResponses, true);
                processUnread(privateRooms, privateRoomResponses, false);

                if (mGroupSummary) {
                    final Notification summaryNotification = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_notification)
                            .setStyle(new NotificationCompat.InboxStyle())
                            .setGroup(NOTIFICATION_GROUP_ID)
                            .setContentText(getString(R.string.new_message_summary))
                            .setGroupSummary(true)
                            .build();
                    ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(875, summaryNotification);
                    mGroupSummary = false;
                }
                jobFinished(job, true);
            }).start();
        }
        return true;
    }

    private void processUnread(List<Room> rooms, List<UnreadResponse> responses, boolean roomtype) {
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            UnreadResponse response = responses.get(i);
            //if room has new unread messages
            if (room.unreadItems < response.chat.size()) {
                List<String> messageIds = new ArrayList<>();
                List<Message> messages = new ArrayList<>();
                //iterate over unread messages and add new ones to List
                for (int j = 0; j < response.chat.size(); j++) {
                    if (j >= room.unreadItems) {
                        messageIds.add(response.chat.get(j));
                    }
                }
                //get new Unread Message objects and create notification for this room
                try {
                    for (String id : messageIds) {
                        Message m = mApiService.getMessageById(room.id, id).execute().body();
                        messages.add(m);
                    }
                    sendNotification(this, messages, room);
                    updateRoom(roomtype, room.name, response.chat.size());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateRoom(boolean roomType, String name, int unreadnumber) {
        if (roomType) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(RoomProviderContract.RoomEntry.COLUMN_UNREAD, unreadnumber);
            getContentResolver().update(RoomProviderContract.RoomEntry.CONTENT_URI, contentValues, RoomProviderContract.RoomEntry.COLUMN_NAME + " =?", new String[]{name});
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(RoomProviderContract.PrivateRoomEntry.COLUMN_UNREAD, unreadnumber);
            getContentResolver().update(RoomProviderContract.PrivateRoomEntry.CONTENT_URI, contentValues, RoomProviderContract.PrivateRoomEntry.COLUMN_NAME + " =?", new String[]{name});
        }
    }

    private Room getRoomsFromCursor(Cursor cursor) {
        Room room = new Room();
        room.id = cursor.getString(cursor.getColumnIndexOrThrow(RoomProviderContract.RoomEntry.COLUMN_ID));
        room.unreadItems = cursor.getInt(cursor.getColumnIndexOrThrow(RoomProviderContract.RoomEntry.COLUMN_UNREAD));
        room.name = cursor.getString(cursor.getColumnIndexOrThrow(RoomProviderContract.RoomEntry.COLUMN_NAME));
        return room;
    }

    private void sendNotification(Context context, List<Message> messages, Room room) {
        mGroupSummary = true;

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        MessagingStyle messagingStyle = new MessagingStyle("")
                .setConversationTitle(room.name);
        for (Message m : messages) {
            messagingStyle.addMessage(Markwon.markdown(context, m.text), m.sent.getTime(), fromHtml("<b>" + m.fromUser.name + ":</b>"));
        }

        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(WIDGET_ROOM_ITEM, Parcels.wrap(room));
        intent.setAction(Constants.WIDGET_CLICK_ACTION);

        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(intent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setStyle(messagingStyle)
                .setContentTitle(room.name)
                .setContentText(messages.size() + getString(R.string.room_new_message_count_text) + room.name)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setGroupAlertBehavior(GROUP_ALERT_SUMMARY)
                .setGroup(NOTIFICATION_GROUP_ID)
                .build();
        notificationManager.notify(room.name.hashCode(), notification);
    }


    private Cursor[] getRooms() {
        Cursor cursor = getContentResolver().query(RoomProviderContract.RoomEntry.CONTENT_URI,
                null, null, null,
                null);
        Cursor prCursor = getContentResolver().query(RoomProviderContract.PrivateRoomEntry.CONTENT_URI,
                null, null, null,
                null);
        return new Cursor[]{cursor, prCursor};
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
