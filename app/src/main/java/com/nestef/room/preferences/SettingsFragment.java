package com.nestef.room.preferences;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.nestef.room.R;

/**
 * Created by Noah Steffes on 5/17/18.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    public static boolean changed;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, final String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        findPreference(getString(R.string.theme_pref_key)).setOnPreferenceChangeListener((preference, newValue) -> {
            changed = true;
            getActivity().finish();
            startActivity(getActivity().getIntent());
            return true;
        });
    }
}
