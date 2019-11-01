package com.example.game;

import android.content.Context;

public class Game {

    protected Context appContext;
    protected User user;
    protected long startTime;

    protected Game(String userName, Context appContext) {
        this.appContext = appContext;
        this.user = new DataLoader(appContext).loadUser(userName);
        this.startTime = System.currentTimeMillis();
    }

}
