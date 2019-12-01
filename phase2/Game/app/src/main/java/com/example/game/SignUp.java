package com.example.game;

import android.content.Context;

class SignUp {

    /**
     * Checks to see if the username already exists.
     *
     * @param username - the string corresponding to the name the user chose.
     * @param applicationContext - the context of the application
     * @return - the user corresponding to the username if it exists and null otherwise
     */
    User checkUsername(String username, Context applicationContext) {
        DataLoader dataLoader = new DataLoader(applicationContext);
        return dataLoader.loadUser(username);
    }

    /**
     * Creates an account for the user by saving the username and corresponding password.
     *
     * @param applicationContext - the context of the application
     * @param username - the username the user chose
     * @param password - the password the user chose
     * @return - whether or not the user has been created
     */
    boolean signUpUser(Context applicationContext, String username, String password) {
        DataSaver dataSaver = new DataSaver(applicationContext);
        return dataSaver.saveUser(null, username, password, null);
    }
}
