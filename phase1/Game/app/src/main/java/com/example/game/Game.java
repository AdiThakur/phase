package com.example.game;

import android.content.Context;

public class Game {

    private Context appContext;
    protected User user;
    private long startTime;
    private String gameName;

    Game(String userName, Context appContext, String gameName) {

        this.appContext = appContext;
        this.user = new DataLoader(appContext).loadUser(userName);
        this.startTime = System.currentTimeMillis();
        this.gameName = gameName;

        if (gameName.equalsIgnoreCase("Connect")) {
            user.connectStats.incrementGamesPlayed();
        } else if (gameName.equalsIgnoreCase("Repeat")) {
            user.matchStats.incrementGamesPlayed();
        } else {
            user.guessStats.incrementGamesPlayed();
        }
    }

    void saveData() {

        long durationInSeconds = (System.currentTimeMillis() - startTime)/1000;
        if (gameName.equalsIgnoreCase("Connect")) {
            user.connectStats.incrementTimePlayed(durationInSeconds);
        } else if (gameName.equalsIgnoreCase("Repeat")) {
            user.matchStats.incrementTimePlayed(durationInSeconds);
        } else {
            user.guessStats.incrementTimePlayed(durationInSeconds);
        }
        new DataSaver(appContext).saveUser(user, user.getName(), user.getPassword(), gameName);
    }

}
