package com.example.game;

import androidx.annotation.Nullable;
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
import android.widget.ImageView;
import android.widget.TextView;

public class GuessView extends AppCompatActivity {

    private GuessPresenter guessPresenter;

    private final String CORRECT_GUESS = "CORRECT!";
    private final String CORRECT_COLOR = "#009688";
    private final String WRONG_GUESS = "WRONG!";
    private final String WRONG_COLOR = "#B22222";

    private TextView guessCorrectTextView;
    private TextView streaksTextView;
    private TextView equationTextView;
    private TextView pivotNumberTextView;
    private Chronometer chronometer;
    private boolean isBusy;

    /**
     * Sets up the entire game when user clicks the game button.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);


        // Initializing Views.
        equationTextView = findViewById(R.id.equationTextView);
        guessCorrectTextView = findViewById(R.id.guessCorrectTextView);
        streaksTextView = findViewById(R.id.streaksTextView);
        pivotNumberTextView = findViewById(R.id.pivotNumberTextView);
        chronometer = findViewById(R.id.chronometer);

        // Grabbing information passed by Configuration activity.
        Intent intent = getIntent();
        String userName = intent.getStringExtra("user");
        int streaksEmoji = intent.getIntExtra("streaksEmoji", R.drawable.redfire);
        String equationColor = intent.getStringExtra("equationColor");
        int difficulty = intent.getIntExtra("difficulty", 1);
        isBusy = false;

        guessPresenter = new GuessPresenter(userName, this, difficulty);

        applyPreferences(streaksEmoji, equationColor);
        updateGUI();
        startClock();
    }

    private void applyPreferences(int streaksEmoji, String equationColor) {

        ImageView streaks = findViewById(R.id.streaksImageView);
        streaks.setImageResource(streaksEmoji);

        equationTextView.setTextColor(Color.parseColor("#" + equationColor));
    }

    /**
     * Checks if the user made the correct guess then outputs if it's right or wrong.
     *
     * @param view the button view of higher or lower
     */
    public void userGuess(View view) {

        if(!isBusy) {
            String userGuess = view.getTag().toString();
            int score = guessPresenter.getStreaks();
            boolean guessCorrect = guessPresenter.checkCorrect(userGuess);

            if (guessCorrect) {
                displayGuess(CORRECT_GUESS, CORRECT_COLOR);
                // If guess is wrong.
            } else {
                displayGuess(WRONG_GUESS, WRONG_COLOR);
                displayAndSaveScore("Game Over!", score);
            }
        }
    }

    private void displayAndSaveScore(String winningMessage, int score) {

        isBusy = true;
        Intent intent = new Intent(this, DisplayScoreboard.class);

        intent.putExtra("score", score);
        intent.putExtra("gameName", guessPresenter.getName());
        intent.putExtra("msg", winningMessage);

        startActivityForResult(intent, 1);
    }

    /**
     * Updates the GUI with appropriate equation, streaks, and the pivot number.
     */
    private void updateGUI() {

        streaksTextView.setText(String.valueOf(guessPresenter.getStreaks()));
        pivotNumberTextView.setText(String.valueOf(guessPresenter.getPivotNumber()));
        equationTextView.setText(guessPresenter.getEquationToGuess());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Restart
        if (resultCode == 1) {
            startClock();
            isBusy = false;
            guessPresenter.setUpRound();
            updateGUI();
            // Quit game
        } else if (resultCode == 2) {
            finish();
        }
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
                GuessView.super.onBackPressed();
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
        guessPresenter.saveUserData();
        super.onDestroy();
    }
}
