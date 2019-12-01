package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.Intent;

public class MatchingConfiguration extends AppCompatActivity implements MatchingPresenter.View{

    public static final String GAME_DIFFICULTY = "com.example.memorygame.GAME_DIFFICULTY";
    public static final String GAME_CARDS = "com.example.memorygame.GAME_CARDS";
    public static final String GAME_SYMBOLS = "com.example.memorygame.GAME_SYMBOLS";

    private String[] cardFaceArray = {"Animals", "Fruits", "Numbers"};
    private String[] cardBackArray = {"Red", "White", "Blue"};

    private SeekBar difficultySeekBar;

    private int selectedDifficulty = 1;
    private int selectedCardFace = 0;
    private int selectedCardBack = 0;

    private String userName;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_configuration);
        this.getSupportActionBar().hide();

        userName = getIntent().getStringExtra("user");
        user = new DataLoader(this).loadUser(userName);
        loadUserCustomizations();

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
        intent.putExtra(GAME_SYMBOLS, cardFaceArray[selectedCardFace]);
        intent.putExtra(GAME_CARDS, cardBackArray[selectedCardBack]);
        intent.putExtra("user", userName);
        saveUserCustomizations(selectedCardFace, selectedCardBack);
        startActivity(intent);
        finish();
    }

    private void loadUserCustomizations() {

        if (user.getLastGame().equalsIgnoreCase(Matching.gameName)) {
            selectedCardFace = user.getIndexOfCustomization1();
            selectedCardBack = user.getIndexOfCustomization2();

            Log.i("Guess", "selectedStreaksEmoji " + selectedCardFace );
            Log.i("Guess", "selectedEquationColor " + selectedCardBack );

            displayUserCustomizations(R.id.cardFaceLinearLayout, selectedCardFace);
            displayUserCustomizations(R.id.cardBackLinearLayout, selectedCardBack);
        }
    }

    private void displayUserCustomizations(int layoutId, int selectedIndex) {

        LinearLayout layout = findViewById(layoutId);
        View lastUsedModel = layout.getChildAt(selectedIndex);
        resetLinearLayout(layout.getId(), 0.3f, false);
        lastUsedModel.setAlpha(1f);
    }

    private void saveUserCustomizations(int indexOfCardFace, int indexOfCardBack) {

        user.setIndexOfCustomization1(indexOfCardFace);
        user.setIndexOfCustomization2(indexOfCardBack);
        new DataSaver(this).saveUser(user, user.getName(), user.getPassword(), user.getLastGame());
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

    public void modelSelected(View selectedModel) {

        LinearLayout cardFaceLinearLayout = findViewById(R.id.cardFaceLinearLayout);
        LinearLayout cardBackLinearLayout = findViewById(R.id.cardBackLinearLayout);
        int indexOfSelectedModel = Integer.valueOf(selectedModel.getTag().toString());

        View parentView = (View) selectedModel.getParent();

        if (parentView.getId() == cardFaceLinearLayout.getId()) {
            selectedCardFace = indexOfSelectedModel;
            resetLinearLayout(cardFaceLinearLayout.getId(), 0.3f, false);
        } else {
            selectedCardBack = indexOfSelectedModel;
            resetLinearLayout(cardBackLinearLayout.getId(), 0.3f, false);
        }

        selectedModel.setAlpha(1f);
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
