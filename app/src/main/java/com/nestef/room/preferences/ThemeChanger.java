package com.nestef.room.preferences;

import android.content.Context;
import android.preference.PreferenceManager;

import com.nestef.room.R;

/**
 * Created by Noah Steffes on 5/17/18.
 */
public class ThemeChanger {

    public static void setTheme(Context context) {
        if (PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(context.getResources().getString(R.string.theme_pref_key), false)) {
            context.setTheme(R.style.DarkTheme);
        }
    }
}
