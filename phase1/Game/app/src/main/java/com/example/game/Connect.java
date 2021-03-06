package com.example.game;

import android.content.Context;

class Connect extends  Game {

    private static final String gameName = "Connect";

    private final int PLAYER_1 = 1;
    private final int COMPUTER = 2;
    private final int row = 3;
    private final int col = 3;

    private int[][] gameBoard;

    /**
     * Constructor of Connect.
     *
     * @param userName
     * @param appContext
     */
    Connect(String userName, Context appContext) {

        super(userName, appContext, gameName);
        this.gameBoard = new int[row][col];
    }

    /**
     * Check if x and y are valid coordinates.
     *
     * @param x x-coordinate of the board
     * @param y y-coordinate of the board
     * @return whether if it is a valid move
     */
    boolean validMove(int x, int y) {

        if (gameBoard[x][y] == 0) {
            gameBoard[x][y] = PLAYER_1;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Handles the player move function.
     *
     * @param x x-coordinate of the board
     * @param y y-coordinate of the board
     * @return a message of who won the game
     */
    String playerMove(int x, int y) {

        gameBoard[x][y] = PLAYER_1;
        if (checkGameWon(PLAYER_1)) {
            resetBoard();
            user.connectStats.incrementGamesWon();
            return user.getName() + " wins!";
        }
        String computerMove = computerMove();
        if (checkGameWon(COMPUTER)) {
            resetBoard();
            return "Computer wins!";
        }
        if (computerMove == null) {
            return "Tie game!";
        } else {
            return computerMove;
        }
    }

    /**
     * Handles the computer's move.
     *
     * @return the computer's move
     */
    private String computerMove() {
        // Very basic (literally random) AI. Will be methodical in Phase 2.
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

    /**
     * Check if game is won.
     *
     * @param player the user that is playing the game
     * @return if the player won the game
     */
    private boolean checkGameWon(int player) {

        return checkHorizontal(player) || checkVertical(player) || checkDiagonal(player);
    }

    /**
     * Check if player has a horizontal win.
     *
     * @param player the current player
     * @return whether the player won or not
     */
    private boolean checkHorizontal(int player) {

        for (int row = 0; row < this.row; row++) {
            boolean rowAllSame = true;
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

    /**
     * Check if player has a vertical win.
     *
     * @param player the current player
     * @return whether the player won or not
     */
    /*
     * This some-what duplicate method has been left intact as in the future we might want to be
     * able to play games of rectangular (non-square board) tic-tact-toe.
     */
    private boolean checkVertical(int player) {

        for (int col = 0; col < this.col; col++) {
            boolean colAllSame = true;
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

    /**
     * Check if player has a diagonal win.
     *
     * @param player the current player
     * @return whether the player won or not
     */
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
        return forwardDiagonalAllSame || backwardDiagonalAllSame;
    }

    /**
     * Resets the board.
     */
    private void resetBoard() {
        gameBoard = new int[row][col];
        user.connectStats.incrementGamesPlayed();
    }

}
