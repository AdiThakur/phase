package com.example.game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


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

        Toast.makeText(this, "Welcome " + userName, Toast.LENGTH_SHORT).show();
    }

    void displayPrefferences(User user) {
        // TODO - Grab user's choice of BG color, text color, and language, draw GUI accordingly. This should be part of game superclass as well.
        View backgroundView = findViewById(R.id.backgroundView);
        backgroundView.setBackgroundColor(Color.parseColor(user.getBackgroundColor()));
    }

    public void gameClicked(View view) {

        Intent intent = null;
        String tag = view.getTag().toString();

        if (tag.equals("GUESS")) {
            intent = new Intent(getApplicationContext(), GuessActivity.class);
        } else if(tag.equals("CONNECT")) {
            intent = new Intent(getApplicationContext(), GuessActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), GuessActivity.class);
        }
        intent.putExtra("user", userName);
        startActivity(intent);
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
                //Toast.makeText(gameSelection.this, "Goodbye " + userName, Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(this, "Goodbye, " + userName, Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}

