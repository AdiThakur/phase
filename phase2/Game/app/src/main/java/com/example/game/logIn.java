package com.example.game;

import android.content.Context;

class LogIn {

    /**
     * Uses a DataLoader to try to load a User from the username given.
     *
     * @param username - the username the user tried to log in with
     * @param appContext - the context of the application
     * @return - an instance of user corresponding to the given username and null if the user does
     * not exist
     */
    User loginUser(String username, Context appContext) {
        DataLoader dataLoader = new DataLoader(appContext);
        return dataLoader.loadUser(username);
    }
}
