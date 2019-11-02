package com.example.game;

class GuessStats extends GameStats {

    private int longestStreak;

    GuessStats(int gamesPlayed, long timePlayed, int longestStreak) {
        super(gamesPlayed, timePlayed);
        this.longestStreak = longestStreak;
    }

    int getLongestStreak() {
        return longestStreak;
    }

    void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }
}
