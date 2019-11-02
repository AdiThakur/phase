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

    Guess(String userName, Context appContext) {

        super (userName, appContext, gameName);

        this.pivotNumber = generateNumber(MAX_BOUND);
        this.correctNumber = generateNumber(MAX_BOUND);
        this.streaks = 0;
    }

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

    void setTimePlayed(long timePlayedInSeconds) {
        user.guessStats.incrementTimePlayed(timePlayedInSeconds);
    }

    private void checkNewHighestStreak() {
        if (user.guessStats.getLongestStreak() < streaks) {
            user.guessStats.setLongestStreak(streaks);
        }
    }

    private void setUpNewNumber() {
        correctNumber = generateNumber(MAX_BOUND);
        pivotNumber = generateNumber(MAX_BOUND);
    }

    private int generateNumber(int maxBound){
        Random r = new Random();
        return r.nextInt(maxBound + 1);
    }

    int getStreaks() {
        return streaks;
    }

    private void resetStreaks() {
        streaks = 0;
    }

    private void incrementStreaks() { streaks++;
    }

    int getPivotNumber() {
        return pivotNumber;
    }


}
