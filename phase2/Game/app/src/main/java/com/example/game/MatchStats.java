package com.example.game;

class MatchStats extends GameStats {

    private int totalMistakes;

    /**
     * Initializes the stats for the matching game.
     * @param gamesPlayed number of games played
     * @param timePlayed time played
     * @param totalMistakes number of mistakes made
     */
    MatchStats(int gamesPlayed, long timePlayed, int totalMistakes) {
        super(gamesPlayed, timePlayed);
        this.totalMistakes = totalMistakes;
    }

    /**
     * Getter for number of mistakes.
     * @return number of total mistakes
     */
    int getTotalMistakes() {
        return totalMistakes;
    }

    /**
     * Called upon making a mistake. Adds one mistake to the overall counter.
     */
    void incrementTotalMistakes() {
        this.totalMistakes++;
    }
}
