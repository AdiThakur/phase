package com.example.game;

public class MatchStats extends GameStats {

    private int totalMistakes;

    MatchStats(int gamesPlayed, long timePlayed, int totalMistakes) {
        super(gamesPlayed, timePlayed);
        this.totalMistakes = totalMistakes;
    }

    public int getTotalMistakes() {
        return totalMistakes;
    }

    public void incrementTotalMistakes() {
        this.totalMistakes++;
    }

}
