package com.example.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class signUp extends AppCompatActivity {

    private EditText userNameEditText;
    private EditText passwordEditText;

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
    }

    /**
     * Tries to sign the user up: raises messages messages if that is not possible that inform
     * the user why this is the case (ex. Empty Fields, etc).
     * @param view standard view parameter
     */
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
                boolean userCreated = dataSaver.saveUser(null, enteredUserName, enteredPassword);
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

    /**
     * Gives feedback to the user in a small pop up on their phone.
     * @param msg the given message to the user
     */
    private void raiseToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
