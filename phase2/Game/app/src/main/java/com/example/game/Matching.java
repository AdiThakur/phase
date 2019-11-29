package com.example.game;


import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Matching extends Game {
    private static final String gameName = "Match";
    //    public User user;
//    public int score;
//    public int time;
//    public int mistakes;
//    public int matchedPairs;
//    public int unmatchedPairs;
//    public int cardsFlipped;
//    public String difficulty;

    private static HashMap<String, Integer> difficultyMap = new HashMap<String, Integer>();
    static {
        difficultyMap.put("Easy", 6);
        difficultyMap.put("Medium", 8);
        difficultyMap.put("Hard", 10);
    }
//    public Card[][]  =

    private ArrayList<Integer> deck = new ArrayList<>();
    private int numPairs;
    private int mistakes;
    private int matchedPairs;

    public Matching(String userName, Context appContext, String difficulty) {
        super(userName, appContext, gameName);
        this.numPairs = difficultyMap.get(difficulty);
        generateDeck();
    }

//    public void startTimer() {
//        time = 0;
//
//    }

//    public boolean updateScore() {
//
//    }


    public int getDifficulty() {
        return numPairs;
    }

    public void generateDeck()
    {
//        Create the cards as pairs and add them to the deck
        for (int i = 0; i < numPairs * 2; i++) {
            deck.add(i);
        }
        ShuffleDeck();
    }


    public ArrayList<Integer> getDeck(){
        return this.deck;
    }

    public void ShuffleDeck() {
        Collections.shuffle(deck);
    }

    public boolean guess(int position1, int position2) {
        Integer card1 = deck.get(position1) % numPairs;
        Integer card2 = deck.get(position2) % numPairs;
        boolean guess = card1.equals(card2);
        if (guess) {
            matchedPairs++;
            score += Math.max(mistakes, 19);
            checkGameWon();

        } else {
            user.matchStats.incrementTotalMistakes();
            mistakes++;
        }
        return guess;
    }

    public int getMistakes() {
        return mistakes;
    }

    public int getMatchedPairs() {
        return matchedPairs;
    }

    private boolean checkGameWon() {
        if (matchedPairs == 6) {
            String str = "Congratulations!";
            System.out.println(str);
            return true;
        } else {
            return false;
        }

    }

}
