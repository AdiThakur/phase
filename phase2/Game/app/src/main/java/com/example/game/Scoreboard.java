package com.example.game;

import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class Scoreboard {

    static void sortFormattedScoreboard(String sortBy, ArrayList<String> formattedScoresList) {

        int size = formattedScoresList.size();
        if (sortBy.equalsIgnoreCase("NAME")) {

            Collections.sort(formattedScoresList);

        } else if (sortBy.equalsIgnoreCase("SCORE")) {

            for (int i = 0; i < size-1; i++)
                for (int j = 0; j < size-i-1; j++) {

                    int currScore = extractScore(formattedScoresList.get(j));
                    int nextScore = extractScore(formattedScoresList.get(j + 1));

                    if (currScore > nextScore) {
                        // swap arr[j+1] and arr[i]
                        String temp = formattedScoresList.get(j);
                        formattedScoresList.set(j, formattedScoresList.get(j+1));
                        formattedScoresList.set(j+1, temp);
                    }
                }
        }
    }

    private static int extractScore(String formattedScoreString) {

        String[] splitFormattedScoreString = formattedScoreString.split(":");
        return Integer.valueOf(splitFormattedScoreString[1].trim());
    }

    // User: score
    private HashMap<String, ArrayList<Integer>> scores;
    private String gameName;

    Scoreboard(String nameOfGame) {
        scores = new HashMap<>();
        gameName = nameOfGame;
    }

    void addScore(String userName, int score) {
        if (!scores.containsKey(userName)) {
            scores.put(userName, new ArrayList<Integer>());
            scores.get(userName).add(score);
        } else if (scores.containsKey(userName) && scores.get(userName) != null) {
            scores.get(userName).add(score);

        }
        Log.i("Score/Scoreboard.addScore() - Displaying HashMap", scores.toString());
    }

    HashMap<String, ArrayList<Integer>> getScoreMap() {
        return scores;
    }

    String getGameName() {
        return this.gameName;
    }

    ArrayList<String> formatToArrayList() {

        ArrayList<String> formattedScoresList = new ArrayList<>();

        for (String userName : scores.keySet()) {
            for (int score : scores.get(userName)) {
                String formattedScore = userName + " : " + score;
                formattedScoresList.add(formattedScore);
            }
        }
        return formattedScoresList;
    }

}
