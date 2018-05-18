package com.nestef.room.preferences;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.nestef.room.R;

/**
 * Created by Noah Steffes on 5/17/18.
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
