package com.example.game;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;

class DataSaver {

    private final String TAG = "DataSaver";
    private final Context appContext;

    /**
     * Constructor of DataSaver.
     *
     * @param appContext the context of the class
     */
    DataSaver(Context appContext) {
        this.appContext = appContext;
    }

    /**
     * Save user information.
     *
     * @param user the current user
     * @param userName the user's name
     * @param password the user's password
     * @return whether user information is saved
     */
    boolean saveUser(User user, String userName, String password) {

        String stringToSave;
        String fileName = userName + ".txt";
        PrintWriter out;

        if (user == null) {
            stringToSave = defaultDataToString(userName, password);
        } else {
            stringToSave = userDataToString(user);
        }
        try {
            OutputStream outStream = this.appContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            out = new PrintWriter(outStream);
            out.print(stringToSave);
            out.close();
            Log.i(TAG, "File saved in" + this.appContext.getFilesDir());
            return true;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Error encountered trying to open file for writing: " + fileName);
            return false;
        }
    }

    /**
     * Change default data to string.
     *
     * @param userName the user's name
     * @param password the user's password
     * @return the user's data in String
     */
    private String defaultDataToString(String userName, String password) {

        StringBuilder output = new StringBuilder();
        output.append(userName);
        output.append("\n");
        output.append(password);
        output.append("\n");
        output.append("white"); // BG color.
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

    /**
     * Change user's data to string.
     *
     * @param user the current user
     * @return user's data in string
     */
    private String userDataToString(User user) {

        StringBuilder output = new StringBuilder();

        output.append(user.getName());
        output.append("\n");
        output.append(user.getPassword());
        output.append("\n");
        output.append(user.getBackgroundColor()); // BG color.
        output.append("\n");
        output.append(user.getTextColor()); // Text color
        output.append("\n");
        output.append(user.getLanguage()); // Language
        output.append("\n");
        // TODO - Change concatenations to append.
        output.append(user.connectStats.getGamesPlayed() + ", " +
                user.connectStats.getTimePlayed() + ", " + user.connectStats.getGamesWon());
        output.append("\n");
        output.append(user.matchStats.getGamesPlayed() + ", " +
                user.matchStats.getTimePlayed() + ", " + user.matchStats.getTotalMistakes());
        output.append("\n");
        output.append(user.guessStats.getGamesPlayed() + ", " +
                user.guessStats.getTimePlayed() + ", " + user.guessStats.getLongestStreak());
        output.append("\n");

        return output.toString();
    }
}
