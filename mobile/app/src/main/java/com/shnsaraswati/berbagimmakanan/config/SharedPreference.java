package com.shnsaraswati.berbagimmakanan.config;

import android.content.Context;
import android.content.SharedPreferences;

import query.UseGetUserByPhoneQuery;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreference {
    private static final String SHARED_PREF_NAME = "shared_preferences";

    SharedPreferences sharedPreferences;

    public SharedPreference(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);;
    }

    public void setProfileSharedPreference(String id, String name, String phone_number){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id", id);
        editor.putString("name", name);
        editor.putString("phone_number", phone_number);
        editor.apply();
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_logged_in", isLoggedIn);
        editor.apply();
    }

    public boolean getIsLoggedIn() {
        return sharedPreferences.getBoolean("is_logged_in", false);
    }

    public String getProfileID() {
        return sharedPreferences.getString("user_id", "");
    }

    public String getProfileName() {
        return sharedPreferences.getString("name", "");
    }

    public String getProfilePhonenumber() {
        return sharedPreferences.getString("phone_number", "");
    }

    public void clearSharedPreference() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
