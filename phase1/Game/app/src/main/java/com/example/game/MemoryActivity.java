package com.example.game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MemoryActivity extends AppCompatActivity {

    private TextView displayNumberTextView;
    private EditText userNumberEditText;
    private Button checkButton;

    private MemoryGame memoryGame;
    private String userName;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        displayNumberTextView = findViewById(R.id.displayNumberTextView);
        userNumberEditText = findViewById(R.id.userEnteredNumberEditText);
        checkButton = findViewById(R.id.checkButton);

        Intent intent = getIntent();
        userName = intent.getStringExtra("user");
        memoryGame = new MemoryGame(userName, this);

        // Puts a delay in the initial run
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                displaySequence();
            }
        }, 1500);
    }

    public void checkEnteredNumber(View view) {

        String enteredNumber = userNumberEditText.getText().toString();
        userNumberEditText.setText("");
        Log.i("Memory/Entered", enteredNumber);
        boolean guessCorrect = memoryGame.checkGuess(enteredNumber);
        // If not empty
        if (!enteredNumber.equals("")) {
            if (guessCorrect) {
                correctGuess();
                Log.i("Memory/Answer", "Correct");
            } else {
                wrongGuess();
                Log.i("Memory/Answer", "Wrong");
            }
        } else {
            Toast.makeText(this, "Please enter a number first!", Toast.LENGTH_SHORT).show();
        }
    }

    private void displaySequence() {
        checkButton.setEnabled(false);
        userNumberEditText.setEnabled(false);
        final String numSeq = memoryGame.displaySequence().toString();
        Log.i("Memory/Actual", numSeq);
        final int numSeqLen = numSeq.length();

        countDownTimer = new CountDownTimer((numSeqLen*2000) + 700, 1000) {
            int counter = 0;
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

    private void correctGuess() {
        displaySequence();
    }

    private void wrongGuess() {
        countDownTimer.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Wrong Sequence! Try Again");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                displaySequence();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

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

    @Override
    protected void onDestroy() {
        memoryGame.saveData();
        super.onDestroy();
    }
}
