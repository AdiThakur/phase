package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

enum Classes {
    gameSelection,
    signUp
}

public class logIn extends AppCompatActivity {

    private final String TAG = "logIn";
    SharedPreferences sharedPreferences;

    private EditText userNameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        sharedPreferences = getSharedPreferences("com.example.game", Context.MODE_PRIVATE);

        // Initializing views.
        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
    }

    public void logInButton(View view) {

        String enteredUserName = userNameEditText.getText().toString();
        String enteredPassword = passwordEditText.getText().toString();

        // Using a DataLoader to try to load User enteredUserName.
        DataLoader dataLoader = new DataLoader(this);
        User user = dataLoader.loadUser(enteredUserName);

        if (user == null) {
            raiseNoRegisteredUserAlert(enteredUserName);
        } else {
            if (!enteredUserName.equals("") && !enteredPassword.equals("")) {
                if (user.authenticateUser(enteredUserName, enteredPassword)) {
                    jumpToActivity(user.getName(), Classes.gameSelection);
                } else {
                    raiseToast("Incorrect credentials!");
                }
            // Empty fields.
            } else {
                raiseToast("Fields cannot be empty!");
            }
        }
    }

    private void jumpToActivity(String userName, Classes activityToShow) {

        Intent intent = null;

        switch(activityToShow) {
            case gameSelection:
                intent = new Intent(getApplicationContext(), gameSelection.class);
                intent.putExtra("user", userName);
                break;
            case signUp:
                intent = new Intent(getApplicationContext(), signUp.class);
                intent.putExtra("user", userName);
                break;
        }
        startActivity(intent);
        finish();
    }

    private void raiseNoRegisteredUserAlert(final String userName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Invalid User");
        builder.setMessage("No such user exists. Would you like to sign-up?");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Sure!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                jumpToActivity(userName, Classes.signUp);
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void raiseToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
