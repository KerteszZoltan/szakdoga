package com.eveldar.eveldar.kertesz_zoltan;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static String SHARED_PREF_NAME="eveldar";
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        this.context = context;
    }

    void saveUser(User user){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putInt("id", user.getId());
        editor.putString("token", user.getToken());
        editor.putString("name", user.getName());
        editor.putString("email", user.getEmail());
        editor.putBoolean("logged", true);
        editor.apply();
    }

    boolean isLoggedIn(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,context.MODE_PRIVATE);
        return sharedPreferences.getBoolean( "logged", false);
    }
    public User getUser(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,context.MODE_PRIVATE);
        return new User(sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("token", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("name", null));
    }

    void logout(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
