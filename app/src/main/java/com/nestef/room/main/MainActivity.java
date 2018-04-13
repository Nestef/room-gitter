package com.nestef.room.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nestef.room.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    int fragmentId = R.id.fragment_switcher;
    @BindView(R.id.navigation_bar)
    BottomNavigation mBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Fragment fragment = SearchFragment.newInstance();
        FragmentTransaction f = getSupportFragmentManager().beginTransaction();
        f.add(fragmentId, fragment).commit();
        mBottomNavigation.setDefaultSelectedIndex(1);
        mBottomNavigation.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(int i, int i1, boolean b) {
                switch (i1) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(fragmentId, RoomFragment.newInstance()).commit();
                        Log.d(TAG, "onMenuItemSelect:0: room");
                        return;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(fragmentId, SearchFragment.newInstance()).commit();
                        Log.d(TAG, "onMenuItemSelect:1:search");
                        return;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(fragmentId, PeopleFragment.newInstance()).commit();
                        Log.d(TAG, "onMenuItemSelect: 2:People");
                        return;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(fragmentId, CommunityFragment.newInstance()).commit();
                        Log.d(TAG, "onMenuItemSelect: 3: Community");
                        return;
                }
            }

            @Override
            public void onMenuItemReselect(int i, int i1, boolean b) {

            }
        });
        
    }

}
