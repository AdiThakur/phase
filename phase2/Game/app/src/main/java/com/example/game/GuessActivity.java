package com.example.game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

public class GuessActivity extends AppCompatActivity {

    private Guess guessGame;

    private final String CORRECT_GUESS = "CORRECT!";
    private final String CORRECT_COLOR = "#009688";
    private final String WRONG_GUESS = "WRONG!";
    private final String WRONG_COLOR = "#B22222";

    private TextView guessCorrectTextView;
    private TextView streaksTextView;
    private TextView pivotNumberTextView;
    private Chronometer chronometer;

    /**
     * Sets up the entire game when user clicks the game button.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        guessCorrectTextView = findViewById(R.id.guessCorrectTextView);
        streaksTextView = findViewById(R.id.streaksTextView);
        pivotNumberTextView = findViewById(R.id.pivotNumberTextView);
        chronometer = findViewById(R.id.chronometer);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("user");
        guessGame = new Guess(userName, this);

        updateGUI();
        startClock();
    }

    /**
     * Checks if the user made the correct guess then outputs if it's right or wrong.
     *
     * @param view the button view of higher or lower
     */
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

    /**
     * Updates the GUI with streaks and the pivot number.
     */
    private void updateGUI() {
        streaksTextView.setText(Integer.toString(guessGame.getStreaks()));
        pivotNumberTextView.setText(Integer.toString(guessGame.getPivotNumber()));
    }

    /**
     * Displays whether the user got the correct guess or not.
     *
     * @param userGuess the string input of correct or wrong
     * @param color the color of the text
     */
    private void displayGuess(String userGuess, String color) {
        updateGUI();
        guessCorrectTextView.setText(userGuess);
        guessCorrectTextView.setTextColor(Color.parseColor(color));
        fade();
    }

    /**
     * Handles the fading feature for texts.
     */
    private void fade() {
        guessCorrectTextView.animate().alpha(1f).setDuration(100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                guessCorrectTextView.animate().alpha(0f).setDuration(100);
            }
        }, 300);
    }

    /**
     * Starts the clock.
     */
    private void startClock() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    /**
     * Handles the function when back button is pressed.
     */
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

    /**
     * Saves the game data and then destroys the content.
     */
    @Override
    protected void onDestroy() {
        guessGame.saveUserData();
        super.onDestroy();
    }
}
