package com.example.game;

public class ConnectStats extends GameStats {

    private int gamesWon;

    /**
     * Constructor of ConnectStats.
     *
     * @param gamesPlayed number of games played
     * @param timePlayed amount of time played
     * @param gamesWon number of games won
     */
    ConnectStats(int gamesPlayed, long timePlayed, int gamesWon) {
        super(gamesPlayed, timePlayed);
        this.gamesWon = gamesWon;

    }

    /**
     * Get the number of games won
     *
     * @return number of games won
     */
    public int getGamesWon() {
        return gamesWon;
    }

    /**
     * Increment number of games won by 1
     */
    public void incrementGamesWon() {
        this.gamesWon++;
    }
}
