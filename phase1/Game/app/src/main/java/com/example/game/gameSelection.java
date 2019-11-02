package com.example.game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class gameSelection extends AppCompatActivity {

    String userName;
    User user;

    /**
     * Sets up game selection screen.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);

        Intent intent = getIntent();
        userName = intent.getStringExtra("user");

        // Loading user's data.
        DataLoader dataLoader = new DataLoader(this);
        user = dataLoader.loadUser(userName);

        Toast.makeText(this, "Welcome " + userName, Toast.LENGTH_SHORT).show();
    }

    /**
     * Creates the option menu.
     *
     * @param menu the option menu
     * @return if menu is open or not
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.checkstatsmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handles the function when options item is selected.
     *
     * @param item the menu item
     * @return whether options item is selected
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.checkStatsButton) {
            Intent intent = new Intent(getApplicationContext(), StatsActivity.class);
            intent.putExtra("user", userName);
            startActivity(intent);
        }
        return true;
    }

    /**
     * Display user's theme preferences.
     *
     * @param user the current user logged in
     */
    void displayPrefferences(User user) {
        // TODO - Grab user's choice of BG color, text color, and language, draw GUI accordingly. This should be part of game superclass as well.
        View backgroundView = findViewById(R.id.backgroundView);
        backgroundView.setBackgroundColor(Color.parseColor(user.getBackgroundColor()));
    }

    /**
     * Changes the screen to the game when User clicks on a game button.
     *
     * @param view the view of what the User clicked
     */
    public void gameClicked(View view) {

        Intent intent = null;
        String tag = view.getTag().toString();

        if (tag.equals("GUESS")) {
            intent = new Intent(getApplicationContext(), GuessActivity.class);
        } else if (tag.equals("CONNECT")) {
            intent = new Intent(getApplicationContext(), ConnectActivity.class);
        } else  if (tag.equals("MATCH")){
            intent = new Intent(getApplicationContext(), MemoryActivity.class);
        }
        if (intent != null) {
            intent.putExtra("user", userName);
            startActivity(intent);
        }
    }

    /**
     * Handles the function when back button is pressed.
     */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Log Out?");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                gameSelection.super.onBackPressed();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Handles the function to destroy content when User decides to exit screen.
     */
    @Override
    protected void onDestroy() {
        Toast.makeText(this, "Goodbye, " + userName, Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}
