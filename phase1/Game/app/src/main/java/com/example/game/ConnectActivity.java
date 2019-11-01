package com.example.game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class ConnectActivity extends AppCompatActivity {

    private LinearLayout linearLayout;

    private final String TAG = "Connect";
    private Connect connectBoard;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        linearLayout = findViewById(R.id.parent);

        Log.i(TAG, "Stared");

        Intent intent = getIntent();
        userName = intent.getStringExtra("user");
        connectBoard = new Connect(userName, this);
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

            if (resultOfMove == null) {
                raiseToast("Tie game!");
                clearBoard();
            } else if (resultOfMove.equals("P")) {
                raiseToast(userName + " wins!");
                clearBoard();
            } else if (resultOfMove.equals("C")) {
                raiseToast("Computer wins!");
                clearBoard();
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateComputerMove(resultOfMove);
                    }
                }, 500);
            }
        } else {
            raiseToast("Invalid move!");
        }
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
    }

    private void raiseToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
