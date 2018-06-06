package com.nestef.room.data;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nestef.room.model.Event;
import com.nestef.room.model.Message;
import com.nestef.room.services.GitterApiService;
import com.nestef.room.services.GitterServiceFactory;
import com.nestef.room.services.GitterStreamingService;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/**
 * Created by Noah Steffes on 6/3/18.
 */
public class MessageManager {

    private static final String TAG = "MessageManager";
    private static MessageManager sInstance;
    private GitterApiService mApiService;
    private GitterStreamingService mStreamingService;
    private Callback mCallback;
    private PrefManager mPrefManager;
    private ObjectMapper mObjectMapper;

    private MessageManager(PrefManager prefManager) {
        mPrefManager = prefManager;
        mApiService = GitterServiceFactory.makeApiService(prefManager.getAuthToken());
        mStreamingService = GitterServiceFactory.makeStreamingService(prefManager.getAuthToken());
    }

    public static MessageManager getInstance(PrefManager prefManager) {
        if (sInstance == null) {
            sInstance = new MessageManager(prefManager);
        }
        return sInstance;
    }

    public void sendMessage() {
    }


    public void getMessages(String roomId, Callback callback) {
        mCallback = callback;
        new MessageAsyncTask().execute(roomId);
    }

    public Observable<Message> getMessageStream(String roomId) {
        return mStreamingService.getMessageStream(roomId)
                .flatMap(responseToObservable())
                .filter(streamFilter())
                .map(toMessage())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<Event> getEventStream(String roomId) {
        return mStreamingService.getEventStream(roomId)
                .flatMap(responseToObservable())
                .filter(streamFilter())
                .map(toEvent())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Function<String, Event> toEvent() {
        return new Function<String, Event>() {
            @Override
            public Event apply(String s) throws Exception {
                if (mObjectMapper == null) {
                    mObjectMapper = new ObjectMapper();
                }
                return mObjectMapper.convertValue(s, Event.class);
            }
        };
    }

    private Function<String, Message> toMessage() {
        return new Function<String, Message>() {
            @Override
            public Message apply(String s) throws Exception {
                if (mObjectMapper == null) {
                    mObjectMapper = new ObjectMapper();
                }
                return mObjectMapper.readValue(s, Message.class);
            }
        };
    }

    private Predicate<String> streamFilter() {
        return new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {
                return !(s.equals("\n") || s.equals(" "));
            }
        };
    }

    private Function<ResponseBody, ObservableSource<String>> responseToObservable() {
        return new Function<ResponseBody, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(ResponseBody responseBody) throws Exception {
                return observableSource(responseBody.source());
            }
        };
    }

    private Observable<String> observableSource(final BufferedSource source) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                boolean isCompleted = false;
                try {
                    while (!emitter.isDisposed()) {
                        emitter.onNext(source.readUtf8Line());
                    }
                } catch (IOException e) {
                    Log.d(TAG, "subscribe: " + e.getMessage());
                    if (e.getMessage().equals("Socket closed")) {
                        isCompleted = true;
                        emitter.onComplete();
                    } else {
                        throw e;
                    }
                }
                if (!isCompleted) {
                    emitter.onComplete();
                }
            }
        });
    }

    public interface Callback {
        void returnMessages(List<Message> messages);

        void fetchMessageError();
    }

    class MessageAsyncTask extends AsyncTask<String, Void, List<Message>> {

        @Override
        protected List<Message> doInBackground(String... strings) {
            try {
                return mApiService.getMessages(strings[0]).execute().body();
            } catch (IOException i) {
                i.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Message> messages) {
            super.onPostExecute(messages);
            if (messages == null) {
                mCallback.fetchMessageError();
            }
            mCallback.returnMessages(messages);
        }
    }
}
