package com.nestef.room.preferences;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nestef.room.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Noah Steffes on 5/17/18.
 */
public class PreferencesActivity extends AppCompatActivity {

    @BindView(R.id.preference_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeChanger.setTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
