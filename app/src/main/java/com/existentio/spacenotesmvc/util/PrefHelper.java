package com.existentio.spacenotesmvc.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Георгий on 23.09.2017.
 */

public class PrefHelper {
    public static final String PREF_THEME = "pref_theme";
    public static final String KEY_THEME = "theme";
    public static final String KEY_THEME_WASTELAND = "theme_wasteland";
    public static final String KEY_THEME_MINIMAL = "theme_minimal";

    public static final String PREF_VIEW = "pref_view";
    public static final String KEY_VIEW_LINES = "view_lines";
    public static final String KEY_VIEW_GRID = "view_grid";

    public static final String PREF_DATE = "pref_date";
    public static final String KEY_DATE = "date";
    public static String CONST_PREF;

    public PrefHelper() {
    }

    public static void putInSharedPrefs(String pref, Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(pref, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putString(key, value);
        editor.apply();
    }

    public void setPref(String pref) {
        CONST_PREF = pref;
    }

    public String getPref() {
        return CONST_PREF;
    }


}
