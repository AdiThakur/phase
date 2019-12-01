package com.example.game;

import android.content.Context;
import android.util.Log;

import java.util.Random;

class Guess extends Game {

    static final String gameName = "Guess";

    private final static int MAX_BOUND = 250;
    private final static String LOWER_GUESS = "LOWER";
    private final static String HIGHER_GUESS = "HIGHER";

    private int pivotNumber;
    private int streaks;
    private int difficulty;

    private int num1;
    private int num2;
    private int valueOfEquation;
    private String equationToGuess;

    private Random random = new Random();
    private GuessEquation equationGenerator;

    /**
     * Constructor of Guess.
     *
     * @param userName the user's name
     * @param appContext the context of the class
     */
    Guess(String userName, Context appContext, int difficulty) {

        super (userName, appContext, gameName);
        this.streaks = 0;
        this.difficulty = difficulty;

        setDifficulty();
        setUpRound();
    }

    private void setDifficulty() {

        if (difficulty == 1) {
            equationGenerator = new GuessEasyDifficulty();
        } else if (difficulty == 2) {
            equationGenerator = new GuessMediumDifficulty();
        } else {
            equationGenerator = new GuessHardDifficulty();
        }
    }

    /**
     * Set up the math equation and the new the number to guess.
     */
    void setUpRound() {
        pivotNumber = generateNumber(MAX_BOUND);
        int op = generateNumber(4);
        setUpEquation(op);
        Log.i("Guess/", "PivotNumber " + pivotNumber);
        Log.i("Guess/", "ValueOfEquation " + valueOfEquation);
    }

    /**
     * Checks if userGuess is correct and increments streaks.
     *
     * @param userGuess the string input from the user
     * @return the boolean value of whether user is correct or not
     */
    boolean checkCorrect(String userGuess){

        boolean correctGuess = ((valueOfEquation >= pivotNumber && userGuess.equals(HIGHER_GUESS))
                || (valueOfEquation <= pivotNumber && userGuess.equals(LOWER_GUESS)));

        if (correctGuess) {
            incrementStreaks();
            checkNewHighestStreak();
            setUpRound();
        // Wrong guess.
        } else {
            resetStreaks();
        }
        return correctGuess;
    }

    /**
     * Check if current streak is the longest streak.
     */
    private void checkNewHighestStreak() {
        if (user.guessStats.getLongestStreak() < streaks) {
            user.guessStats.setLongestStreak(streaks);
        }
    }

    private void setUpEquation(int operator){
        equationGenerator.setUpEquation(operator);
    }

    /**
     * Generate a random integer given maxBound.
     *
     * @param maxBound the maximum bound of what number to generate
     * @return an integer between 0 and maxBound
     */
    private int generateNumber(int maxBound){
        return random.nextInt(maxBound) + 1;
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

    String getEquationToGuess(){
        return equationToGuess;
    }

    int getPivotNumber(){
        return pivotNumber;
    }

    interface GuessEquation {
        void setUpEquation(int operator);
    }

    // Strategy Pattern for Difficulty.

    private class GuessEasyDifficulty implements  GuessEquation {

        @Override
        public void setUpEquation(int operator) {

            num1 = generateNumber(MAX_BOUND);
            num2 = generateNumber(MAX_BOUND);
            equationToGuess = num1 + "+" + num2;
            valueOfEquation = num1 + num2;
        }
    }

    private class GuessMediumDifficulty implements  GuessEquation {

        @Override
        public void setUpEquation(int operator) {

            num1 = generateNumber(MAX_BOUND);
            num2 = generateNumber(MAX_BOUND);

            if (operator % 2 == 0) {
                equationToGuess = num1 + "+" + num2;
                valueOfEquation = num1 + num2;
            } else {
                equationToGuess = num1 + "-" + num2;
                valueOfEquation = num1 - num2;
            }
        }
    }

    private class GuessHardDifficulty implements  GuessEquation {

        @Override
        public void setUpEquation(int operator) {

            num1 = generateNumber(MAX_BOUND);
            num2 = generateNumber(MAX_BOUND);

            if (operator == 1) {
                equationToGuess = num1 + "+" + num2;
                valueOfEquation = num1 + num2;
            } else if (operator == 2) {
                equationToGuess = num1 + "-" + num2;
                valueOfEquation = num1 - num2;
            } else if (operator == 3) {
                // This ensures that the product isn't too obvious, or big number * big number.
                num1 = generateNumber( (int) (Math.sqrt(MAX_BOUND)));
                num2 = generateNumber( (int) (Math.sqrt(MAX_BOUND)));
                equationToGuess = num1 + "x" + num2;
                valueOfEquation = num1 * num2;
            } else {
                equationToGuess = num1 + "÷" + num2;
                valueOfEquation = num1 / num2;
            }
        }
    }
}
