package com.example.game;

abstract class GameStats {

    private int gamesPlayed;
    private long timePlayed;

    /**
     * Constructor of GameStats.
     *
     * @param gamesPlayed number of games played
     * @param timePlayed amount of time played
     */
    GameStats(int gamesPlayed, long timePlayed) {
        this.gamesPlayed = gamesPlayed;
        this.timePlayed = timePlayed;
    }

    /**
     * Increments the number of games played.
     */
    void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    /**
     * Get the number of games played.
     *
     * @return the number of games played
     */
    int getGamesPlayed() {
        return this.gamesPlayed;
    }

    /**
     * Increment the total amount of time played.
     *
     * @param timePlayed the current time played
     */
    void incrementTimePlayed(long timePlayed) {
        this.timePlayed += timePlayed;
    }

    /**
     * Get the total amount of time played.
     *
     * @return the amount of time played
     */
    long getTimePlayed() {
        return this.timePlayed;
    }


}
