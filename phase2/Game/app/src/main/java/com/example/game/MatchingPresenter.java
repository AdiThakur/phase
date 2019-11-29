package com.example.game;

import android.content.Context;

import java.util.ArrayList;

public class MatchingPresenter {

    private Matching game;
    private int difficulty;

    public boolean guess(int position1, int position2) {
        return game.guess(position1, position2);
    }

    public void saveData() {
        game.saveUserData();
    }

    public interface View {

    }

    public MatchingPresenter(String user, Context appContext, String difficulty) {
        game = new Matching(user, appContext, difficulty);
        this.difficulty = game.getDifficulty();
    }

    public ArrayList<Integer> getDeck() {
        return game.getDeck();
    }

//        public void saveData() {
//                game.saveData();
//        }

    public int getDifficulty() {
        return difficulty;
    }

    public int getMistakes() {
        return game.getMistakes();
    }

    public int getScore() { return game.getScore(); }

    public String getGameName() {return game.getName();}
}
