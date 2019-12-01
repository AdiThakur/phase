package com.example.game;

import android.content.Intent;

class SignUpPresenter {
    private SignUpView signUpView;
    private SignUp signUp;

    /**
     * Constructs an instance of SignUpPresenter.
     *
     * @param signUpView - the view/activity that interacts with the user
     * @param signUp - an instance of SignUp
     */
    SignUpPresenter(SignUpView signUpView, SignUp signUp) {
        this.signUpView = signUpView;
        this.signUp = signUp;
    }

    /**
     * Tries to sign the user up into the app: raises messages if this is not possible that informs the
     * user why that is the case. For example the username was already taken.
     *
     * @param username - the username the user tried to sign up with
     * @param password - the password the user tried to sign up with
     */
    void validateCredentials(String username, String password) {
        if (username.equals("") || password.equals("")) {
            signUpView.raiseToast("Fields cannot be empty!");
        } else {
            User user = signUp.checkUsername(username, signUpView.getApplicationContext());
            if (user == null) {
                boolean userCreated = signUp.signUpUser(signUpView.getApplicationContext(), username, password);
                if (userCreated) {
                    Intent intent = new Intent(signUpView.getApplicationContext(), gameSelection.class);
                    // This allows gameSelection activity to load data from enteredUserName.txt
                    intent.putExtra("user", username);
                    signUpView.startActivity(intent);
                    signUpView.finish();
                }
            } else {
                signUpView.raiseToast("User name is already taken!");
            }
        }
    }
}
