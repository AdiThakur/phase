package com.example.game;

import android.content.Context;

import java.util.ArrayList;

public class MatchingPresenter {

    private Matching game;
    private int difficulty;

    /**
     * Given two positions, return whether or not the values in those positions are pairs.
     *
     * @param position1 - int representing the position of the first value
     * @param position2 - int representing the position of the second value
     * @return true if the two values at the given positions are pairs
     */
    public boolean guess(int position1, int position2) {
        return game.guess(position1, position2);
    }

    /**
     * Save the game data.
     *
     */
    void saveData() {
        game.saveUserData();
    }

    /**
     * Get the number of pairs found so far in the game.
     *
     * @return int representing the number of pairs found
     */
    int getPairsFound() {
        return game.getMatchedPairs();
    }

    /**
     * Check whether the game has been won or not.
     *
     * @return true if the game has been won and false otherwise
     */
    boolean checkGameWon() {
        return game.checkGameWon();
    }

    int getBackgroundColor() {
        return game.getBackgroundColor();
    }

    public interface View {

    }

    /**
     * Constructs an intstance of MatchingPresenter and creates an instance of Matching with the
     * given difficulty.
     *
     * @param user - the username of the user currently playing the game
     * @param appContext - the context of the application
     * @param difficulty - a string representing the difficulty chosen by the user
     */
    MatchingPresenter(String user, Context appContext, String difficulty) {
        game = new Matching(user, appContext, difficulty);
        this.difficulty = game.getDifficulty();
    }

    /**
     * Gets the deck for the Matching game.
     *
     * @return an arraylist representing the deck
     */
    ArrayList<Integer> getDeck() {
        return game.getDeck();
    }

    /**
     * Gets the difficulty/number of pairs.
     *
     * @return the int representing the difficulty/number of pairs
     */
    int getDifficulty() {
        return difficulty;
    }

    /**
     * Gets the total number of mistakes made so far in the game.
     *
     * @return int representing the number of mistakes made
     */
    int getMistakes() {
        return game.getMistakes();
    }

    /**
     * Gets the score for the current game.
     *
     * @return it representing the score for the Matching game.
     */
    int getScore() { return game.getScore(); }

    /**
     * Gets the name of the game.
     *
     * @return String representing the name of the game which is "Match"
     */
    String getGameName() {return game.getName();}
}
