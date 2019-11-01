package com.example.game;

public class ConnectStats extends GameStats {

    private int gamesWon;

    ConnectStats(int gamesPlayed, long timePlayed, int gamesWon) {
        super(gamesPlayed, timePlayed);
        this.gamesWon = gamesWon;

    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void incrementGamesWon() {
        this.gamesWon++;
    }
}
