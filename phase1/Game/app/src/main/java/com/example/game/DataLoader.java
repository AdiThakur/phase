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

    private final String TAG = "DataLoader";
    private Context appContext;

    DataLoader(Context appContext) {
        this.appContext = appContext;
    }

    // Reads data from user.txt and loads it into an ArrayList<String>.
    private ArrayList<String> dataToArrayList(String userName) {

        // Saves data read from text file.
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
            // Three sets of stats.
            // TODO: Create a super class game - contains stats games played and time played. Make each game inherit from it.
            // user.setConnectStats(userData.get(LANGUAGE_INDEX));

        }
        return user;

    }
}
