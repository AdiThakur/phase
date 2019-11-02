package com.example.game;

import android.content.Context;
import android.util.Log;

import java.util.Random;

class MemoryGame extends Game {

    private static final String gameName = "Repeat";

    private StringBuilder numberSequence;

   MemoryGame(String userName, Context appContext) {
       super(userName, appContext, gameName);
       numberSequence = new StringBuilder();
       generateNumber();
   }

    private void generateNumber() {
        Random random = new Random();
        numberSequence.append(random.nextInt(10));
   }

   StringBuilder displaySequence() {
       return numberSequence;
   }

   private void setUpNextLevel(boolean guessCorrect) {

       if (guessCorrect) {
           generateNumber();
       } else {
           user.matchStats.incrementTotalMistakes();
           numberSequence = new StringBuilder();
           generateNumber();
       }
   }

   boolean checkGuess(String enteredNumber) {

       boolean guessCorrect = enteredNumber.equals(numberSequence.toString());
       setUpNextLevel(guessCorrect);
       return guessCorrect;

   }

}
