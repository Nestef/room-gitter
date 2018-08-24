package com.nestef.room.messaging;

import android.os.Bundle;

import com.nestef.room.R;
import com.nestef.room.model.Room;
import com.nestef.room.preferences.ThemeChanger;

import org.parceler.Parcels;

import androidx.appcompat.app.AppCompatActivity;

public class MessagingActivity extends AppCompatActivity {

    private static final String ROOM_EXTRA = "room_extra";

    private final String MESSAGE_FRAGMENT_TAG = "message_fragment";

    int mContainerId = R.id.messaging_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeChanger.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        Room room = Parcels.unwrap(getIntent().getParcelableExtra(ROOM_EXTRA));
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(mContainerId, MessagingFragment.newInstance(room), MESSAGE_FRAGMENT_TAG).commit();
        }
    }

}
