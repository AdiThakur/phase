package com.example.game;

public class GuessStats extends GameStats {

    private int longestStreak;

    GuessStats(int gamesPlayed, long timePlayed, int longestStreak) {
        super(gamesPlayed, timePlayed);
        this.longestStreak = longestStreak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }
}
