package com.bar.barapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {

    public static void saveStringToPrefs(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStringFromPrefs(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("MyPref", 0);
        return prefs.getString(key, null);
    }
}
