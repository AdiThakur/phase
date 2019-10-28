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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class signUp extends AppCompatActivity {

    final String TAG = "signUp";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sharedPreferences = getSharedPreferences("com.example.game", Context.MODE_PRIVATE);
        HashSet<String> users = (HashSet<String>) sharedPreferences.getStringSet("users", null);
        if (users != null) {
            Log.i(TAG, users.toString());
        }

    }

    public String defaultUserData(String username, String password) {

        StringBuilder output = new StringBuilder();
        output.append(username);
        output.append("\n");
        output.append(password);
        output.append("\n");
        output.append("green"); // BG color.
        output.append("\n");
        output.append("white"); // Text color
        output.append("\n");
        output.append("english"); // Language
        output.append("\n");
        output.append("0, 0, 0"); // Tic
        output.append("\n");
        output.append("0, 0, 0"); // Match
        output.append("\n");
        output.append("0, 0, 0"); // Higher or Lower
        output.append("\n");

        return output.toString();
    }

    public boolean saveUserToFile(String username, String password) {

        HashSet<String> savedUserList = (HashSet<String>) sharedPreferences.getStringSet("users", null);

        // Copy of savedUserList that will be modified.
        ArrayList<String> userList;
        if (savedUserList == null) {
            Log.i(TAG, "HashSet users is empty");
             userList = new ArrayList<>();
        } else {
            userList = new ArrayList<>(savedUserList);
        }

        // Username is taken.
        if (userList.contains(username)) {
            raiseToast("Username is taken!");
            return false;
        // Username is available.
        } else {
            // Saving username in persistent list of users.
            userList.add(username);
            sharedPreferences.edit().putStringSet("users", new HashSet<>(userList)).apply();

            // Creating text file for user.
            String fileName = username + ".txt";
            PrintWriter out;
            try {
                OutputStream outStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                out = new PrintWriter(outStream);
                // Saving username, password, and default data to username.txt.
                String output = defaultUserData(username, password);
                out.print(output);
                out.close();
                Log.i(TAG, "File saved in" + getFilesDir());
            } catch (FileNotFoundException e) {
                Log.e(TAG, "Error encountered trying to open file for writing: " + fileName);
            }
            return true;
        }

    }

    public void signUpButton(View view) {

        // Fetching ids' of different elements of corresponding layout.
        EditText userNameEditText = findViewById(R.id.userNameEditText);
        String enteredUserName = userNameEditText.getText().toString();
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        String enteredPassword = passwordEditText.getText().toString();

        // If username and password fields aren't empty.
        if (!enteredUserName.equals("") || !enteredPassword.equals("")) {
            boolean userCreated = saveUserToFile(enteredUserName, enteredPassword);
            // Only move onto game selection is user was created successfully.
            if (userCreated) {
                Intent intent = new Intent(getApplicationContext(), gameSelection.class);
                // This allows gameSelection activity to load data from enteredUserName.txt
                intent.putExtra("user", enteredUserName);
                startActivity(intent);
                finish();
            }
        } else {
            raiseToast("Fields cannot be empty!");
        }

    }

    private void raiseToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
