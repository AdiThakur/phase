package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.graphics.drawable.TransitionDrawable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();
        runTransition();
    }

    public void runTransition() {
        View view = findViewById(R.id.background);
        TransitionDrawable transition = (TransitionDrawable) view.getBackground();
        transition.startTransition(3000);
        transition.reverseTransition(3000);
    }

    public void logIn(View view) {
        Intent intent = new Intent(getApplicationContext(), logIn.class);
        startActivity(intent);
    }

    public void signUp(View view) {
        Intent intent = new Intent(getApplicationContext(), signUp.class);
        startActivity(intent);
    }
}