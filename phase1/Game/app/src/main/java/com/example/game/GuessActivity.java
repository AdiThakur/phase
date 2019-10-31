package com.example.game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

public class GuessActivity extends AppCompatActivity {

    private String userName;
    private Guess guessGame;

    private final String CORRECT_GUESS = "CORRECT!";
    private final String CORRECT_COLOR = "#009688";
    private final String WRONG_GUESS = "WRONG!";
    private final String WRONG_COLOR = "#B22222";

    private TextView guessCorrectTextView;
    private TextView streaksTextView;
    private TextView pivotNumberTextView;
    private Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        guessCorrectTextView = findViewById(R.id.guessCorrectTextView);
        streaksTextView = findViewById(R.id.streaksTextView);
        pivotNumberTextView = findViewById(R.id.pivotNumberTextView);
        chronometer = findViewById(R.id.chronometer);

        Intent intent = getIntent();
        userName = intent.getStringExtra("user");
        guessGame = new Guess(userName, this);

        updateGUI();
        startClock();
    }

    public void guessNumber(View view) {

        String userGuess = view.getTag().toString();
        boolean guessCorrect = guessGame.checkCorrect(userGuess);

        if (guessCorrect) {
            displayGuess(CORRECT_GUESS, CORRECT_COLOR);
        } else {
            displayGuess(WRONG_GUESS, WRONG_COLOR);
            guessGame.setTimePlayed((SystemClock.elapsedRealtime() - chronometer.getBase())/1000);
            startClock();
        }
    }

    private void updateGUI() {
        streaksTextView.setText(Integer.toString(guessGame.getStreaks()));
        pivotNumberTextView.setText(Integer.toString(guessGame.getPivotNumber()));
    }

    private void displayGuess(String userGuess, String color) {
        updateGUI();
        guessCorrectTextView.setText(userGuess);
        guessCorrectTextView.setTextColor(Color.parseColor(color));
        fade();
    }

    private void fade() {
        guessCorrectTextView.animate().alpha(1f).setDuration(100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                guessCorrectTextView.animate().alpha(0f).setDuration(100);
            }
        }, 300);
    }

    private void startClock() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    @Override
    public void onBackPressed() {
        chronometer.stop();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                chronometer.setBase(chronometer.getBase());
                chronometer.start();
            }
        });
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                GuessActivity.super.onBackPressed();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        guessGame.setTimePlayed((SystemClock.elapsedRealtime() - chronometer.getBase())/1000);
        guessGame.saveData();
        super.onDestroy();
    }
}
