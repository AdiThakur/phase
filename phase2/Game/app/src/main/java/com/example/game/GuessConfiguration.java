package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class GuessConfiguration extends AppCompatActivity {

    private int[] streaksEmojiId = {R.drawable.redfire, R.drawable.bluefire, R.drawable.greenfire};
    private String[] equationColorValue = {"da7c7c", "2dd5d5", "74ea27"};
    private String userName;

    private int selectedDifficulty = 1;

    private int selectedStreaksEmoji = 0;
    private int selectedEquationColor = 0;

    private SeekBar difficultySeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_configuration);
        this.getSupportActionBar().hide();

        userName = getIntent().getStringExtra("user");

        difficultySeekBar = findViewById(R.id.difficultySeekBar);
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

        Intent intent = new Intent(getApplicationContext(), GuessActivity.class);
        intent.putExtra("streaksEmoji", streaksEmojiId[selectedStreaksEmoji]);
        intent.putExtra("equationColor", equationColorValue[selectedEquationColor]);
        intent.putExtra("difficulty", selectedDifficulty);
        intent.putExtra("user", userName);
        startActivity(intent);
        finish();

    }

    public void modelSelected(View view) {

        LinearLayout streaksEmojiLinearLayout = findViewById(R.id.streaksEmojiLinearLayout);
        LinearLayout equationColorsLinearLayout = findViewById(R.id.equationColorsLinearLayout);
        int modelIndex = Integer.valueOf(view.getTag().toString());

        View parentView = (View) view.getParent();

        if (parentView.getId() == streaksEmojiLinearLayout.getId()) {
            selectedStreaksEmoji = modelIndex;
            resetLinearLayout(streaksEmojiLinearLayout.getId(), 0.3f, false);
        } else {
            selectedEquationColor = modelIndex;
            resetLinearLayout(equationColorsLinearLayout.getId(), 0.3f, false);
        }

        view.setAlpha(1f);
        view.setEnabled(false);
    }

    public void resetButton(View view) {

        resetLinearLayout(R.id.streaksEmojiLinearLayout, 1f, true);
        resetLinearLayout(R.id.equationColorsLinearLayout, 1f, true);
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
        String difficultyText = "Difficulty- " + difficultySeekBar.getProgress();
        difficulty.setText(difficultyText);

    }
}
