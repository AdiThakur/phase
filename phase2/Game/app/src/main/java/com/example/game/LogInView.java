package com.example.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

enum Classes {
    gameSelection,
    signUp,
}

public class LogInView extends AppCompatActivity {
    private final String TAG = "LogInView";

    private EditText userNameEditText;
    private EditText passwordEditText;
    private LogInPresenter logInPresenter;

    /**
     * Initializes activity.
     * @param savedInstanceState saved instance state (null on first run)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        logInPresenter = new LogInPresenter(this, new LogIn());

    }

    /**
     * Tries to log the user into the app: raises messages if this is not possible that inform
     * the user why this is the case (ex. Incorrect credentials, etc).
     * @param view standard view parameter
     */
    public void logInButton(View view) {

        String enteredUserName = userNameEditText.getText().toString();
        String enteredPassword = passwordEditText.getText().toString();

        logInPresenter.validateCredentials(enteredUserName, enteredPassword);
    }

    /**
     * Jumps to a new activity.
     * @param userName current user that is passed to the new activity
     * @param activityToShow activityToShow
     */
    void jumpToActivity(String userName, Classes activityToShow) {

        Intent intent = null;

        switch(activityToShow) {
            case gameSelection:
                intent = new Intent(getApplicationContext(), gameSelection.class);
                intent.putExtra("user", userName);
                break;
            case signUp:
                intent = new Intent(getApplicationContext(), SignUpView.class);
                intent.putExtra("user", userName);
                break;
        }
        startActivity(intent);
        finish();
    }

    /**
     * Gives the user an option to go straight to the sign-up.
     * @param userName current user that is passed to the SignUpView activity
     */
    void raiseNoRegisteredUserAlert(final String userName) {
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

    /**
     * Gives feedback to the user in a small pop up on their phone.
     * @param msg the given message to the user
     */
    void raiseToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
