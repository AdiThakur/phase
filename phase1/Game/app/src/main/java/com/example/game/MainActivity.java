package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSupportActionBar().hide();

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
