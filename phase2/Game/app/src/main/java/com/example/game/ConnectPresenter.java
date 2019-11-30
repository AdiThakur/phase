package com.example.game;

import android.content.Context;

public class ConnectPresenter {

    Connect game;

    ConnectPresenter(String userName, Context appContext, int gridSize, int difficulty) {
        game = new Connect(userName, appContext, gridSize, difficulty);
    }

    /**
     * Check if x and y are valid coordinates.
     *
     * @param x x-coordinate of the board
     * @param y y-coordinate of the board
     * @return whether if it is a valid move
     */
    boolean validMove(int x, int y) {
        return game.validMove(x,y);
    }

    /**
     * Handles the player move function.
     *
     * @param x x-coordinate of the board
     * @param y y-coordinate of the board
     * @return a message of who won the game, and the computer's last move
     */
    String playerMove(int x, int y) {

        return game.playerMove(x, y);
    }

    int getScore() {return game.getScore();}

    String getName() {return game.getName();}

    void resetBoard() {game.resetBoard();}

    void saveUserData() { game.saveUserData(); }




}
