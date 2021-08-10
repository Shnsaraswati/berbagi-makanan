package com.shnsaraswati.berbagimmakanan.config;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreference {
    private static final String SHARED_PREF_NAME = "shared_preferences";

    SharedPreferences sharedPreferences;

    public void setSharedPreference(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

    }
}
