package com.nestef.room.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nestef.room.R;
import com.nestef.room.messaging.MessagingActivity;
import com.nestef.room.messaging.MessagingFragment;
import com.nestef.room.model.Group;
import com.nestef.room.model.Room;
import com.nestef.room.preferences.PreferencesActivity;
import com.nestef.room.preferences.ThemeChanger;

import org.parceler.Parcels;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

import static com.nestef.room.util.Constants.WIDGET_CLICK_ACTION;
import static com.nestef.room.util.Constants.WIDGET_ROOM_ITEM;

public class MainActivity extends AppCompatActivity implements GroupsFragment.OnCommunitySelection, RoomFragment.RoomSelectionCallback {

    private static final String TAG = "MainActivity";
    private static final String ROOM_EXTRA = "room_extra";

    int mFragmentId = R.id.fragment_switcher;

    int mMessageFragmentId = R.id.message_holder;

    final RoomFragment mRoomFragment = RoomFragment.newInstance();
    final SearchFragment mSearchFragment = SearchFragment.newInstance();
    final PeopleFragment mPeopleFragment = PeopleFragment.newInstance();
    final GroupsFragment mGroupsFragment = GroupsFragment.newInstance();


    @BindView(R.id.navigation_bar)
    BottomNavigation mBottomNavigation;
    @BindInt(R.integer.is_tablet)
    int mIsTablet;
    @android.support.annotation.Nullable
    @BindView(R.id.default_toolbar)
    Toolbar mDefaultToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ThemeChanger.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction().add(mFragmentId, mRoomFragment).commit();
        mBottomNavigation.setDefaultSelectedIndex(0);

        Intent activityIntent = getIntent();
        if (activityIntent.getAction() != null && activityIntent.getAction().equals(WIDGET_CLICK_ACTION)) {
            Room widgetItem = Parcels.unwrap(activityIntent.getParcelableExtra(WIDGET_ROOM_ITEM));
            if (isTablet()) {
                setSupportActionBar(mDefaultToolbar);
                getSupportFragmentManager().beginTransaction().add(mMessageFragmentId, MessagingFragment.newInstance(widgetItem)).commit();
            } else {
                startMessagingActivity(widgetItem);
            }
        } else {
            if (isTablet()) {
                setSupportActionBar(mDefaultToolbar);
                getSupportFragmentManager().beginTransaction().add(mMessageFragmentId, new MessagingFragment()).commit();
            }
        }

        mBottomNavigation.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(int i, int i1, boolean b) {
                switch (i1) {
                    case 0:
                        switchFragments(mRoomFragment);
                        return;
                    case 1:
                        switchFragments(mSearchFragment);
                        return;
                    case 2:
                        switchFragments(mPeopleFragment);
                        return;
                    case 3:
                        switchFragments(mGroupsFragment);
                        Log.d(TAG, "onMenuItemSelect: 3: Community");
                }
            }

            @Override
            public void onMenuItemReselect(int i, int i1, boolean b) {
            }
        });
    }

    private void switchFragments(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(mFragmentId, fragment)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isTablet()) {
            getMenuInflater().inflate(R.menu.main_actions, menu);
            return super.onCreateOptionsMenu(menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_action:
                Intent intent = new Intent();
                intent.setClass(this, PreferencesActivity.class);
                startActivity(intent
                );
            default:
                return super.onOptionsItemSelected(item);

        }


    }

    private boolean isTablet() {
        return mIsTablet == 1;
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
            startMessagingActivity(room);
        }
    }

    private void startMessagingActivity(Room room) {
        Intent intent = new Intent();
        intent.setClass(this, MessagingActivity.class);
        intent.putExtra(ROOM_EXTRA, Parcels.wrap(room));
        startActivity(intent);
    }
}
