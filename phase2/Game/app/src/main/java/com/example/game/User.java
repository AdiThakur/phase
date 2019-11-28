package com.example.game;

/**
 * User class.
 */
class User {

    private String name;
    private String password;
    private String backgroundColor;
    private String textColor;
    private String language;
    private String lastGame;

    ConnectStats connectStats;
    MatchStats matchStats;
    GuessStats guessStats;

    /**
     * Creates a user to be called by DataLoader.
     * @param name the name of this user
     */
    User(String name) {
        // This constructor will only be called by DataLoader.
        // Create new constructor for a default user (username, password).
        this.name = name;
    }

    String getLastGame() {
        return lastGame;
    }

    void setLastGame(String lastGame) {
        this.lastGame = lastGame;
    }

    /**
     * Getter for user's name.
     * @return user's name
     */
    String getName() {
        return this.name;
    }

    /**
     * Setter for user's password.
     * @param password user's password
     */
    void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for user's password.
     * @return user's password
     */
    String getPassword() {
        return this.password;
    }

    /**
     * Setter for backgroundColor.
     * @param backgroundColorHexa user's preferred background colour
     */
    void setBackgroundColor(String backgroundColorHexa) {
        this.backgroundColor = backgroundColorHexa;
    }

    /**
     * Getter for backgroundColor.
     * @return user's preferred background colour
     */
    String getBackgroundColor() {
        return this.backgroundColor;
    }

    /**
     * Setter for textColor.
     * @param textColorHexa user's preferred text colour
     */
    void setTextColor(String textColorHexa) {
        this.textColor = textColorHexa;
    }

    /**
     * Getter for textColor.
     * @return user's preferred text colour
     */
    String getTextColor() {
        return this.textColor;
    }

    /**
     * Setter for language.
     * @param language user's preferred language
     */
    void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Getter for language.
     * @return user's preferred language
     */
    String getLanguage() {
        return this.language;
    }

    /**
     * Checks if the user is valid.
     * @param enteredUsername given username
     * @param enteredPassword given password
     * @return if the user is valid
     */
    boolean authenticateUser(String enteredUsername, String enteredPassword) {
        return this.name.equals(enteredUsername) && this.password.equals(enteredPassword);
    }

    /**
     * Initialize stats for connect.
     * @param gamesPlayed number of games played
     * @param timePlayed time played
     * @param gamesWon number of games won
     */
    void initializeConnectStats(int gamesPlayed, long timePlayed, int gamesWon) {
        this.connectStats = new ConnectStats(gamesPlayed, timePlayed, gamesWon);
    }

    /**
     * Initialize stats for match.
     * @param gamesPlayed number of games played
     * @param timePlayed time played
     * @param totalMistakes number of mistakes made
     */
    void initializeMatchStats(int gamesPlayed, long timePlayed, int totalMistakes) {
        this.matchStats = new MatchStats(gamesPlayed, timePlayed, totalMistakes);
    }

    /**
     * Initialize stats for guess.
     * @param gamesPlayed number of games played
     * @param timePlayed time played
     * @param streaks user's longest streak of correct guesses
     */
    void initializeGuessStats(int gamesPlayed, long timePlayed, int streaks) {
        this.guessStats = new GuessStats(gamesPlayed, timePlayed, streaks);
    }
}
