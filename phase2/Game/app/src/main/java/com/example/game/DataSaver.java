package com.example.game;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

class DataSaver {

    private final Context appContext;

    DataSaver(Context appContext) {
        this.appContext = appContext;
    }

    boolean saveScoreboard(Scoreboard scoreboard) {

        String fileName = scoreboard.getGameName() + "scores.txt";
        String stringToSave = scoreBoardToString(scoreboard);
        PrintWriter out;

        try {
            OutputStream outStream = this.appContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            out = new PrintWriter(outStream);
            out.print(stringToSave);
            out.close();
            return true;
        } catch (FileNotFoundException e) {
            Log.i("DataSaver", "File not found");
            e.printStackTrace();
            return false;
        }

    }

    private String scoreBoardToString(Scoreboard scoreboard) {

        HashMap<String, ArrayList<Integer>> userToScores = scoreboard.getScoreMap();
        StringBuilder output = new StringBuilder();

        for (String userName : userToScores.keySet()) {
            output.append(userName + ":");
            for (int score : userToScores.get(userName)) {
                output.append(score).append(",");
            }
            // After we iterate over each user's score, we add a newline before writing next user's scores.
            output.append("\n");
        }
        return output.toString();
    }

    boolean saveUser(User user, String userName, String password, String lastGame) {

        String stringToSave;
        String fileName = userName + ".txt";
        PrintWriter out;

        if (user == null) {
            stringToSave = defaultUserDataToString(userName, password);
        } else {
            stringToSave = userDataToString(user, lastGame);
        }
        try {
            OutputStream outStream = this.appContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            out = new PrintWriter(outStream);
            out.print(stringToSave);
            out.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    private String defaultUserDataToString(String userName, String password) {

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
        output.append("");
        output.append("\n");

        return output.toString();
    }

    private String userDataToString(User user, String lastGame) {

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
        output.append(user.connectStats.getGamesPlayed() + ", " +
                user.connectStats.getTimePlayed() + ", " + user.connectStats.getGamesWon());
        output.append("\n");
        output.append(user.matchStats.getGamesPlayed() + ", " +
                user.matchStats.getTimePlayed() + ", " + user.matchStats.getTotalMistakes());
        output.append("\n");
        output.append(user.guessStats.getGamesPlayed() + ", " +
                user.guessStats.getTimePlayed() + ", " + user.guessStats.getLongestStreak());
        output.append("\n");
        output.append(lastGame);
        output.append("\n");

        return output.toString();
    }
}
