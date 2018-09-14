package com.nestef.room.main;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.firebase.jobdispatcher.Constraint;
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

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;
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

    List<String> tags = Arrays.asList(ROOM_FRAGMENT_TAG, SEARCH_FRAGMENT_TAG, PEOPLE_FRAGMENT_TAG, GROUPS_FRAGMENT_TAG);
    private static final String ROOM_FRAGMENT_TAG = "room_fragment";
    private static final String SEARCH_FRAGMENT_TAG = "search_fragment";
    private static final String PEOPLE_FRAGMENT_TAG = "people_fragment";
    private static final String GROUPS_FRAGMENT_TAG = "groups_fragment";
    private static final String COMMUNITY_FRAGMENT_TAG = "community_fragment";

    private int mFragmentId = R.id.fragment_switcher;

    private int mMessageFragmentId = R.id.message_holder;

    private RoomFragment mRoomFragment;
    private SearchFragment mSearchFragment;
    private PeopleFragment mPeopleFragment;
    private GroupsFragment mGroupsFragment;
    private CommunityFragment mCommunityFragment;
    private String mCurrentFragmentTag = ROOM_FRAGMENT_TAG;
    private int mNavigationIndex = 0;

    @BindView(R.id.navigation_bar)
    BottomNavigation mBottomNavigation;
    @BindInt(R.integer.is_tablet)
    int mIsTablet;
    @androidx.annotation.Nullable
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
        mCommunityFragment = (CommunityFragment) fragmentManager.findFragmentByTag(COMMUNITY_FRAGMENT_TAG);
        mBottomNavigation.setDefaultSelectedIndex(0);
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction().replace(mFragmentId, RoomFragment.newInstance(), ROOM_FRAGMENT_TAG).commit();
            mCurrentFragmentTag = ROOM_FRAGMENT_TAG;
            setupNotifications();
        }
        if (savedInstanceState != null) {
            mCurrentFragmentTag = savedInstanceState.getString(SAVE_FRAGMENT_TAG);
            mNavigationIndex = savedInstanceState.getInt(NAV_INDEX);
        }

        Intent activityIntent = getIntent();
        if (activityIntent.getAction() != null && activityIntent.getAction().equals(WIDGET_CLICK_ACTION)) {
            Room widgetItem = Parcels.unwrap(activityIntent.getParcelableExtra(WIDGET_ROOM_ITEM));
            if (isTablet()) {
                setSupportActionBar(mDefaultToolbar);
                mDefaultToolbar.inflateMenu(R.menu.main_actions);
                mDefaultToolbar.setOnMenuItemClickListener(this::handleMenuClick);
                getSupportFragmentManager().beginTransaction().replace(mMessageFragmentId, MessagingFragment.newInstance(widgetItem)).commit();
            } else {
                startMessagingActivity(widgetItem);
            }
        } else {
            if (isTablet()) {
                setSupportActionBar(mDefaultToolbar);
                mDefaultToolbar.inflateMenu(R.menu.main_actions);
                mDefaultToolbar.setOnMenuItemClickListener(this::handleMenuClick);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_INDEX, mNavigationIndex);
        outState.putString(SAVE_FRAGMENT_TAG, mCurrentFragmentTag);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentFragmentTag = savedInstanceState.getString(SAVE_FRAGMENT_TAG);
        mNavigationIndex = savedInstanceState.getInt(NAV_INDEX);
        mBottomNavigation.setSelectedIndex(mNavigationIndex, false);
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

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        }
        if (mCurrentFragmentTag.equals(COMMUNITY_FRAGMENT_TAG) && getSupportFragmentManager().findFragmentByTag(COMMUNITY_FRAGMENT_TAG) == null) {
            mCurrentFragmentTag = GROUPS_FRAGMENT_TAG;
        }
        switch (tag) {
            case ROOM_FRAGMENT_TAG:
                if (mRoomFragment == null) {
                    mRoomFragment = RoomFragment.newInstance();
                    addFragment(mRoomFragment, ROOM_FRAGMENT_TAG);
                } else {
                    showFragment(mRoomFragment, ROOM_FRAGMENT_TAG);
                }
                break;
            case SEARCH_FRAGMENT_TAG:
                if (mSearchFragment == null) {
                    mSearchFragment = SearchFragment.newInstance();
                    addFragment(mSearchFragment, SEARCH_FRAGMENT_TAG);
                } else {
                    showFragment(mSearchFragment, SEARCH_FRAGMENT_TAG);
                }
                break;
            case PEOPLE_FRAGMENT_TAG:
                if (mPeopleFragment == null) {
                    mPeopleFragment = PeopleFragment.newInstance();
                    addFragment(mPeopleFragment, PEOPLE_FRAGMENT_TAG);
                } else {
                    showFragment(mPeopleFragment, PEOPLE_FRAGMENT_TAG);
                }
                break;
            case GROUPS_FRAGMENT_TAG:
                if (mGroupsFragment == null) {
                    mGroupsFragment = GroupsFragment.newInstance();
                    addFragment(mGroupsFragment, GROUPS_FRAGMENT_TAG);
                } else {
                    showFragment(mGroupsFragment, GROUPS_FRAGMENT_TAG);
                }
                break;
            case COMMUNITY_FRAGMENT_TAG:
                if (mCommunityFragment == null) {
                    mGroupsFragment = GroupsFragment.newInstance();
                    addFragment(mGroupsFragment, GROUPS_FRAGMENT_TAG);
                } else {
                    showFragment(mCommunityFragment, COMMUNITY_FRAGMENT_TAG);
                }
            default:
                break;
        }
    }

    private void addFragment(Fragment fragment, String tag) {

        FragmentTransaction f = getSupportFragmentManager()
                .beginTransaction()
                .add(mFragmentId, fragment, tag);
        if (getSupportFragmentManager().findFragmentByTag(COMMUNITY_FRAGMENT_TAG) != null) {
            f.hide(getSupportFragmentManager().findFragmentByTag(GROUPS_FRAGMENT_TAG));
        }
        if (getSupportFragmentManager().findFragmentByTag(mCurrentFragmentTag) != null) {
            f.hide(getSupportFragmentManager().findFragmentByTag(mCurrentFragmentTag));
        }
        f.commit();
        fragment.onHiddenChanged(false);
    }

    private void showFragment(Fragment fragment, String tag) {

        FragmentTransaction f = getSupportFragmentManager()
                .beginTransaction()
                .show(fragment);
        if (getSupportFragmentManager().findFragmentByTag(mCurrentFragmentTag) != null) {
            f.hide(getSupportFragmentManager().findFragmentByTag(mCurrentFragmentTag));
        }
        if (mCurrentFragmentTag.equals(COMMUNITY_FRAGMENT_TAG)) {
            f.hide(getSupportFragmentManager().findFragmentByTag(GROUPS_FRAGMENT_TAG));
        }
        f.commit();
        fragment.onHiddenChanged(false);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return handleMenuClick(item);
    }

    private boolean handleMenuClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_action:
                Intent intent = new Intent();
                intent.setClass(this, PreferencesActivity.class);
                startActivity(intent
                );
                return true;
            default:
                return false;
        }
    }


    private boolean isTablet() {
        return mIsTablet == 1;
    }

    @Override
    public void onCommunitySelect(Group group) {
        mCurrentFragmentTag = COMMUNITY_FRAGMENT_TAG;
        mCommunityFragment = CommunityFragment.newInstance(group);
        getSupportFragmentManager()
                .beginTransaction()
                .add(mFragmentId, mCommunityFragment, COMMUNITY_FRAGMENT_TAG)
                .hide(getSupportFragmentManager().findFragmentByTag(GROUPS_FRAGMENT_TAG))
                .addToBackStack(COMMUNITY_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onRoomSelected(Room room) {
        if (isTablet()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(mMessageFragmentId, MessagingFragment.newInstance(room))
                    .commit();
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

    private void setupNotifications() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!preferences.getBoolean(getString(R.string.notification_pref_key), false)) {


            FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String description = getString(R.string.notification_channel_description);
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
                    .addConstraint(Constraint.ON_ANY_NETWORK)
                    .build();
            dispatcher.schedule(notificationJob);
        }
    }
}
