package com.example.game;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class ConnectConfiguration extends AppCompatActivity {

    private int[] modelId = {R.drawable.x, R.drawable.o, R.drawable.yellow};
    private String userName;

    private int selectedGridSize = 3;
    private int selectedAiLevel = 1;

    private int selectedPlayerModelIndex;
    private int selectedAiModelIndex;

    private SeekBar gridSizeSeek;
    private SeekBar aiLevelSeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_configuration);
        this.getSupportActionBar().hide();

        userName = getIntent().getStringExtra("user");

        gridSizeSeek = findViewById(R.id.gridSizeSeekBar);
        gridSizeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int seek, boolean b) {
                selectedGridSize = seek;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateConfigurationTextViews();
            }
        });

        aiLevelSeek = findViewById(R.id.aiLevelSeekBar);
        aiLevelSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int seek, boolean b) {
                selectedAiLevel = seek;
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

        Intent intent = new Intent(getApplicationContext(), ConnectActivity.class);
        intent.putExtra("playerModel", modelId[selectedPlayerModelIndex]);
        intent.putExtra("aiModel", modelId[selectedAiModelIndex]);
        intent.putExtra("gridSize", selectedGridSize);
        intent.putExtra("aiLevel", selectedAiLevel);
        intent.putExtra("user", userName);
        startActivity(intent);
        finish();

    }

    public void modelSelected(View view) {

        LinearLayout playerModelLinearLayout = findViewById(R.id.playerModelLinearLayout);
        LinearLayout aiModelLinearLayout = findViewById(R.id.aiModelLinearLayout);
        int modelIndex = Integer.valueOf(view.getTag().toString());

        View parentView = (View) view.getParent();
        View imageToDisable;

        if (parentView.getId() == playerModelLinearLayout.getId()) {
            selectedPlayerModelIndex = modelIndex;
            imageToDisable = aiModelLinearLayout.getChildAt(modelIndex);
            resetLinearLayout(playerModelLinearLayout.getId(), 0.3f, false);
        } else {
            selectedAiModelIndex = modelIndex;
            imageToDisable = playerModelLinearLayout.getChildAt(modelIndex);
            resetLinearLayout(aiModelLinearLayout.getId(), 0.3f, false);
        }

        view.setAlpha(1f);
        view.setEnabled(false);

        imageToDisable.setAlpha(0.3f);
        imageToDisable.setEnabled(false);
    }

    public void resetButton(View view) {

        resetLinearLayout(R.id.playerModelLinearLayout, 1f, true);
        resetLinearLayout(R.id.aiModelLinearLayout, 1f, true);
        gridSizeSeek.setProgress(3);
        aiLevelSeek.setProgress(1);
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

        TextView gridSize = findViewById(R.id.gridSizeTextView);
        String gridText = "Grid Size - " + gridSizeSeek.getProgress();
        gridSize.setText(gridText);

        TextView aiLevel = findViewById(R.id.aiLevelTextView);
        String aiText = "A.I Level - " + aiLevelSeek.getProgress();
        aiLevel.setText(aiText);

    }
}
