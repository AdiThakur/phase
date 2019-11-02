package com.example.game;

public class GuessStats extends GameStats {

    private int longestStreak;

    /**
     * Constructor for GuessStats.
     *
     * @param gamesPlayed number of games played
     * @param timePlayed total time played
     * @param longestStreak the longest streak User ever gotten
     */
    GuessStats(int gamesPlayed, long timePlayed, int longestStreak) {
        super(gamesPlayed, timePlayed);
        this.longestStreak = longestStreak;
    }

    /**
     * Get longest streak of User.
     *
     * @return the longest streak
     */
    public int getLongestStreak() {
        return longestStreak;
    }

    /**
     * Set longest streak of User.
     *
     * @param longestStreak the new longest streak
     */
    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }
}
