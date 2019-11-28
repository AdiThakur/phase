package com.example.game;

import android.content.Context;
import java.util.Random;

class Guess extends Game {

    private static final String gameName = "Guess";

    private final int MAX_BOUND = 10000;
    private final String LOWER_GUESS = "LOWER";
    private final String HIGHER_GUESS = "HIGHER";

    private int pivotNumber;
    private int correctNumber;
    private int streaks;

    /**
     * Constructor of Guess.
     *
     * @param userName the user's name
     * @param appContext the context of the class
     */
    Guess(String userName, Context appContext) {

        super (userName, appContext, gameName);

        this.pivotNumber = generateNumber(MAX_BOUND);
        this.correctNumber = generateNumber(MAX_BOUND);
        this.streaks = 0;
    }

    /**
     * Checks if userGuess is correct and increments streaks.
     *
     * @param userGuess the string input from the user
     * @return the boolean value of whether user is correct or not
     */
    boolean checkCorrect(String userGuess){

        boolean correctGuess = ((correctNumber >= pivotNumber && userGuess.equals(HIGHER_GUESS))
                || (correctNumber <= pivotNumber && userGuess.equals(LOWER_GUESS)));
        if (correctGuess) {
            incrementStreaks();
            correctGuess =  true;
            // Round ends: Save time, restart clock.
        } else {
            resetStreaks();
            correctGuess = false;
        }
        checkNewHighestStreak();
        setUpNewNumber();
        return correctGuess;
    }

    /**
     * Sets the amount of time played.
     *
     * @param timePlayedInSeconds amount of time played
     */
    void setTimePlayed(long timePlayedInSeconds) {
        user.guessStats.incrementTimePlayed(timePlayedInSeconds);
    }

    /**
     * Check if current streak is the longest streak.
     */
    private void checkNewHighestStreak() {
        if (user.guessStats.getLongestStreak() < streaks) {
            user.guessStats.setLongestStreak(streaks);
        }
    }

    /**
     * Sets up the new number to guess.
     */
    private void setUpNewNumber() {
        correctNumber = generateNumber(MAX_BOUND);
        pivotNumber = generateNumber(MAX_BOUND);
    }

    /**
     * Generate a random integer given maxBound.
     *
     * @param maxBound the maximum bound of what number to generate
     * @return an integer between 0 and maxBound
     */
    private int generateNumber(int maxBound){
        Random r = new Random();
        return r.nextInt(maxBound + 1);
    }

    /**
     * Gets the streaks.
     *
     * @return the current streak
     */
    int getStreaks() {
        return streaks;
    }

    /**
     * Resets the streak.
     */
    private void resetStreaks() {
        streaks = 0;
    }

    /**
     * Increments streak.
     */
    private void incrementStreaks() {
        streaks++;
    }

    /**
     * Get the pivot number to guess.
     *
     * @return the pivot number
     */
    int getPivotNumber() {
        return pivotNumber;
    }


}
