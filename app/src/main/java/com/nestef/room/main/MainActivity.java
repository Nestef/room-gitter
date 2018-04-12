package com.nestef.room.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.nestef.room.R;

public class MainActivity extends AppCompatActivity {

    int fragmentId = R.id.fragment_switcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment = RoomFragment.newInstance();
        FragmentTransaction f = getSupportFragmentManager().beginTransaction();
        f.add(fragmentId, fragment).commit();

    }
}
