package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("user");
        User user = new DataLoader(this).loadUser(userName);

        // Connect Stats
        TextView connectGamesPlayed = findViewById(R.id.connectGamesPlayed);
        connectGamesPlayed.setText(user.connectStats.getGamesPlayed() + "");
        TextView connectTimePlayed = findViewById(R.id.connectTimePlayed);
        connectTimePlayed.setText(user.connectStats.getTimePlayed() + "");
        TextView connectGamesWon = findViewById(R.id.connectGamesWon);
        connectGamesWon.setText(user.connectStats.getGamesWon() + "");

        // Repeat Stats
        TextView repeatGamesPlayed = findViewById(R.id.repeatGamesPlayed);
        repeatGamesPlayed.setText(user.matchStats.getGamesPlayed()+ "");
        TextView repeatTimePlayed = findViewById(R.id.repeatTimePlayed);
        repeatTimePlayed.setText(user.matchStats.getTimePlayed() + "");
        TextView repeatMistakes = findViewById(R.id.repeatMistakes);
        repeatMistakes.setText(user.matchStats.getTotalMistakes() + "");

        // Guess Stats
        TextView guessGamesPlayed = findViewById(R.id.guessGamesPlayed);
        guessGamesPlayed.setText(user.guessStats.getGamesPlayed()+ "");
        TextView guessTimePlayed = findViewById(R.id.guessTimePlayed);
        guessTimePlayed.setText(user.guessStats.getTimePlayed() + "");
        TextView guessLongestStreak = findViewById(R.id.guessLongestStreak);
        guessLongestStreak.setText(user.guessStats.getLongestStreak() + "");

    }
}
