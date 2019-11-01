package com.example.game;

import android.content.Context;
import android.util.Log;

public class Connect {

    private final int PLAYER_1 = 1;
    private final int COMPUTER = 2;
    private final int row = 3;
    private final int col = 3;

    // Instance variables
    private final Context appContext;
    private User user;
    private long startTime;

    boolean gameOver;
    int[][] gameBoard;

    Connect(String userName, Context appContext) {

        this.appContext = appContext;
        this.user = new DataLoader(this.appContext).loadUser(userName);
        user.connectStats.incrementGamesPlayed();
        startTime = System.currentTimeMillis();
        this.gameOver = false;
        this.gameBoard = new int[row][col];
    }

    boolean validMove(int x, int y) {

        if (gameBoard[x][y] == 0) {
            gameBoard[x][y] = PLAYER_1;
            return true;
        } else {
            return false;
        }
    }

    String playerMove(int x, int y) {

        gameBoard[x][y] = PLAYER_1;
        if (checkGameWon(PLAYER_1)) {
            resetBoard();
            user.connectStats.incrementGamesWon();
            return "P";
        }
        String computerMove = computerMove();
        if (checkGameWon(COMPUTER)) {
            resetBoard();
            return "C";
        }
        return computerMove;
    }

    private String computerMove() {
        // Very basic (literally random) AI
        int count = 0;
        String computerMove = null;
        for (int x = 0; x < gameBoard.length; x++) {
            for (int y = 0; y < gameBoard[x].length; y++) {
                if (gameBoard[x][y] == 0 && count < 1) {
                    gameBoard[x][y] = COMPUTER;
                    count++;
                    computerMove = x + "" + y + "";
                }
            }
        }
        return computerMove;
    }

    boolean checkGameWon(int player) {

        if (checkHorizontal(player) || checkVertical(player) || checkDiagonal(player)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkHorizontal(int player) {

        // Iterate over each row.
        for (int row = 0; row < this.row; row++) {
            boolean rowAllSame = true;
            // Iterate over each column in current row.
            for (int col = 0; col < this.col; col++) {
                if (gameBoard[row][col] != player) {
                    rowAllSame = false;
                }
            }
            if (rowAllSame) {
                return true;
            }
        }
        return false;
    }

    /*
     * This some-what duplicate method has been left intact as in the future we might want to be
     * able to play games of rectangular (non-square board) tic-tact-toe.
     */
    private boolean checkVertical(int player) {
        // Iterate over each column.
        for (int col = 0; col < this.col; col++) {
            boolean colAllSame = true;
            // Iterate over each row in current column.
            for (int row = 0; row < this.row; row++) {
                if (gameBoard[row][col] != player) {
                    colAllSame = false;
                }
            }
            if (colAllSame) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonal(int player) {

        boolean forwardDiagonalAllSame = true;
        for (int row = 0, col = 0; (row < this.col && col < this.col); row++, col++) {
            if (gameBoard[row][col] != player) {
                forwardDiagonalAllSame = false;
            }
        }

        boolean backwardDiagonalAllSame = true;
        for (int row = 0, col = this.col - 1; (row < this.row && col > -1); row++, col--) {
            if (gameBoard[row][col] != player) {
                backwardDiagonalAllSame = false;
            }
        }
        Log.i("Connect/backward", backwardDiagonalAllSame + "'");
        return forwardDiagonalAllSame || backwardDiagonalAllSame;
    }

    private void resetBoard() {
        gameBoard = new int[row][col];
    }

    void saveData() {
        long durationInSeconds = (System.currentTimeMillis() - startTime) / 1000;
        user.connectStats.incrementTimePlayed(durationInSeconds);
        new DataSaver(this.appContext).saveUser(user, user.getName(), user.getPassword());
    }
}
