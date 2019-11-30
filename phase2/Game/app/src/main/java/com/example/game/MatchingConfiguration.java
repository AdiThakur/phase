package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class MatchingConfiguration extends AppCompatActivity implements MatchingPresenter.View{

    public static final String GAME_DIFFICULTY = "com.example.memorygame.GAME_DIFFICULTY";
    public static final String GAME_CARDS = "com.example.memorygame.GAME_CARDS";
    public static final String GAME_SYMBOLS = "com.example.memorygame.GAME_SYMBOLS";

    private String userName;
    private SeekBar difficultySeekBar;

    private int selectedDifficulty;
    private String selectedCardFace = "Animals";
    private String selectedCardBack = "Red";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_configuration);
        this.getSupportActionBar().hide();

        userName = getIntent().getStringExtra("user");

        difficultySeekBar = findViewById(R.id.difficultySeekbar);
        difficultySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int seek, boolean b) {
                selectedDifficulty = seek;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateConfigurationTextViews();
            }
        });
    }

    public void playButton(View view) {

        Intent intent = new Intent(getApplicationContext(), MatchingGameView.class);

        intent.putExtra(GAME_DIFFICULTY, selectedDifficultyAsString(selectedDifficulty));
        intent.putExtra(GAME_SYMBOLS, selectedCardFace);
        intent.putExtra(GAME_CARDS, selectedCardBack);
        intent.putExtra("user", userName);

        startActivity(intent);
        finish();

    }

    private String selectedDifficultyAsString(int selectedDifficulty) {

        String difficultyAsString;
        Log.println(Log.DEBUG, "Selected Difficulty", Integer.toString(selectedDifficulty));
        if ( selectedDifficulty <= 1) {
            difficultyAsString = "Easy";
        } else if (selectedDifficulty == 2) {
            difficultyAsString = "Medium";
        } else {
            difficultyAsString = "Hard";
        }
        return difficultyAsString;
    }

    public void modelSelected(View imageClicked) {

        LinearLayout cardFaceLinearLayout = findViewById(R.id.cardFaceLinearLayout);
        LinearLayout cardBackLinearLayout = findViewById(R.id.cardBackLinearLayout);

        View parentView = (View) imageClicked.getParent();

        if (parentView.getId() == cardFaceLinearLayout.getId()) {
            selectedCardFace = imageClicked.getTag().toString();
            resetLinearLayout(cardFaceLinearLayout.getId(), 0.3f, false);
        } else {
            selectedCardBack = imageClicked.getTag().toString();
            resetLinearLayout(cardBackLinearLayout.getId(), 0.3f, false);
        }

        imageClicked.setAlpha(1f);
        imageClicked.setEnabled(false);
    }

    public void resetButton(View view) {

        resetLinearLayout(R.id.cardFaceLinearLayout, 1f, true);
        resetLinearLayout(R.id.cardBackLinearLayout, 1f, true);
        difficultySeekBar.setProgress(1);
        updateConfigurationTextViews();
    }

    private void resetLinearLayout(int layoutId, float alpha, boolean isEnabled) {

        LinearLayout layoutToReset = findViewById(layoutId);
        for (int i = 0; i < layoutToReset.getChildCount(); i++) {
            ImageView image = (ImageView) layoutToReset.getChildAt(i);
            image.setAlpha(alpha);
            image.setEnabled(isEnabled);
        }
    }

    private void updateConfigurationTextViews(){

        TextView difficulty = findViewById(R.id.difficultyTextView);
        String difficultyText = "Difficulty - " + difficultySeekBar.getProgress();
        difficulty.setText(difficultyText);
    }
}
