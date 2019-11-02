package com.example.game;

class MatchStats extends GameStats {

    private int totalMistakes;

    MatchStats(int gamesPlayed, long timePlayed, int totalMistakes) {
        super(gamesPlayed, timePlayed);
        this.totalMistakes = totalMistakes;
    }

    int getTotalMistakes() {
        return totalMistakes;
    }

    void incrementTotalMistakes() {
        this.totalMistakes++;
    }

}
