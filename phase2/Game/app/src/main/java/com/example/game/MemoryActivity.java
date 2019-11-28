package com.example.game;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MemoryActivity extends AppCompatActivity {

    private TextView displayNumberTextView;
    private EditText userNumberEditText;
    private Button checkButton;

    private MemoryGame memoryGame;
    private CountDownTimer countDownTimer;

    /**
     * Initializes activity.
     * @param savedInstanceState saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        displayNumberTextView = findViewById(R.id.displayNumberTextView);
        userNumberEditText = findViewById(R.id.userEnteredNumberEditText);
        checkButton = findViewById(R.id.checkButton);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("user");
        memoryGame = new MemoryGame(userName, this);

        // Puts a delay in the initial run
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                displaySequence();
            }
        }, 1500);
    }

    /**
     * Checks the entered number to see if it is correct.
     * @param view standard view parameter
     */
    public void checkEnteredNumber(View view) {

        String enteredNumber = userNumberEditText.getText().toString();
        userNumberEditText.setText("");
        boolean guessCorrect = memoryGame.checkGuess(enteredNumber);
        if (!enteredNumber.equals("")) {
            if (guessCorrect) {
                correctGuess();
            } else {
                wrongGuess();
            }
        } else {
            Toast.makeText(this, "Please enter a number first!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Displays the sequence of numbers that the user has to remember.
     */
    private void displaySequence() {

        checkButton.setEnabled(false);
        userNumberEditText.setEnabled(false);
        final String numSeq = memoryGame.displaySequence().toString();
        final int numSeqLen = numSeq.length();

        countDownTimer = new CountDownTimer((numSeqLen*2000) + 700, 1000) {
            int counter = 0;
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                if (counter < numSeqLen) {
                    displayNumberTextView.setText(numSeq.charAt(counter) + "");
                    counter++;
                }
            }
            @Override
            public void onFinish() {
                displayNumberTextView.setText("");
                checkButton.setEnabled(true);
                userNumberEditText.setEnabled(true);
            }
        }.start();
    }

    /**
     * Displays the new, harder sequence.
     */
    private void correctGuess() {
        displaySequence();
    }

    /**
     * Informs the user that they have failed and asks if they want to try again.
     */
    private void wrongGuess() {

        countDownTimer.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wrong Sequence!");
        builder.setMessage("Would you like to try again?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                displaySequence();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                memoryGame.saveUserData();
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Asks the user if they really want to exit the game upon pressing the back button.
     */
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                displaySequence();
            }
        });
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MemoryActivity.super.onBackPressed();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Saves the users data in the game upon destruction.
     */
    @Override
    protected void onDestroy() {
        memoryGame.saveUserData();
        super.onDestroy();
    }
}
