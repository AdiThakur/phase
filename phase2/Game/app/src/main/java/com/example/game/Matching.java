package com.example.game;


import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Matching extends Game {

    private static final String gameName = "Match";
    private static HashMap<String, Integer> difficultyMap = new HashMap<String, Integer>();

    static {
        difficultyMap.put("Easy", 6);
        difficultyMap.put("Medium", 8);
        difficultyMap.put("Hard", 10);
    }

    private ArrayList<Integer> deck = new ArrayList<>();
    private int numPairs;
    private int mistakes;
    private int matchedPairs;


    /**
     * Constructs an instance of Matching with a given difficulty for a given user.
     *
     * @param userName the user's name
     * @param appContext the context of the class
     * @param difficulty the difficulty chosen by the user (Easy, Medium, or Hard)
     */
    Matching(String userName, Context appContext, String difficulty) {
        super(userName, appContext, gameName);
        this.numPairs = difficultyMap.get(difficulty);
        generateDeck();
        matchedPairs = 0;
    }

    /**
     * Getter for difficulty. The difficulty is equivalent to the number of pairs that
     * will be in the deck.
     *
     * @return the number of pairs in the game as an int
     */
    public int getDifficulty() {
        return numPairs;
    }

    /**
     * Creates the deck by generatinng random numbers between 0 and numPairs * 2 - 1.
     * A pair of numbers have the same remainder when divided by the number of pairs.
     *
     * For example if the difficulty is medium and numPairs = 8, 0 and 8 will be pairs,
     * 1 and 9 will be pairs, 2 and 10 and so on. All the integers get added to the ArrayList
     * deck and after that is finished the ArrayList gets shuffled.
     */
    private void generateDeck() {
//        Create the cards as pairs and add them to the deck
        for (int i = 0; i < numPairs * 2; i++) {
            deck.add(i);
        }
        ShuffleDeck();
    }


    /**
     * Gets the deck.
     *
     * @return the arraylist of integers that represents the deck
     */
    ArrayList<Integer> getDeck() {
        return this.deck;
    }

    /**
     * Shuffles the deck randomly
     */
    private void ShuffleDeck() {
        Collections.shuffle(deck);
    }

    /**
     * Takes two different positions as input and checks whether the Integers
     * in deck that are in those positions are pairs. For a definition of pair see generateDeck().
     * Returns true if the two Integers in the given positions are pairs and false otherwise.
     *
     * @param position1 - the position of the first chosen value
     * @param position2 - the position of the second chosen value
     * @return whether or not the guess was correct as a boolean
     */
    public boolean guess(int position1, int position2) {
        Integer card1 = deck.get(position1) % numPairs;
        Integer card2 = deck.get(position2) % numPairs;
        boolean guess = card1.equals(card2);
        if (guess) {
            matchedPairs++;
            score += Math.max(mistakes, 19);

        } else {
            user.matchStats.incrementTotalMistakes();
            mistakes++;
        }
        return guess;
    }

    /**
     * Get the total mistakes made so far
     *
     * @return int
     */
    int getMistakes() {
        return mistakes;
    }

    /**
     * Get the total number of matched pairs found so far.
     *
     * @return the number of matched pairs as an int
     */
    int getMatchedPairs() {
        return matchedPairs;
    }

    /**
     * Determine whether or not the game has been won.
     * The game is won when all distinct pairs have been found. This will be the case when
     * matchedPairs is equal to numPairs. Return
     *
     * @return whether or not the game has been won as a boolean
     */
    boolean checkGameWon() {
        if (matchedPairs == numPairs) {
            String str = "Congratulations!";
            System.out.println(str);
            return true;
        } else {
            return false;
        }

    }

}
