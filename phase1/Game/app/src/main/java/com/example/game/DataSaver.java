package com.example.game;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class DataSaver {

    private final String TAG = "DataSaver";
    private final Context appContext;

    DataSaver(Context appContext) {
        this.appContext = appContext;
    }

    boolean saveUser(User user, String userName) {

        String stringToSave;
        String fileName = userName + ".txt";
        PrintWriter out;

        if (user == null) {
            stringToSave = defaultDataToString(userName);
        } else {
            stringToSave = userDataToString(user);
        }

        try {
            OutputStream outStream = this.appContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            out = new PrintWriter(outStream);
            out.print(stringToSave);
            out.close();
            Log.i(TAG, "File saved in" + this.appContext.getFilesDir());
            return true;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Error encountered trying to open file for writing: " + fileName);
            return false;
        }
    }

    // TODO user DataSaver.saveUser to MODIFY the SHIT out of signUp

    private String defaultDataToString(String userName) {
        return "Finish this method ploz.";
    }

    private String userDataToString(User user) {

        StringBuilder output = new StringBuilder();
        output.append(user.getName());
        output.append("\n");
        output.append(user.getPassword());
        output.append("\n");
        output.append("green"); // BG color.
        output.append("\n");
        output.append("white"); // Text color
        output.append("\n");
        output.append("english"); // Language
        output.append("\n");
        output.append("0, 0, 0"); // Tic
        output.append("\n");
        output.append("0, 0, 0"); // Match
        output.append("\n");
        output.append("0, 0, 0"); // Higher or Lower
        output.append("\n");

        return output.toString();
    }
}
