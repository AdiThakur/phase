package com.example.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class gameSelection extends AppCompatActivity {

    String userName;
    String lastGame;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);

        Intent intent = getIntent();
        userName = intent.getStringExtra("user");

        // Loading user's data.
        user = new DataLoader(this).loadUser(userName);
        lastGame = user.getLastGame();

        Toast.makeText(this, "Welcome " + userName, Toast.LENGTH_SHORT).show();
        if (!lastGame.equals("")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    continueLastGame();
                }
            }, 1000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.checkstatsmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.checkStatsButton) {
            Intent intent = new Intent(getApplicationContext(), StatsActivity.class);
            intent.putExtra("user", userName);
            startActivity(intent);
        }
        return true;
    }

    void displayPrefferences(User user) {
        // TODO - Grab user's choice of BG color, text color, and language, draw GUI accordingly. This should be part of game superclass as well.
        View backgroundView = findViewById(R.id.backgroundView);
        backgroundView.setBackgroundColor(Color.parseColor(user.getBackgroundColor()));
    }

    private void continueLastGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Continue last game?");
        builder.setMessage("Looks like you were last playing " + lastGame + ". Would you like to continue?");
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               launchGame(lastGame);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void gameClicked(View view) {

        String tag = view.getTag().toString();
        launchGame(tag);
    }

    private void launchGame(String gameName) {
        Intent intent = null;
        if (gameName.equalsIgnoreCase("GUESS")) {
            intent = new Intent(getApplicationContext(), GuessActivity.class);
        } else if (gameName.equalsIgnoreCase("CONNECT")) {
            intent = new Intent(getApplicationContext(), ConnectActivity.class);
        } else  if (gameName.equalsIgnoreCase("REPEAT")){
            intent = new Intent(getApplicationContext(), MemoryActivity.class);
        }
        if (intent != null) {
            intent.putExtra("user", userName);
            startActivity(intent);
        }
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
