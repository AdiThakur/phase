package com.example.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayScoreboard extends AppCompatActivity {

    Scoreboard scoreboard;
    ArrayList<String> scoresList;
    ArrayAdapter<String> arrayAdapter;
    ListView scoresListView;
    String winningMsg;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_scoreboard);

        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        String lastGamePlayed = intent.getStringExtra("gameName");
        winningMsg = intent.getStringExtra("msg");

        // Loading the score board for the appropriate game.
        scoreboard = new DataLoader(this).loadScoreBoard(lastGamePlayed);

        // Initial update to have the previously saved scores in the background.
        scoresList = scoreboard.formatToArrayList();
        TextView messageTextView = findViewById(R.id.messageTextView);
        messageTextView.setText(winningMsg);

        // Setting up the ListView that displays all scores.
        scoresListView = findViewById(R.id.scoresListView);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scoresList);
        scoresListView.setAdapter(arrayAdapter);

        // Displaying the dialogue box to get user's preferred display name.
        buildScoreInputDialog(winningMsg, lastGamePlayed);

    }

    // Inflates sorting menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sort_score_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Sorts scores by selected option (score, name).
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.sortByScoreButton) {
            Scoreboard.sortFormattedScoreboard("score", scoresList);
        } else if (item.getItemId() == R.id.sortByNameButton) {
            Scoreboard.sortFormattedScoreboard("name", scoresList);
        }
        arrayAdapter.notifyDataSetChanged();
        return true;
    }

    private void buildScoreInputDialog(String winningMsg, String lastGamePlayed) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(winningMsg.trim());
        builder.setMessage("You scored " + score + " in " + lastGamePlayed +
                "! Do you wish to save your score?");

        final EditText editText = new EditText(this);
        editText.setHint("Enter display name...");
        editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        builder.setView(editText);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addToScoreboard(editText.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void addToScoreboard(String displayName) {

        scoreboard.addScore(displayName, score);
        scoresList.add(displayName + " : " + score);
        arrayAdapter.notifyDataSetChanged();
    }

    public void buttonClicked(View view) {

        int resultCode = 0;

        if (view.getId() == R.id.restartButton) {
            resultCode = 1;
        } else if (view.getId() == R.id.quitGameButton) {
            resultCode = 2;
        }

        if (resultCode != 0) {
            setResult(resultCode);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please click either button!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {

        new DataSaver(this).saveScoreboard(scoreboard);
        super.onDestroy();

    }
}
