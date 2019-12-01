package com.example.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpView extends AppCompatActivity {

    private EditText userNameEditText;
    private EditText passwordEditText;
    private SignUpPresenter signUpPresenter;

    /**
     * Initializes activity.
     * @param savedInstanceState saved instance state (null on first run)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        Intent intent = getIntent();
        String desiredUserName = intent.getStringExtra("user");
        userNameEditText.setText(desiredUserName);
        signUpPresenter = new SignUpPresenter(this, new SignUp());
    }

    /**
     * Tries to sign the user up: raises messages messages if that is not possible that inform
     * the user why this is the case (ex. Empty Fields, etc).
     * @param view standard view parameter
     */
    public void signUpButton(View view) {

        String enteredUserName = userNameEditText.getText().toString();
        String enteredPassword = passwordEditText.getText().toString();

        signUpPresenter.validateCredentials(enteredUserName, enteredPassword);

    }

    /**
     * Gives feedback to the user in a small pop up on their phone.
     * @param msg the given message to the user
     */
    void raiseToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
