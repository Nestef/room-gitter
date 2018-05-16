package com.nestef.room.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.nestef.room.R;
import com.nestef.room.messaging.MessagingActivity;
import com.nestef.room.messaging.MessagingFragment;
import com.nestef.room.model.Group;
import com.nestef.room.model.Room;

import org.parceler.Parcels;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class MainActivity extends AppCompatActivity implements GroupsFragment.OnCommunitySelection, RoomFragment.RoomSelectionCallback {

    private static final String TAG = "MainActivity";
    private static final String ROOM_EXTRA = "room_extra";

    int mFragmentId = R.id.fragment_switcher;

    int mMessageFragmentId = R.id.message_holder;

    @BindView(R.id.navigation_bar)
    BottomNavigation mBottomNavigation;
    @BindInt(R.integer.is_tablet)
    int mIsTablet;
    @android.support.annotation.Nullable
    @BindView(R.id.default_toolbar)
    Toolbar mDefaultToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Fragment fragment = RoomFragment.newInstance();
        FragmentTransaction f = getSupportFragmentManager().beginTransaction();
        f.add(mFragmentId, fragment).commit();
        if (isTablet()) {
            setSupportActionBar(mDefaultToolbar);
        }
        mBottomNavigation.setDefaultSelectedIndex(0);
        Log.d(TAG, "onCreate: is tablet" + isTablet());
        mBottomNavigation.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(int i, int i1, boolean b) {
                switch (i1) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(mFragmentId, RoomFragment.newInstance()).commit();
                        Log.d(TAG, "onMenuItemSelect:0: room");
                        return;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(mFragmentId, SearchFragment.newInstance()).commit();

                        return;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(mFragmentId, PeopleFragment.newInstance()).commit();
                        Log.d(TAG, "onMenuItemSelect: 2:People");
                        return;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(mFragmentId, GroupsFragment.newInstance()).commit();
                        Log.d(TAG, "onMenuItemSelect: 3: Community");
                        return;
                }
            }

            @Override
            public void onMenuItemReselect(int i, int i1, boolean b) {

            }
        });

    }

    private boolean isTablet() {
        return mIsTablet == 1;
    }

    private void tabletSetup() {
        // Toolbar mToolbar = findViewById(R.id.tablet_toolbar);
        // setSupportActionBar(mToolbar);

    }

    @Override
    public void onCommunitySelect(Group group) {
        getSupportFragmentManager().beginTransaction().replace(mFragmentId, CommunityFragment.newInstance(group)).addToBackStack("communityfragment").commit();
    }

    @Override
    public void onRoomSelected(Room room) {
        if (isTablet()) {
            getSupportFragmentManager().beginTransaction().replace(mMessageFragmentId, MessagingFragment.newInstance(room)).commit();
        } else {
            Intent intent = new Intent();
            intent.setClass(this, MessagingActivity.class);
            intent.putExtra(ROOM_EXTRA, Parcels.wrap(room));
            startActivity(intent);
        }
    }
}
