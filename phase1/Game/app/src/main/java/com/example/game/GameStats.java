package com.example.game;

abstract class GameStats {

    private int gamesPlayed;
    private long timePlayed;

    GameStats(int gamesPlayed, long timePlayed) {
        this.gamesPlayed = gamesPlayed;
        this.timePlayed = timePlayed;
    }

    void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    int getGamesPlayed() {
        return this.gamesPlayed;
    }

    void incrementTimePlayed(long timePlayed) {
        this.timePlayed += timePlayed;
    }

    long getTimePlayed() {
        return this.timePlayed;
    }


}
