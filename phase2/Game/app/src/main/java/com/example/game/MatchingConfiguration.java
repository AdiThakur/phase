package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.example.game.R;

public class MatchingConfiguration extends AppCompatActivity implements MatchingPresenter.View{

    public static final String GAME_DIFFICULTY = "com.example.memorygame.GAME_DIFFICULTY";
    public static final String GAME_CARDS = "com.example.memorygame.GAME_CARDS";
    public static final String GAME_SYMBOLS = "com.example.memorygame.GAME_SYMBOLS";
    private String userName;
    RadioGroup difficulty;
    RadioButton difficultyButton;
    TextView difficultyTextview;

    RadioGroup cardBack;
    RadioButton cardBackButton;
    TextView cardBackTextview;

    RadioGroup symbol;
    RadioButton symbolButton;
    TextView symbolTextview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        Intent intent = getIntent();
        userName = intent.getStringExtra("user");

        difficulty = findViewById(R.id.difficulty);
        symbol = findViewById(R.id.symbol);
        cardBack = findViewById(R.id.cardBack);
        difficultyButton = getRadioButton(difficulty);
        cardBackButton = getRadioButton(cardBack);
        symbolButton = getRadioButton(symbol);

        Button buttonPlay = findViewById(R.id.button_play);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });
    }

    private RadioButton getRadioButton(RadioGroup group) {
        int radioId = group.getCheckedRadioButtonId();

        RadioButton Button = findViewById(radioId);

        return Button;
    }

    public void checkDifficulty(View v) {

        difficultyButton = getRadioButton(difficulty);

        Toast.makeText(this, "Difficulty: " + difficultyButton.getText(), Toast.LENGTH_SHORT).show();
    }


    public void checkCardBack(View v) {
        cardBackButton = getRadioButton(cardBack);

        Toast.makeText(this, "Card Back: " + cardBackButton.getText(), Toast.LENGTH_SHORT).show();
    }

    public void checkSymbol(View v) {
        symbolButton = getRadioButton(symbol);

        Toast.makeText(this, "Symbol: " + symbolButton.getText(), Toast.LENGTH_SHORT).show();
    }

    public void play() {
        String gameDifficulty = difficultyButton.getText().toString();
        String gameCards = cardBackButton.getText().toString();
        String gameSymbols = symbolButton.getText().toString();
        Intent intent = new Intent(this, MatchingGameView.class);
        intent.putExtra(GAME_DIFFICULTY, gameDifficulty);
        intent.putExtra(GAME_CARDS, gameCards);
        intent.putExtra(GAME_SYMBOLS, gameSymbols);
        intent.putExtra("user", userName);
        startActivity(intent);
    }

}
