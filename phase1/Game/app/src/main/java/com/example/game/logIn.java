package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class logIn extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        sharedPreferences = getSharedPreferences("com.example.game", Context.MODE_PRIVATE);
    }

    public void logIn(View view) {

        // Check if user in file
        // If they are in file, valid = true. Load all of their preferences, and move on to game
        // selection screen
        // else output Not a valid user, please try again.
        EditText userNameEditText = findViewById(R.id.userNameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);

        String enteredUserName = userNameEditText.getText().toString();
        String enteredPassword = passwordEditText.getText().toString();

        String userName = sharedPreferences.getString(enteredUserName, null);
        // If enteredUserName in user list.
        if (userName != null) {
            // Check if correct password.
            // Create a new User object using just the username. Call User.load() to load all of
            // this user's data. Check if entered password is the same as saved password.
            if (enteredPassword.equals("password")) {
                // Then we can jump to the main menu
             // If the user exists, but password is messed
            } else {
                // Raise toast: wrong password - Try again
                // reset the editTextField for password
            }
        // If username not in list
        } else {
            // jump to sign-up activity
            // create a new user, save it in sharedprefferences, and txt file
        }



    }
}
