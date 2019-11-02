package com.example.game;

class ConnectStats extends GameStats {

    private int gamesWon;

    ConnectStats(int gamesPlayed, long timePlayed, int gamesWon) {
        super(gamesPlayed, timePlayed);
        this.gamesWon = gamesWon;

    }

    int getGamesWon() {
        return gamesWon;
    }

    void incrementGamesWon() {
        this.gamesWon++;
    }
}
