package com.nestef.room.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.nestef.room.R;

import static com.nestef.room.util.Constants.BLACK_THEME;
import static com.nestef.room.util.Constants.THEME_NAME_PREF;
import static com.nestef.room.util.Constants.WHITE_THEME;

/**
 * Created by Noah Steffes on 5/17/18.
 */
public class ThemeChanger {

    public static void setTheme(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (sharedPreferences.getBoolean(context.getResources().getString(R.string.theme_pref_key), false)) {
            context.setTheme(R.style.DarkTheme);
            setThemeName(context, BLACK_THEME);
        } else {
            context.setTheme(R.style.AppTheme);
            setThemeName(context, WHITE_THEME);
        }
    }

    private static void setThemeName(Context context, String theme) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(THEME_NAME_PREF, theme);
        editor.apply();
    }

    public static int getThemeIdForDialog(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (sharedPreferences.getBoolean(context.getResources().getString(R.string.theme_pref_key), false)) {
            return R.style.DarkTheme_Dialog;
        } else {
            return R.style.AppTheme_Dialog;
        }
    }

}
