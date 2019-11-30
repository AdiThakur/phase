package com.example.game;

import android.content.Context;

public class GuessPresenter {

    Guess game;

    GuessPresenter(String userName, Context appContext, int difficulty) {
        game = new Guess(userName, appContext, difficulty);
    }

    int getStreaks() {
        return game.getStreaks();
    }

    boolean checkCorrect(String userGuess) {
        return game.checkCorrect(userGuess);
    }

    public String getName() {
        return game.getName();
    }

    int getPivotNumber() {
        return game.getPivotNumber();
    }

    String getEquationToGuess() {
        return game.getEquationToGuess();
    }

    void setUpRound() {
        game.setUpRound();
    }

    void saveUserData() {
        game.saveUserData();
    }
}
