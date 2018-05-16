package com.nestef.room.messaging;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nestef.room.R;
import com.nestef.room.model.Room;

import org.parceler.Parcels;

public class MessagingActivity extends AppCompatActivity {

    private static final String ROOM_EXTRA = "room_extra";
    int mContainerId = R.id.messaging_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        Room room = Parcels.unwrap(getIntent().getParcelableExtra(ROOM_EXTRA));
        getSupportFragmentManager().beginTransaction().add(mContainerId, MessagingFragment.newInstance(room)).commit();
    }

}
