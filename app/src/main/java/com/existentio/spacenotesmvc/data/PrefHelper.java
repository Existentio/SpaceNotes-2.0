package com.existentio.spacenotesmvc.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Георгий on 23.09.2017.
 */

public class PrefHelper {
    public static final String PREF_THEME = "pref_theme";
    public static final String KEY_THEME = "theme";
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
