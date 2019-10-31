package com.example.game;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class MemoryGame {

    private User user;
    private Context appContext;
    private int score;
    private int mistakes;
    private StringBuilder numberSeqeunce;


   MemoryGame(String userName, Context appContext) {
       this.appContext = appContext;
       this.user = new DataLoader(this.appContext).loadUser(userName);

       numberSeqeunce = new StringBuilder();
       this.score = 0;

       generateNumber();
   }

    private void generateNumber() {
        Random random = new Random();
        numberSeqeunce.append(random.nextInt(10));
   }

   StringBuilder displaySequence() {
       return numberSeqeunce;
   }

   private void setUpNextLevel(boolean guessCorrect) {

       if (guessCorrect) {
           generateNumber();
       } else {
           user.matchStats.incrementTotalMistakes();
           numberSeqeunce = new StringBuilder();
           generateNumber();
       }
   }

   boolean checkGuess(String enteredNumber) {

       boolean guessCorrect = enteredNumber.equals(numberSeqeunce.toString());
       setUpNextLevel(guessCorrect);
       return guessCorrect;

   }

}
