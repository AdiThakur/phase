package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class logIn extends AppCompatActivity {

    final String TAG = "logIn";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        sharedPreferences = getSharedPreferences("com.example.game", Context.MODE_PRIVATE);
        HashSet<String> users = (HashSet<String>) sharedPreferences.getStringSet("users", null);
        if (users != null) {
            Log.i(TAG, users.toString());
        }
    }

    public void logInButton(View view) {

        HashSet<String> savedUserList = (HashSet<String>)
                sharedPreferences.getStringSet("users", null);

        if (savedUserList == null) {
            Toast.makeText(this, "No local accounts. Please sign up.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), signUp.class));
            finish();
        } else {

            ArrayList<String> userList = new ArrayList<>(savedUserList);

            EditText userNameEditText = findViewById(R.id.userNameEditText);
            String enteredUserName = userNameEditText.getText().toString();
            EditText passwordEditText = findViewById(R.id.passwordEditText);
            String enteredPassword = userNameEditText.getText().toString();

            // If username and password fields aren't empty.
            if (!enteredUserName.equals("") || !enteredPassword.equals("")) {
                Log.i(TAG, enteredUserName);

                // If such user exists.
                if (userList.contains(enteredUserName)) {
                    // TODO: Check password.
                    Toast.makeText(this, "Welcome " + enteredUserName, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), gameSelection.class));
                    finish();
                } else {
                    Toast.makeText(this, "No such user found!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
