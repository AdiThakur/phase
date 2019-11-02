package com.example.game;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class DataLoader {

    private final int USERNAME_INDEX = 0;
    private final int PASSWORD_INDEX = 1;
    private final int BACKGROUNDCOLOR_INDEX = 2;
    private final int TEXTCOLOR_INDEX = 3;
    private final int LANGUAGE_INDEX = 4;
    private final int CONNECT_STATS = 5;
    private final int MATCH_STATS = 6;
    private final int GUESS_STATS = 7;

    private final String TAG = "DataLoader";
    private final Context appContext;

    /**
     * Constructor for DataLoader.
     *
     * @param appContext the context of the class
     */
    DataLoader(Context appContext) {
        this.appContext = appContext;
    }

    // Reads data from user.txt and loads it into an ArrayList<String>.
    private ArrayList<String> dataToArrayList(String userName) {

        String fileName = userName + ".txt";
        ArrayList<String> dataFromFile = new ArrayList<>();
        // Reading from file
        try (Scanner scanner = new Scanner(this.appContext.openFileInput(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                dataFromFile.add(line);
            }
        } catch (IOException e) {
            Log.e(TAG, "Couldn't open " + fileName + ". Loading default user.");
            dataFromFile = null;
        }

        return dataFromFile;

    }

    /**
     * Load the user with the username of userName.
     *
     * @param userName the user's name
     * @return the user with the correct username
     */
    // Only public method. Allows us to change how data is stored/loaded.
    User loadUser(String userName) {

        User user;
        ArrayList<String> userData = dataToArrayList(userName);

        // No data was loaded for user: User doesn't exist.
        if (userData == null) {
            user = null;
        } else {
            user = new User(userName);
            user.setPassword(userData.get(PASSWORD_INDEX));
            // Three preferences.
            user.setBackgroundColor(userData.get(BACKGROUNDCOLOR_INDEX));
            user.setTextColor(userData.get(TEXTCOLOR_INDEX));
            user.setLanguage(userData.get(LANGUAGE_INDEX));
            // Statistics for Connect.
            String[] connectStats = userData.get(CONNECT_STATS).split(",");
            user.initializeConnectStats(Integer.parseInt(connectStats[0].trim()),
                    Long.parseLong(connectStats[1].trim()), Integer.parseInt(connectStats[2].trim()));
            // Statistics for Match.
            String[] matchStats = userData.get(MATCH_STATS).split(",");
            user.initializeMatchStats(Integer.parseInt(matchStats[0].trim()),
                    Long.parseLong(matchStats[1].trim()), Integer.parseInt(matchStats[2].trim()));
            // Statistics for Guess
            String[] guessStats = userData.get(GUESS_STATS).split(",");
            user.initializeGuessStats(Integer.parseInt(guessStats[0].trim()),
                    Long.parseLong(guessStats[1].trim()), Integer.parseInt(guessStats[2].trim()));
        }
        return user;
    }
}
