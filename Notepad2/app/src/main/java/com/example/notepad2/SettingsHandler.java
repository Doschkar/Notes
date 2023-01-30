package com.example.notepad2;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsHandler {

    public boolean[] readSettings(Context context){
        boolean[] settings = new boolean[3];

        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        settings[0] = sharedPreferences.getBoolean("dateSetting", false);
        settings[1] = sharedPreferences.getBoolean("expiredSetting", false);
        settings[2] = sharedPreferences.getBoolean("doneSetting", false);

        return settings;
    }

    public void writeSettings(Context context, boolean[] settings){
        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("dateSetting", settings[0]);
        editor.putBoolean("expiredSetting", settings[1]);
        editor.putBoolean("doneSettings", settings[2]);
        editor.apply();
    }
}