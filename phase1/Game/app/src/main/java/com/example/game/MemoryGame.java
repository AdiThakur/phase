package com.example.game;

import android.content.Context;
import android.util.Log;

import java.util.Random;

public class MemoryGame {

    private User user;
    private Context appContext;
    private StringBuilder numberSequence;
    private long startTime;

    private int streaks;



    MemoryGame(String userName, Context appContext) {
       this.appContext = appContext;
       this.user = new DataLoader(this.appContext).loadUser(userName);
       user.matchStats.incrementGamesPlayed();

       this.streaks = 0;
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
           incrementStreaks();
       } else {
           user.matchStats.incrementTotalMistakes();
           numberSequence = new StringBuilder();
           generateNumber();
           setStreaks(0);
       }
   }

   boolean checkGuess(String enteredNumber) {

       boolean guessCorrect = enteredNumber.equals(numberSequence.toString());
       setUpNextLevel(guessCorrect);
       return guessCorrect;

   }

   void saveData() {
       long durationInSeconds = (System.currentTimeMillis() - startTime)/1000;
       Log.i("MemoryGame - Time Played", durationInSeconds + "");
       user.matchStats.incrementTimePlayed(durationInSeconds);
       new DataSaver(this.appContext).saveUser(user, user.getName(), user.getPassword());
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

}
