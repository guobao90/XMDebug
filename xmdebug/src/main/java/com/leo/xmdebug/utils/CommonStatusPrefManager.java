package com.leo.xmdebug.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class CommonStatusPrefManager {

    private static String PREFS = "openTime";

    private CommonStatusPrefManager() {
    }

    public static void saveOpenTime(Context context) {
        SharedPreferences spf = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Editor edit = spf.edit();
        edit.putString(PREFS, "open time  == " + System.currentTimeMillis());
        edit.apply();
    }

}
