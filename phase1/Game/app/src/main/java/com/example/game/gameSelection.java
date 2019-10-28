package com.example.game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.HashSet;

public class gameSelection extends AppCompatActivity {

    private final String TAG = "gameSelection";
    String userName;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);

        Intent intent = getIntent();
        userName = intent.getStringExtra("user");

        // Loading user's data.
        DataLoader dataLoader = new DataLoader(this);
        user = dataLoader.loadUser(userName);

        if (user != null) {
            Log.i(TAG, user.connectStats.getGamesWon() + ", " + user.connectStats.getGamesPlayed() + ", " + user.connectStats.getTimePlayed());
        }

    }

    void displayPrefferences(User user) {
        // TODO - Grab user's choice of BG color, text color, and language, draw GUI accordingly.
        View backgroundView = findViewById(R.id.backgroundView);
        backgroundView.setBackgroundColor(Color.parseColor(user.getBackgroundColor()));

    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Log Out?");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                gameSelection.super.onBackPressed();
                Toast.makeText(gameSelection.this, "Goodbye " + userName, Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
