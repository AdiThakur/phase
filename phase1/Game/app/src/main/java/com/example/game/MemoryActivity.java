package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MemoryActivity extends AppCompatActivity {

    private TextView displayNumberTextView;
    private EditText userNumberEditText;

    private MemoryGame memoryGame;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        displayNumberTextView = findViewById(R.id.displayNumberTextView);
        userNumberEditText = findViewById(R.id.userEnteredNumberEditText);

        Intent intent = getIntent();
        userName = intent.getStringExtra("user");
        memoryGame = new MemoryGame(userName, this);

        displaySequence();
    }

    // User presses check button
    public void checkEnteredNumber(View view) {

        String enteredNumber = userNumberEditText.getText().toString();
        Log.i("Memory/Entered", enteredNumber);
        boolean guessCorrect = memoryGame.checkGuess(enteredNumber);
        // If not empty
        if (!enteredNumber.equals("")) {
            if (guessCorrect) {
                correctGuess();
                Log.i("Memory/Answer", "Correct");
            } else {
                wrongGuess();
            }
        } else {
            Toast.makeText(this, "Please enter a number first!", Toast.LENGTH_SHORT).show();
        }
    }

    private void displaySequence() {
        //final StringBuilder numberSequence = memoryGame.displaySequence();
        final String numberSequence = "123456789";
        Log.i("Memory", numberSequence);
        for (int i = 0; i < numberSequence.length(); i++) {
            try {
                //set time in mili
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 300);

            }catch (Exception e){
                e.printStackTrace();
            }
            displayNumberTextView.setText(numberSequence.charAt(i) + "");
        }
    }

    private void correctGuess() {
        displaySequence();
    }

    private void wrongGuess() {
        // Create alert asking user to try again.
    }




}
