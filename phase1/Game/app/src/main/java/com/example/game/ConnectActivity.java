package com.example.game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ConnectActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private Chronometer chronometer;

    private Connect connectBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        linearLayout = findViewById(R.id.parent);
        chronometer = findViewById(R.id.chronometer);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("user");
        connectBoard = new Connect(userName, this);

        startClock();
    }

    public void imageClicked(View view) {

        ImageView imageClicked = (ImageView) view;
        String tag = imageClicked.getTag().toString();
        // No risk in parsing here as we have complete control over tag: It is always an int.
        int x = Integer.parseInt(tag.charAt(0) + "");
        int y = Integer.parseInt(tag.charAt(1) + "");

        // Asking connectBoard if this is a valid move.
        if (connectBoard.validMove(x, y)) {

            imageClicked.setImageResource(R.drawable.x);
            imageClicked.setAlpha(1f);
            final String resultOfMove = connectBoard.playerMove(x, y);

            // This move did not result in the game ending.
            if (resultOfMove != null & resultOfMove.length() == 2) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateComputerMove(resultOfMove);
                    }
                }, 500);
            } else {
                displayWinner(resultOfMove);
            }
        } else {
            raiseToast("Invalid move!");
        }
    }

    private void displayWinner(String winningMsg) {
        chronometer.stop();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(winningMsg);
        builder.setMessage("Do you want to play again?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                connectBoard.saveData();
                finish();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearBoard();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateComputerMove(String resultOfMove) {

        for (int row = 0; row < linearLayout.getChildCount(); row++) {
            LinearLayout childLinearLayout = (LinearLayout) linearLayout.getChildAt(row);
            for (int col = 0; col < childLinearLayout.getChildCount(); col++) {
                ImageView imageView = (ImageView) childLinearLayout.getChildAt(col);
                if (imageView.getTag().toString().equals(resultOfMove)) {
                    imageView.setImageResource(R.drawable.o);
                    imageView.setAlpha(1f);
                }
            }
        }
    }

    private void clearBoard() {

        for (int row = 0; row < linearLayout.getChildCount(); row++) {
            LinearLayout childLinearLayout = (LinearLayout) linearLayout.getChildAt(row);
            for (int col = 0; col < childLinearLayout.getChildCount(); col++) {
                ImageView imageView = (ImageView) childLinearLayout.getChildAt(col);
                imageView.setImageResource(R.drawable.grey);
                imageView.setAlpha(0.5f);
            }
        }
        startClock();
    }

    private void startClock() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    private void raiseToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {

        chronometer.stop();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                chronometer.setBase(chronometer.getBase());
                chronometer.start();
            }
        });
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ConnectActivity.super.onBackPressed();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        connectBoard.saveData();
        super.onDestroy();
    }
}
