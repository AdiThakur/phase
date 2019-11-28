package com.example.game;

import android.content.Context;
import java.util.Random;
import java.lang.Math;

class Guess extends Game {

    private static final String gameName = "Guess";

    private final int MAX_BOUND = 10000;
    private final String LOWER_GUESS = "LOWER";
    private final String HIGHER_GUESS = "HIGHER";

    private int equationNumber;
    private int correctNumber;
    private int streaks;

    private String guessingEquation;
    private int num1;
    private int num2;


    /**
     * Constructor of Guess.
     *
     * @param userName the user's name
     * @param appContext the context of the class
     */
    Guess(String userName, Context appContext) {

        super (userName, appContext, gameName);

        this.correctNumber = generateNumber(MAX_BOUND);

        Random r = new Random();
        setUpMath(r.nextInt(5));

        this.streaks = 0;
    }

    /**
     * Checks if userGuess is correct and increments streaks.
     *
     * @param userGuess the string input from the user
     * @return the boolean value of whether user is correct or not
     */
    boolean checkCorrect(String userGuess){

        boolean correctGuess = ((correctNumber <= equationNumber && userGuess.equals(HIGHER_GUESS))
                || (correctNumber >= equationNumber && userGuess.equals(LOWER_GUESS)));
        if (correctGuess) {
            incrementStreaks();
            correctGuess =  true;
            // Round ends: Save time, restart clock.
        } else {
            resetStreaks();
            correctGuess = false;
        }
        checkNewHighestStreak();

        setUpRound();

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
     * Set up the math equation and the new the number to guess.
     */
    private void setUpRound() {
        correctNumber = generateNumber(MAX_BOUND);
        Random r = new Random();
        int op = r.nextInt(5);
        setUpMath(op);
    }

    private void setUpMath(int op){
        switch(op){
            case 1:
                num1 = generateNumber((int)(Math.sqrt(MAX_BOUND)));
                num2 = generateNumber((int)(Math.sqrt(MAX_BOUND)));
                guessingEquation = num1 + "x" + num2;
                equationNumber = num1 * num2;
                break;

            case 2:
                num1 = generateNumber(MAX_BOUND);
                num2 = generateNumber(MAX_BOUND);
                guessingEquation = num1 + "+" + num2;
                equationNumber = num1 + num2;
                break;

            case 3:
                num1 = generateNumber(MAX_BOUND);
                num2 = generateNumber(MAX_BOUND);
                guessingEquation = num1 + "-" + num2;
                equationNumber = num1 - num2;
                break;

            case 4:
                num1 = generateNumber(MAX_BOUND);
                num2 = generateNumber(10);
                guessingEquation = num1 + "÷" + num2;
                equationNumber = Math.round(num1/num2);
                break;
        }
    }

    /**
     * Generate a random integer given maxBound.
     *
     * @param maxBound the maximum bound of what number to generate
     * @return an integer between 0 and maxBound
     */
    private int generateNumber(int maxBound){
        Random r = new Random();
        return r.nextInt(maxBound) + 1;
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
    int getEquationNumber() {
        return equationNumber;
    }

    /**
     * Get the pivot equation to guess.
     *
     * @return the pivot equation
     */
    String getPivotEquation(){
        return guessingEquation;
    }

    int getCorrectNumber(){
        return correctNumber;
    }


}
