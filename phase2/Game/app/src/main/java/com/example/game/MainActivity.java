package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.graphics.drawable.TransitionDrawable;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    /**
     * Initializes activity.
     * @param savedInstanceState saved instance state (mull on first run)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(this.getSupportActionBar()).hide();
        runTransition();
    }

    public void runTransition() {
        View view = findViewById(R.id.background);
        TransitionDrawable transition = (TransitionDrawable) view.getBackground();
        transition.startTransition(3000);
        transition.reverseTransition(3000);
    }

    /**
     * Starts the LogInView activity.
     * @param view standard view parameter
     */
    public void logIn(View view) {
        Intent intent = new Intent(getApplicationContext(), LogInView.class);
        startActivity(intent);
    }

    /**
     * Starts the SignUpView activity.
     * @param view standard view parameter
     */
    public void signUp(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUpView.class);
        startActivity(intent);
    }
}

// TODO - MVP architecture Connect, Guess, Log-In, Sign-Up
// TODO - Customizations - Background color, Music

// TODO - Make gameSelection and Scoreboard better looking

// TODO - Update UML diagrams
// TODO - Update the readme file.

// TODO - Fix issues in Connect avatar things