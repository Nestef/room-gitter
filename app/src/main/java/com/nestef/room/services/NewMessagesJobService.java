package com.nestef.room.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.nestef.room.R;
import com.nestef.room.data.PrefManager;
import com.nestef.room.db.AppDatabase;
import com.nestef.room.db.RoomDao;
import com.nestef.room.main.MainActivity;
import com.nestef.room.model.Message;
import com.nestef.room.model.Room;
import com.nestef.room.model.UnreadResponse;
import com.nestef.room.util.Constants;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.MessagingStyle;
import androidx.core.app.TaskStackBuilder;
import androidx.preference.PreferenceManager;
import retrofit2.Response;
import ru.noties.markwon.Markwon;

import static android.text.Html.fromHtml;
import static androidx.core.app.NotificationCompat.GROUP_ALERT_SUMMARY;
import static com.nestef.room.util.Constants.NOTIFICATION_CHANNEL_ID;
import static com.nestef.room.util.Constants.NOTIFICATION_GROUP_ID;
import static com.nestef.room.util.Constants.WIDGET_ROOM_ITEM;

/**
 * Created by Noah Steffes on 7/23/18.
 */
public class NewMessagesJobService extends JobService {

    private static final String TAG = "NewMessagesJobService";

    private GitterApiService mApiService;

    private boolean mGroupSummary = false;

    private RoomDao mDao;

    @Override
    public boolean onStartJob(final JobParameters job) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!preferences.getBoolean(getString(R.string.notification_pref_key), false)) {
            new Thread(() -> {

                PrefManager prefManager = PrefManager
                        .getInstance(getSharedPreferences(Constants.AUTH_SHARED_PREF, MODE_PRIVATE));
                AppDatabase appDatabase = AppDatabase.getDatabase(this);
                mDao = appDatabase.roomDao();
                if (prefManager.getAuthToken() == null) {
                    jobFinished(job, false);
                }
                mApiService = GitterServiceFactory.makeApiService(prefManager.getAuthToken());

                List<List<Room>> data = getRooms(mDao);

                List<UnreadResponse> roomResponses = new ArrayList<>();
                List<UnreadResponse> privateRoomResponses = new ArrayList<>();
                List<Room> rooms = data.get(0);
                List<Room> privateRooms = data.get(1);
                String userId = prefManager.getUserId();

                // for subscribed rooms
                // make api call for unreadmessages
                if (rooms != null) {
                    for (int i = 0; i < rooms.size(); i++) {
                        Room room = rooms.get(i);
                        try {
                            if (userId != null) {
                                Response<UnreadResponse> response = mApiService.getUnread(userId, room.id).execute();
                                if (response.code() == 200) {
                                    roomResponses.add(response.body());
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    processUnread(rooms, roomResponses, true);
                }
                if (privateRooms != null) {
                    for (int i = 0; i < privateRooms.size(); i++) {
                        Room room = privateRooms.get(i);
                        try {
                            if (userId != null) {
                                UnreadResponse response = mApiService.getUnread(userId, room.id).execute().body();
                                privateRoomResponses.add(response);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    processUnread(privateRooms, privateRoomResponses, false);
                }

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
                    updateRoom(room, response.chat.size());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateRoom(Room room, int unreadCount) {
        room.unreadItems = unreadCount;
        mDao.updateRoom(room);
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


    private List<List<Room>> getRooms(RoomDao roomDao) {
        List<Room> rooms = roomDao.getRooms().getValue();
        List<Room> pRooms = roomDao.getPrivateRooms().getValue();
        return new ArrayList<>(Arrays.asList(rooms, pRooms));
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
