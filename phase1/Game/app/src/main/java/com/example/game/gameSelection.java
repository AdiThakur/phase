package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.HashSet;

public class gameSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        HashSet<String> users = (HashSet<String>) sharedPreferences.getStringSet("users", null);
        if (users != null) {
            Log.i("gameSelection", users.toString());
        }
    }
}
