package com.example.game;

import android.content.Context;
import android.util.Log;

import java.util.Random;

public class MemoryGame {

    private User user;
    private Context appContext;
    private StringBuilder numberSequence;
    private long startTime;


   MemoryGame(String userName, Context appContext) {
       this.appContext = appContext;
       this.user = new DataLoader(this.appContext).loadUser(userName);
       user.matchStats.incrementGamesPlayed();

       startTime = System.currentTimeMillis();
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

   void saveData() {
       long durationInSeconds = (System.currentTimeMillis() - startTime)/1000;
       user.matchStats.incrementTimePlayed(durationInSeconds);
       new DataSaver(this.appContext).saveUser(user, user.getName(), user.getPassword());
   }

}
