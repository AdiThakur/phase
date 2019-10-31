package com.example.game;

import android.content.Context;
import java.util.Random;

class Guess {

    private final int MAX_BOUND = 10000;
    private final String LOWER_GUESS = "LOWER";
    private final String HIGHER_GUESS = "HIGHER";

    private int pivotNumber;
    private int correctNumber;
    private int streaks;
    private User user;
    private Context appContext;

    Guess(String userName, Context appContext) {

        this.appContext = appContext;
        user = new DataLoader(this.appContext).loadUser(userName);
        user.guessStats.incrementGamesPlayed();

        this.pivotNumber = generateNumber(MAX_BOUND);
        this.correctNumber = generateNumber(MAX_BOUND);
        this.streaks = 0;

    }
    // TODO - Implement a stopwatch (records how long you play each session).

    boolean checkCorrect(String userGuess){

        boolean correctGuess = ((correctNumber >= pivotNumber && userGuess.equals(HIGHER_GUESS))
                || (correctNumber <= pivotNumber && userGuess.equals(LOWER_GUESS)));
        if (correctGuess) {
            incrementStreaks();
            correctGuess =  true;
            // Round ends: Save time, restart clock.
        } else {
            setStreaks(0);
            correctGuess = false;
        }
        checkNewHighestStreak();
        setUpNewNumber();
        return correctGuess;
    }

    void setTimePlayed(long timePlayedInSeconds) {
        this.user.guessStats.incrementTimePlayed(timePlayedInSeconds);
    }

    private void checkNewHighestStreak() {
        if (this.user.guessStats.getLongestStreak() < this.streaks) {
            this.user.guessStats.setLongestStreak(this.streaks);
        }
    }

    void saveData() {
        new DataSaver(this.appContext).saveUser(this.user, this.user.getName(),
                this.user.getPassword());
    }

    private void setUpNewNumber() {
        this.correctNumber = generateNumber(MAX_BOUND);
        this.pivotNumber = generateNumber(MAX_BOUND);
    }

    private int generateNumber(int maxBound){
        Random r = new Random();
        return r.nextInt(maxBound + 1);
    }

    int getStreaks() {
        return this.streaks;
    }

    private void setStreaks(int newStreaks) {
        this.streaks = newStreaks;
    }

    private void incrementStreaks() {
        this.streaks++;
    }

    int getPivotNumber() {
        return this.pivotNumber;
    }

    int getCorrectNumber() {
        return this.correctNumber;
    }

}
