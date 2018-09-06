package com.nestef.room.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nestef.room.model.Event;
import com.nestef.room.model.Message;
import com.nestef.room.model.Room;
import com.nestef.room.model.UnreadResponse;
import com.nestef.room.services.GitterApiService;
import com.nestef.room.services.GitterServiceFactory;
import com.nestef.room.services.GitterStreamingService;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Noah Steffes on 6/3/18.
 */
public class MessageManager {

    private static final String TAG = "MessageManager";
    private static MessageManager sInstance;
    private GitterApiService mApiService;
    private GitterStreamingService mStreamingService;
    private ObjectMapper mObjectMapper;
    private retrofit2.Callback emptyCallback = new retrofit2.Callback() {
        @Override
        public void onResponse(Call call, Response response) {

        }

        @Override
        public void onFailure(Call call, Throwable t) {
            t.printStackTrace();
        }
    };

    private MessageManager(PrefManager prefManager) {
        mApiService = GitterServiceFactory.makeApiService(prefManager.getAuthToken());
        mStreamingService = GitterServiceFactory.makeStreamingService(prefManager.getAuthToken());
    }

    public static MessageManager getInstance(PrefManager prefManager) {
        if (sInstance == null) {
            sInstance = new MessageManager(prefManager);
        }
        return sInstance;
    }

    public void getUnreadMessages(String userId, String roomId, retrofit2.Callback<UnreadResponse> callback) {
        mApiService.getUnread(userId, roomId).enqueue(callback);
    }

    public void sendMessage(String roomId, String messageText) {
        mApiService.sendMessage(roomId, messageText).enqueue(emptyCallback);
    }

    public void editMessage(String roomId, String messageId, String messageText) {
        mApiService.editMessage(roomId, messageId, messageText).enqueue(emptyCallback);
    }

    public void markMessagesRead(String userId, String roomId, List<String> messageIds) {
        mApiService.readMessages(userId, roomId, messageIds).enqueue(emptyCallback);
    }

    public void getMessages(String roomId, retrofit2.Callback<List<Message>> callback) {
        mApiService.getMessages(roomId).enqueue(callback);
    }

    public void getOlderMessages(String roomId, String beforeId, retrofit2.Callback<List<Message>> callback) {
        mApiService.getMessagesBeforeMessage(roomId, beforeId).enqueue(callback);
    }

    public void getRoom(String roomId, retrofit2.Callback<Room> callback) {
        mApiService.getRoomById(roomId).enqueue(callback);
    }

    public void joinRoom(String userId, String roomId) {
        mApiService.joinRoom(userId, roomId).enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void leaveRoom(String roomId, String userId) {
        mApiService.leaveRoom(roomId, userId).enqueue(emptyCallback);
    }

    public Observable<Message> getMessageStream(String roomId) {
        return mStreamingService.getMessageStream(roomId)
                .flatMap(responseBody -> observableSource(responseBody.source()))
                .filter(s -> !(s.equals("\n") || s.equals(" ")))
                .map(s -> {
                    if (mObjectMapper == null) {
                        mObjectMapper = new ObjectMapper();
                    }
                    return mObjectMapper.readValue(s, Message.class);
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<Event> getEventStream(String roomId) {
        return mStreamingService.getEventStream(roomId)
                .flatMap(responseBody -> observableSource(responseBody.source()))
                .filter(s -> !(s.equals("\n") || s.equals(" ")))
                .map(s -> {
                    if (mObjectMapper == null) {
                        mObjectMapper = new ObjectMapper();
                    }
                    return mObjectMapper.readValue(s, Event.class);
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<String> observableSource(final BufferedSource source) {
        return Observable.create(emitter -> {
            boolean isCompleted = false;
            try {
                while (!emitter.isDisposed()) {
                    emitter.onNext(source.readUtf8Line());
                }
            } catch (IOException e) {
                if (e.getMessage() == null || e.getMessage().equals("Socket closed")) {
                    isCompleted = true;
                    emitter.onComplete();
                } else {
                    throw e;
                }
            }
            if (!isCompleted) {
                emitter.onComplete();
            }
        });
    }

    public interface Callback {
        void returnMessages(List<Message> messages);

        void olderMessages(List<Message> messages);

        void fetchMessageError();

        void returnRoom(Room room);

        void returnUnreadIds(List<String> messageIds);
    }

}
