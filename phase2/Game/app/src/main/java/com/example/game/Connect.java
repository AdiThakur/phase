package com.example.game;

import android.content.Context;

class Connect extends  Game {

    private static final String gameName = "Connect";

    private final int PLAYER_1 = 1;
    private final int COMPUTER = 2;

    private int row ;
    private int col;
    private int moves;
    private int difficulty;

    private int[][] gameBoard;
    private ConnectAlgo computerAlgo;

    /**
     * Constructor of Connect.
     *
     * @param userName
     * @param appContext
     */
    Connect(String userName, Context appContext, int gridSize, int difficulty) {

        super(userName, appContext, gameName);
        this.gameBoard = new int[gridSize][gridSize];
        this.row = gridSize;
        this.col = gridSize;
        this.moves = 0;
        this.difficulty = difficulty;

        setDifficulty();
    }

    private void setDifficulty( ) {

        if (difficulty == 3) {
            computerAlgo = new ConnectAlgoStrategy();
        } else if (difficulty == 2) {
            computerAlgo = new ConnectAlgoBlock();
        } else {
            computerAlgo = new ConnectAlgoRandom();
        }
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
     * @return a message of who won the game, and the computer's last move
     */
    String playerMove(int x, int y) {

        // Player's move.
        gameBoard[x][y] = PLAYER_1;
        incrementMoves();
        if (checkGameWon(PLAYER_1, gameBoard)) {
            user.connectStats.incrementGamesWon();
            score = difficulty * 10;
            return user.getName() + " wins!";
        }
        // Computer's move.
        String computerMove = computerAlgo.computerMove(gameBoard);
        incrementMoves();
        if (checkGameWon(COMPUTER, gameBoard)) {
            score = difficulty;
            return computerMove + ", Computer wins!";
        }
        // Tie game.
        if (computerMove == null || moves >= row*col) {
            score = difficulty * 5;
            return computerMove + ", Tie game!";
        } else {
            return computerMove;
        }
    }

    /**
     * Check if game is won.
     *
     * @param player the user that is playing the game
     * @return if the player won the game
     */
    private boolean checkGameWon(int player, int[][] gameBoard) {

        return checkHorizontal(player, gameBoard) || checkVertical(player, gameBoard) || checkDiagonal(player, gameBoard);
    }

    /**
     * Check if player has a horizontal win.
     *
     * @param player the current player
     * @return whether the player won or not
     */
    private boolean checkHorizontal(int player, int[][] gameBoard) {

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
    private boolean checkVertical(int player, int[][] gameBoard) {

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
    private boolean checkDiagonal(int player, int[][] gameBoard) {

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

    private void incrementMoves(){
        moves++;
    }

    /**
     * Resets the board.
     */
    void resetBoard() {
        gameBoard = new int[row][col];
        moves = 0;
        user.connectStats.incrementGamesPlayed();
    }

    private class ConnectAlgoRandom implements ConnectAlgo {

        @Override
        public String computerMove(int[][] gameBoard) {

//            Random random = new Random();
//            int x, y;
//            boolean madeMove = false;
//            String computerMove = null;
//
//            while (!madeMove) {
//
//                x = random.nextInt(gameBoard.length);
//                y = random.nextInt(gameBoard.length);
//
//                if (gameBoard[x][y] == 0) {
//                    gameBoard[x][y] = COMPUTER;
//                    computerMove = "" + x + "" + y;
//                    madeMove = true;
//                }
//            }
//            return computerMove;
//        }

            int xMove = 0, yMove = 0;
            boolean madeMove = false;

            Outer:
            for (int x = 0; x < row; x++) {
                for (int y = 0; y < col; y++) {
                    if (gameBoard[x][y] == 0) {
                        gameBoard[x][y] = COMPUTER;
                        xMove = x;
                        yMove = y;
                        madeMove = true;
                        break Outer;
                    }
                }
            }
            if (madeMove) {
                return xMove + "" + yMove;
            } else {
                return null;
            }

        }
    }

    private class ConnectAlgoBlock implements ConnectAlgo {

        @Override
        public String computerMove(int [][] gameBoard) {

            for (int row = 0; row < gameBoard.length; row++) {
                for (int col = 0; col < gameBoard[row].length; col++) {

                    if (gameBoard[row][col] == 0) {
                        // If player could win the game at this position, computer will block.
                        gameBoard[row][col] = PLAYER_1;
                        if (checkGameWon(PLAYER_1, gameBoard)) {
                            gameBoard[row][col] = COMPUTER;
                            return "" + row + "" + col;
                        } else {
                            gameBoard[row][col] = 0;
                        }
                    }
                }
            }

            // No blocking to be done yet.
            ConnectAlgoRandom temp = new ConnectAlgoRandom();
            return temp.computerMove(gameBoard);
        }
    }

    private class ConnectAlgoStrategy implements ConnectAlgo {

        @Override
        public String computerMove(int[][] gameBoard) {

            int maxWins = 0;
            int maxWinsX = -1, maxWinsY = -1;
            int maxAdjacentTokens = 0;

            int[] winInfoAtCurrPos;
            int[][] copyBoard = copyGameBoard(gameBoard);

            Outer:
            for (int x = 0; x < gameBoard.length; x++) {
                for (int y = 0; y < gameBoard[x].length; y++) {
                    if (gameBoard[x][y] == 0) {

                        // Checking if Computer could win the game by placing here.
                        copyBoard[x][y] = COMPUTER;
                        if (checkGameWon(COMPUTER, copyBoard)) {
                            maxWinsX = x;
                            maxWinsY = y;
                            break Outer;
                        } else {
                            copyBoard[x][y] = 0;
                        }
                        // Checking if Player could win the game by placing here.
                        copyBoard[x][y] = PLAYER_1;
                        if (checkGameWon(PLAYER_1, copyBoard)) {
                            maxWinsX = x;
                            maxWinsY = y;
                            break Outer;
                        } else {
                            copyBoard[x][y] = 0;
                        }
                        winInfoAtCurrPos = calculateTotalWins(x, y);
                        // If current position has higher potential to lead to a winning move.
                        if (winInfoAtCurrPos[0] >= maxWins && winInfoAtCurrPos[1] >= maxAdjacentTokens) {
                            maxWins = winInfoAtCurrPos[0];
                            maxAdjacentTokens = winInfoAtCurrPos[1];
                            maxWinsX = x;
                            maxWinsY = y;
                        }
                    }
                }
            }
            // Playing the calculated move.
            gameBoard[maxWinsX][maxWinsY] = COMPUTER;
            return maxWinsX + "" + maxWinsY;
        }

        private int[] calculateTotalWins(int xRoot, int yRoot) {

            int adjacentTokens;
            adjacentTokens = calculateAdjacentTokens(xRoot, yRoot);

            int totalWins = 0;
            // Checking if horizontal win is possible.
            boolean allHoriSame = true;
            for (int x = 0; x < row; x++) {
                if (gameBoard[x][yRoot] == PLAYER_1) {
                    allHoriSame = false;
                }
            }
            if (allHoriSame) {totalWins++;}
            // Checking if vertical win is possible.
            boolean allVertSame = true;
            for (int y = 0; y < col; y++) {
                if (gameBoard[xRoot][y] == PLAYER_1) {
                    allVertSame = false;
                }
            }
            if (allVertSame) {totalWins++;}

            // Checking if diagonal win is possible.
            boolean forwardDiagonalAllSame = true;
            int consecutiveCounter = 0;
            for (int x = -row, y = -col; (x < row && y < col); x++, y++) {
                if (0 <= xRoot + x  && xRoot + x < row && 0 <= yRoot + y  && yRoot + y < col ) {
                    if (gameBoard[xRoot + x][yRoot + y] == PLAYER_1) {
                        forwardDiagonalAllSame = false;
                    }
                    consecutiveCounter++;
                }
            }
            if (forwardDiagonalAllSame && consecutiveCounter == row) {
                totalWins++;
            }

            consecutiveCounter = 0;
            boolean backwardDiagonalAllSame = true;
            for (int x = -row, y = col; (x < row && y > -col); x++, y--) {
                if (0 <= xRoot + x  && xRoot + x < row && 0 <= yRoot + y  && yRoot + y < col ) {
                    if (gameBoard[xRoot + x][yRoot + y] == PLAYER_1) {
                        backwardDiagonalAllSame = false;
                    }
                    consecutiveCounter++;
                }
            }
            if (backwardDiagonalAllSame && consecutiveCounter == row) {
                totalWins++;
            }

            return  new int[] {totalWins, adjacentTokens};
        }

        private int calculateAdjacentTokens(int xRoot, int yRoot) {

            int adjacentTokens = 0;
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (0 <= xRoot + i && xRoot + i < row && 0 <= yRoot + j  && yRoot + j < col ) {
                        if (gameBoard[xRoot + i][yRoot + j] == COMPUTER) {
                            adjacentTokens++;
                        }
                    }
                }
            }
            return adjacentTokens;
        }
    }

    private int[][] copyGameBoard(int [][] gameBoard) {

        int[][] copyBoard = new int[row][col];
        for (int i = 0; i < gameBoard.length; i++) {
            copyBoard[i] = gameBoard[i].clone();
        }
        return copyBoard;
    }

    interface  ConnectAlgo {
        String computerMove(int[][] board);
    }
}

