package com.nestef.room.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.nestef.room.R;
import com.nestef.room.messaging.MessagingActivity;
import com.nestef.room.messaging.MessagingFragment;
import com.nestef.room.model.Group;
import com.nestef.room.model.Room;
import com.nestef.room.preferences.PreferencesActivity;
import com.nestef.room.preferences.SettingsFragment;
import com.nestef.room.preferences.ThemeChanger;
import com.nestef.room.services.NewMessagesJobService;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

import static com.nestef.room.util.Constants.NOTIFICATION_CHANNEL_ID;
import static com.nestef.room.util.Constants.NOTIFICATION_CHANNEL_TITLE;
import static com.nestef.room.util.Constants.WIDGET_CLICK_ACTION;
import static com.nestef.room.util.Constants.WIDGET_ROOM_ITEM;

public class MainActivity extends AppCompatActivity implements GroupsFragment.OnCommunitySelection, RoomFragment.RoomSelectionCallback {

    private static final String TAG = "MainActivity";
    private static final String ROOM_EXTRA = "room_extra";
    private static final String SAVE_FRAGMENT_TAG = "fragment_tag";
    private static final String NAV_INDEX = "nav";
    private static final String ROOM_FRAGMENT_TAG = "room_fragment";
    private static final String SEARCH_FRAGMENT_TAG = "search_fragment";
    private static final String PEOPLE_FRAGMENT_TAG = "people_fragment";
    private static final String GROUPS_FRAGMENT_TAG = "groups_fragment";
    private static final String COMMUNITY_FRAGMENT_TAG = "community_fragment";

    int mFragmentId = R.id.fragment_switcher;

    int mMessageFragmentId = R.id.message_holder;

    private RoomFragment mRoomFragment;
    private SearchFragment mSearchFragment;
    private PeopleFragment mPeopleFragment;
    private GroupsFragment mGroupsFragment;
    private String mCurrentFragmentTag = ROOM_FRAGMENT_TAG;
    private int mNavigationIndex = 0;


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

        FragmentManager fragmentManager = getSupportFragmentManager();
        mRoomFragment = (RoomFragment) fragmentManager.findFragmentByTag(ROOM_FRAGMENT_TAG);
        mSearchFragment = (SearchFragment) fragmentManager.findFragmentByTag(SEARCH_FRAGMENT_TAG);
        mPeopleFragment = (PeopleFragment) fragmentManager.findFragmentByTag(PEOPLE_FRAGMENT_TAG);
        mGroupsFragment = (GroupsFragment) fragmentManager.findFragmentByTag(GROUPS_FRAGMENT_TAG);
        mBottomNavigation.setDefaultSelectedIndex(0);
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().replace(mFragmentId, RoomFragment.newInstance(), ROOM_FRAGMENT_TAG).commit();
        }
        if (savedInstanceState != null) {
            mCurrentFragmentTag = savedInstanceState.getString(SAVE_FRAGMENT_TAG);
            mNavigationIndex = savedInstanceState.getInt(NAV_INDEX);
        }
        setupNotifications();
        Intent activityIntent = getIntent();
        if (activityIntent.getAction() != null && activityIntent.getAction().equals(WIDGET_CLICK_ACTION)) {
            Room widgetItem = Parcels.unwrap(activityIntent.getParcelableExtra(WIDGET_ROOM_ITEM));
            if (isTablet()) {
                setSupportActionBar(mDefaultToolbar);
                getSupportFragmentManager().beginTransaction().replace(mMessageFragmentId, MessagingFragment.newInstance(widgetItem)).commit();
            } else {
                startMessagingActivity(widgetItem);
            }
        } else {
            if (isTablet()) {
                setSupportActionBar(mDefaultToolbar);
                getSupportFragmentManager().beginTransaction().replace(mMessageFragmentId, new MessagingFragment()).commit();
            }
        }

        mBottomNavigation.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(int i, int i1, boolean b) {
                switch (i1) {
                    case 0:
                        setFragment(ROOM_FRAGMENT_TAG);
                        mCurrentFragmentTag = ROOM_FRAGMENT_TAG;
                        mNavigationIndex = 0;
                        return;
                    case 1:
                        setFragment(SEARCH_FRAGMENT_TAG);
                        mCurrentFragmentTag = SEARCH_FRAGMENT_TAG;
                        mNavigationIndex = 1;
                        return;
                    case 2:
                        setFragment(PEOPLE_FRAGMENT_TAG);
                        mCurrentFragmentTag = PEOPLE_FRAGMENT_TAG;
                        mNavigationIndex = 2;
                        return;
                    case 3:
                        setFragment(GROUPS_FRAGMENT_TAG);
                        mCurrentFragmentTag = GROUPS_FRAGMENT_TAG;
                        mNavigationIndex = 3;
                }
            }

            @Override
            public void onMenuItemReselect(int i, int i1, boolean b) {
            }
        });
    }

    private void setupNotifications() {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String description = "Notifications for new messages";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_TITLE, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        Job notificationJob = dispatcher.newJobBuilder()
                .setService(NewMessagesJobService.class)
                .setRecurring(true)
                .setTag("new-messages-service")
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(0, 60))
                .build();
        Log.d(TAG, "setupNotifications: ");
        dispatcher.schedule(notificationJob);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_INDEX, mNavigationIndex);
        outState.putString(SAVE_FRAGMENT_TAG, mCurrentFragmentTag);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkTheme();
    }

    private void checkTheme() {
        if (SettingsFragment.changed) {
            SettingsFragment.changed = false;
            recreate();
        }
    }

    private void setFragment(String tag) {
        Log.d(TAG, "setFragment: ");
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = null;
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null && !fragments.isEmpty()) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible()) {
                    currentFragment = fragment;
                }
            }
        }
        switch (tag) {
            case ROOM_FRAGMENT_TAG:
                if (mRoomFragment == null) {
                    addFragment(RoomFragment.newInstance(), currentFragment, ROOM_FRAGMENT_TAG);
                } else {
                    showFragment(mRoomFragment, currentFragment);
                }
                break;
            case SEARCH_FRAGMENT_TAG:
                if (mSearchFragment == null) {
                    addFragment(SearchFragment.newInstance(), currentFragment, SEARCH_FRAGMENT_TAG);
                } else {
                    showFragment(mSearchFragment, currentFragment);
                }
                break;
            case PEOPLE_FRAGMENT_TAG:
                if (mPeopleFragment == null) {
                    addFragment(PeopleFragment.newInstance(), currentFragment, PEOPLE_FRAGMENT_TAG);
                } else {
                    showFragment(mPeopleFragment, currentFragment);
                }
                break;
            case GROUPS_FRAGMENT_TAG:
                if (mGroupsFragment == null) {
                    addFragment(GroupsFragment.newInstance(), currentFragment, GROUPS_FRAGMENT_TAG);
                } else {
                    showFragment(mGroupsFragment, currentFragment);
                }
                break;
            default:
                break;
        }
    }

    private void addFragment(Fragment fragment, Fragment currentFragment, String tag) {
        currentFragment.onHiddenChanged(true);
        getSupportFragmentManager().beginTransaction()
                .add(mFragmentId, fragment, tag)
                .hide(currentFragment)
                .commit();
        fragment.onHiddenChanged(false);
    }

    private void showFragment(Fragment fragment, Fragment currentFragment) {
        currentFragment.onHiddenChanged(true);
        getSupportFragmentManager().beginTransaction()
                .hide(currentFragment)
                .show(fragment)
                .commit();
        fragment.onHiddenChanged(false);
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
