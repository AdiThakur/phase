package com.example.game;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class DataLoader {

    // Constant indices for user data stored in text file.
    private final int USERNAME_INDEX = 0;
    private final int PASSWORD_INDEX = 1;
    private final int BACKGROUNDCOLOR_INDEX = 2;
    private final int CUSTOM1_INDEX = 3;
    private final int CUSTOM2_INDEX = 4;
    private final int CONNECT_STATS = 5;
    private final int MATCH_STATS = 6;
    private final int GUESS_STATS = 7;
    private final int LAST_GAME = 8;

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
    private ArrayList<String> dataToArrayList(String filePath) {

        ArrayList<String> dataFromFile = new ArrayList<>();
        // Reading from file
        try (Scanner scanner = new Scanner(this.appContext.openFileInput(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                dataFromFile.add(line);
            }
        } catch (IOException e) {
            dataFromFile = null;
        }
        return dataFromFile;

    }

    Scoreboard loadScoreBoard(String gameName) {

        String filePath = gameName + "scores.txt";
        Scoreboard scoreboard = new Scoreboard(gameName);

        ArrayList<String> dataFromFile = dataToArrayList(filePath);

        // Condition: ArrayList dataFromFile cannot be empty or null.
        if (dataFromFile != null && !dataFromFile.isEmpty()) {
            for (String line : dataFromFile) {
                // Splitting userName from scores.
                String[] dataFromCurrLine = line.split(":");
                String userName = dataFromCurrLine[0];
                // Splitting all the scores apart.
                String[] scoresAsStrings = dataFromCurrLine[1].split(",");
                // For each score that this user has for this particular game.
                for (String scoreString : scoresAsStrings) {
//                    Log.i("Score/loadScoreboard", userName + " " + scoreString);
                    scoreboard.addScore(userName, Integer.valueOf(scoreString));
                }
            }
        }
        return scoreboard;
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
        String filePath = userName + ".txt";
        ArrayList<String> userDataFromFile = dataToArrayList(filePath);

        // No data was loaded for user: User doesn't exist.
        if (userDataFromFile == null) {
            user = null;
        } else {
            user = new User(userName);
            user.setPassword(userDataFromFile.get(PASSWORD_INDEX));
            // Three preferences.
            user.setBackgroundColor(userDataFromFile.get(BACKGROUNDCOLOR_INDEX));
            user.setIndexOfCustomization1(userDataFromFile.get(CUSTOM1_INDEX));
            user.setIndexOfCustomization2(userDataFromFile.get(CUSTOM2_INDEX));
            // Statistics for Connect.
            String[] connectStats = userDataFromFile.get(CONNECT_STATS).split(",");
            user.initializeConnectStats(Integer.parseInt(connectStats[0].trim()),
                    Long.parseLong(connectStats[1].trim()), Integer.parseInt(connectStats[2].trim()));
            // Statistics for Match.
            String[] matchStats = userDataFromFile.get(MATCH_STATS).split(",");
            user.initializeMatchStats(Integer.parseInt(matchStats[0].trim()),
                    Long.parseLong(matchStats[1].trim()), Integer.parseInt(matchStats[2].trim()));
            // Statistics for Guess
            String[] guessStats = userDataFromFile.get(GUESS_STATS).split(",");
            user.initializeGuessStats(Integer.parseInt(guessStats[0].trim()),
                    Long.parseLong(guessStats[1].trim()), Integer.parseInt(guessStats[2].trim()));
            user.setLastGame(userDataFromFile.get(LAST_GAME).trim());
        }
        return user;
    }
}
