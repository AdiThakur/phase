package com.example.game;

class User {

    private String name;
    private String password;
    private String backgroundColor;
    private String textColor;
    private String language;

    ConnectStats connectStats;
    MatchStats matchStats;
    GuessStats guessStats;


    User(String name) {

        // TODO - THis constructor will only be called by DataLoader. Create new constructor for a default user (username, password).
        this.name = name;
    }

    String getName() {
        return this.name;
    }

    void setPassword(String password) {
        this.password = password;
    }

    String getPassword() {
        return this.password;
    }

    void setBackgroundColor(String backgroundColorHexa) {
        this.backgroundColor = backgroundColorHexa;
    }

    String getBackgroundColor() {
        return this.backgroundColor;
    }

    void setTextColor(String textColorHexa) {
        this.textColor = textColorHexa;
    }

    String getTextColor() {
        return this.textColor;
    }

    void setLanguage(String language) {
        this.language = language;
    }

    String getLanguage() {
        return this.language;
    }

    boolean authenticateUser(String enteredUsername, String enteredPassword) {
        return this.name.equals(enteredUsername) && this.password.equals(enteredPassword);
    }

    void initializeConnectStats(int gamesPlayed, long timePlayed, int gamesWon) {
        this.connectStats = new ConnectStats(gamesPlayed, timePlayed, gamesWon);
    }

    void initializeMatchStats(int gamesPlayed, long timePlayed, int totalMistakes) {
        this.matchStats = new MatchStats(gamesPlayed, timePlayed, totalMistakes);
    }

    void initializeGuessStats(int gamesPlayed, long timePlayed, int streaks) {
        this.guessStats = new GuessStats(gamesPlayed, timePlayed, streaks);
    }
}
