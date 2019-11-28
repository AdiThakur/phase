package com.example.game;

import android.content.Context;

public class Game {

    private Context appContext;
    protected User user;
    private long startTime;
    private String gameName;
    protected int score;

    private GameStats stats;
    private Scoreboard scoreboard;

    /**
     * Constructor for Game.
     *
     * @param userName the user's name
     * @param appContext the context of the class
     * @param gameName the name of the game
     */
    Game(String userName, Context appContext, String gameName) {

        this.appContext = appContext;
        this.gameName = gameName;

        DataLoader dataLoader = new DataLoader(appContext);
        user = dataLoader.loadUser(userName);
        scoreboard = dataLoader.loadScoreBoard(gameName);

        initializeStats();

        startTime = System.currentTimeMillis();
        score = 0;
    }

    private void initializeStats() {

        if (gameName.equalsIgnoreCase("Connect")) {
            stats = user.connectStats;
        } else if (gameName.equalsIgnoreCase("Match")) {
            stats = user.matchStats;
        } else {
            stats = user.guessStats;
        }
        stats.incrementGamesPlayed();
    }

    int getScore() {
        return score;
    }

    String getName() {
        return gameName;
    }

    /**
     * Saves the game data.
     */
    void saveUserData() {

        long durationInSeconds = (System.currentTimeMillis() - startTime)/1000;
        stats.incrementTimePlayed(durationInSeconds);

        DataSaver dataSaver = new DataSaver(appContext);
        dataSaver.saveUser(user, user.getName(), user.getPassword(), gameName);
    }

    void addScore(String userName, int score) {
        scoreboard.addScore(userName, score);
    }

}
