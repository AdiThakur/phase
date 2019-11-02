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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class signUp extends AppCompatActivity {

    private EditText userNameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        Intent intent = getIntent();
        String desiredUserName = intent.getStringExtra("user");
        userNameEditText.setText(desiredUserName);
    }

    public void signUpButton(View view) {

        String enteredUserName = userNameEditText.getText().toString();
        String enteredPassword = passwordEditText.getText().toString();

        // If fields aren't empty and no such user is saved.
        if ((!enteredUserName.equals("") && !enteredPassword.equals(""))) {
            DataLoader dataLoader = new DataLoader(this);
            User user = dataLoader.loadUser(enteredUserName);
            // If such user isn't saved in files.
            if (user == null) {
                DataSaver dataSaver = new DataSaver(this);
                boolean userCreated = dataSaver.saveUser(null, enteredUserName, enteredPassword, null);
                if (userCreated) {
                    Intent intent = new Intent(getApplicationContext(), gameSelection.class);
                    // This allows gameSelection activity to load data from enteredUserName.txt
                    intent.putExtra("user", enteredUserName);
                    startActivity(intent);
                    finish();
                }
            } else {
                raiseToast("User name is already taken!");
            }
        } else {
            raiseToast("Fields cannot be empty!");
        }
    }

    private void raiseToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
