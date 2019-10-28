package com.example.game;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class User {



    /**
     * name of this user
     */
    String name;

    /**
     * password of this user
     */
    private String password;

    /**
     * statistics for each game of this user
     */
    HashMap<String, HashMap<String, Integer>> statistics;

    /**
     * customizations for each game of this user
     */
    HashMap<String, HashMap<String, String>> customizations;

    static ArrayList<String> userList = new ArrayList<>();
    private String backgroundColor;
    private String textColor;
    public String language;

    public static boolean validUser(String username) {
        return userList.contains(username);
    }


    /**
     * Constructs a new User
     *
     * @param name the name of this user
     */
    User(String name) {

        // TODO - THis contstructor will only be called by DataLoader. Create new constrcutor for a default user (username, password).
        this.name = name;

        // initialize statistics
        statistics = new HashMap<>();
        HashMap<String, Integer> game1_stats = new HashMap<>();
        HashMap<String, Integer> game2_stats = new HashMap<>();
        HashMap<String, Integer> game3_stats = new HashMap<>();

        game1_stats.put("games_won", 0);
        game1_stats.put("games_played", 0);
        game1_stats.put("time", 0);
        game2_stats.put("games_won", 0);
        game2_stats.put("games_played", 0);
        game2_stats.put("mistakes", 0);
        game3_stats.put("games_won", 0);
        game3_stats.put("games_lost", 0);
        game3_stats.put("games_tied", 0);

        statistics.put("game1", game1_stats);
        statistics.put("game2", game2_stats);
        statistics.put("game3", game3_stats);

        // initialize customizations
        customizations = new HashMap<>();
        HashMap<String, String> game1_custom = new HashMap<>();
        HashMap<String, String> game2_custom = new HashMap<>();
        HashMap<String, String> game3_custom = new HashMap<>();

        game1_custom.put("colour", "#ffffff");
        game1_custom.put("difficulty", "easy");
        game1_custom.put("background", "#ffffff");
        game2_custom.put("colour", "#ffffff");
        game2_custom.put("difficulty", "easy");
        game2_custom.put("background", "#ffffff");
        game3_custom.put("colour", "#ffffff");
        game3_custom.put("difficulty", "easy");
        game3_custom.put("background", "#ffffff");

        customizations.put("game1", game1_custom);
        customizations.put("game2", game2_custom);
        customizations.put("game3", game3_custom);
    }

    /**
     * Sets the statistics of this User for the game <game>
     *
     * @param game      the game the statistics are for
     * @param new_stats the updated statistics for this game
     */
    void set_statistics(String game, HashMap<String, Integer> new_stats) {
        statistics.put(game, new_stats);
    }

    /**
     * Returns the statistics of this User for the game
     *
     * @param game the game the statistics are for
     * @return the statistics of this game
     */
    HashMap<String, Integer> get_statistics(String game) {
        return statistics.get(game);
    }

    /**
     * Sets the customizations of this User for the game <game>
     *
     * @param game   the game the customizations are for
     * @param custom the customizations for <game>
     */
    void set_customizations(String game, HashMap<String, String> custom) {
        customizations.put(game, custom);
    }

    /**
     * Returns the customizations of this user for the game <game>
     *
     * @param game the game the customizations are for
     * @return the customizations for <game>
     */
    HashMap<String, String> get_customizations(String game) {
        return customizations.get(game);
    }

    /**
     * Sets the password of this User
     *
     * @param password the password of this User
     */
    void setPassword(String password) {
        this.password = password;
    }

    void setBackgroundColor(String backgroundColorHexa) {
        this.backgroundColor = backgroundColorHexa;
    }

    void setTextColor(String textColorHexa) {
        this.textColor = textColorHexa;
    }

    void setLanguage(String language) {
        this.language = language;
    }

    boolean authenticateUser(String enteredUsername, String enteredPassword) {
        return this.name.equals(enteredUsername) && this.password.equals(enteredPassword);
    }
}
