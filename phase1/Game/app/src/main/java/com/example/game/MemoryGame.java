package com.example.game;

import android.content.Context;

import java.util.Random;

class MemoryGame extends Game {

    private static final String gameName = "Repeat";

    private StringBuilder numberSequence;

    /**
     * The memory game.
     * @param userName current user playing this game
     * @param appContext appContext
     */
   MemoryGame(String userName, Context appContext) {
       super(userName, appContext, gameName);
       numberSequence = new StringBuilder();
       generateNumber();
   }

    /**
     * Generates the random number.
     */
    private void generateNumber() {
        Random random = new Random();
        numberSequence.append(random.nextInt(10));
   }

    /**
     * Displays the sequence of numbers.
     * @return the sequence of numbers
     */
   StringBuilder displaySequence() {
       return numberSequence;
   }

    /**
     * Sets up the next, harder level or resets the difficulty if the user failed.
     * @param guessCorrect if the user guessed correctly or not
     */
   private void setUpNextLevel(boolean guessCorrect) {

       if (guessCorrect) {
           generateNumber();
       } else {
           user.matchStats.incrementTotalMistakes();
           numberSequence = new StringBuilder();
           generateNumber();
       }
   }

    /**
     * Checks if the users guess was correct or not, and sets up the next level accordingly.
     * @param enteredNumber the number entered
     * @return if the guess was correct or not
     */
   boolean checkGuess(String enteredNumber) {

       boolean guessCorrect = enteredNumber.equals(numberSequence.toString());
       setUpNextLevel(guessCorrect);
       return guessCorrect;

   }

}
