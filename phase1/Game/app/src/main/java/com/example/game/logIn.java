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

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class logIn extends AppCompatActivity {

    final String TAG = "logIn";
    SharedPreferences sharedPreferences;

    EditText userNameEditText;
    String enteredUserName;
    EditText passwordEditText;
    String enteredPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        sharedPreferences = getSharedPreferences("com.example.game", Context.MODE_PRIVATE);

        // Initializing views.
        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
    }

    public ArrayList<String> readData(String user) {

        // Saves data read from text file.
        String fileName = user + ".txt";
        ArrayList<String> dataFromFile = new ArrayList<>();
        // Reading from file
        try (Scanner scanner = new Scanner(openFileInput(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                dataFromFile.add(line);
            }
        } catch (IOException e) {
            Log.e(TAG, "Couldn't open following file(s) " + fileName);
        }
        return dataFromFile;
    }

    private void authenticateUser(String user, ArrayList<String> dataFromFile) {

        String savedPassword = dataFromFile.get(User.PASSWORD_INDEX);
        if (enteredPassword.equals(savedPassword.trim())) {
            Toast.makeText(this, "Welcome " + enteredUserName, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), gameSelection.class);
            intent.putStringArrayListExtra(user, dataFromFile);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Wrong password!", Toast.LENGTH_SHORT).show();
            Log.i(TAG + "Saved", savedPassword);
            Log.i(TAG + "Entered", enteredPassword);
            passwordEditText.setText("");
        }
    }

    public void logInButton(View view) {

        HashSet<String> savedUserList = (HashSet<String>)
                sharedPreferences.getStringSet("users", null);

        // If no users have been created, jump into signUp activity.
        if (savedUserList == null) {
            Toast.makeText(this, "No local accounts. Please sign up.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), signUp.class));
            finish();
        // If users have been declared, check credentials.
        } else {
            ArrayList<String> userList = new ArrayList<>(savedUserList);

            // Retrieving entered username and password.
            enteredUserName = userNameEditText.getText().toString();
            enteredPassword = passwordEditText.getText().toString();

            // If username and password fields aren't empty.
            if (!enteredUserName.equals("") || !enteredPassword.equals("")) {
                Log.i(TAG, enteredUserName);
                // If such user exists.
                if (userList.contains(enteredUserName)) {
                    ArrayList<String> dataFromFile = readData(enteredUserName);
                    authenticateUser(enteredUserName, dataFromFile);
                } else {
                    Toast.makeText(this, "No such user found!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
