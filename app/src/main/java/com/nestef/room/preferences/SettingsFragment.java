package com.nestef.room.preferences;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;

import com.nestef.room.R;
import com.nestef.room.auth.AuthActivity;
import com.nestef.room.data.PrefManager;
import com.nestef.room.util.Constants;

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
        findPreference(getString(R.string.logout_pref_key)).setOnPreferenceClickListener((preference -> {
            Log.d("Settings", "onPreferenceClick: " + preference.getKey());
            PrefManager p = PrefManager.getInstance(getContext().getSharedPreferences(Constants.AUTH_SHARED_PREF, Context.MODE_PRIVATE));
            p.deleteAuthToken();
            p.deleteUserId();
            Intent intent = new Intent();
            intent.setClass(getContext(), AuthActivity.class);
            getContext().startActivity(intent);
            getActivity().finish();
            return true;
        }));
    }

}
