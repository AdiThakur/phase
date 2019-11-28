package com.example.game;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class ConnectActivity extends AppCompatActivity {

    private TableLayout tableLayout;
    private Chronometer chronometer;
    private String userName;

    private Connect connectGame;
    private int playerModel;
    private int aiModel;

    /**
     * Create Connect game when user starts the game.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        this.getSupportActionBar().hide();

        Log.i("Connect/Tie", "Startup");

        chronometer = findViewById(R.id.chronometer);

        Intent intent = getIntent();
        userName = getIntent().getStringExtra("user");
        int gridSize = intent.getIntExtra("gridSize", 3);
        int aiLevel = intent.getIntExtra("aiLevel", 1);

        connectGame = new Connect(userName, this, gridSize, aiLevel);
        playerModel = intent.getIntExtra("playerModel", R.drawable.x);
        aiModel = intent.getIntExtra("aiModel", R.drawable.o);

        displayAppropriateGrid(gridSize);
        startClock();
    }

    private void displayAppropriateGrid(int gridSize) {

        int layoutId;
        int tableId;

        if (gridSize == 3) {
            layoutId = R.id.connect3Layout;
            tableId = R.id.connect3Table;
        } else if (gridSize == 4) {
            layoutId = R.id.connect4Layout;
            tableId = R.id.connect4Table;
        } else {
            layoutId = R.id.connect5Layout;
            tableId = R.id.connect5Table;
        }
        findViewById(layoutId).setVisibility(View.VISIBLE);
        tableLayout = findViewById(tableId);
    }

    /**
     *
     *
     * @param view
     */
    public void registerMove(View view) {

        ImageView imageClicked = (ImageView) view;
        String tag = imageClicked.getTag().toString();
        // No risk in parsing here as we have complete control over tag: It is always an int.
        int x = Integer.parseInt(tag.charAt(0) + "");
        int y = Integer.parseInt(tag.charAt(1) + "");

        if (connectGame.validMove(x, y)) {

            imageClicked.setImageResource(playerModel);
            // Problem in connectGame.playerMove() - All strats!
            final String resultOfMove = connectGame.playerMove(x, y);

            // This move did not result in the game ending.
            if (resultOfMove != null & resultOfMove.length() == 2) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        displayComputerMove(resultOfMove);
                    }
                }, 300);
                // If the result of the last move causes game to end.
            } else {
                displayWinningMove(resultOfMove);
            }
        } else {
            raiseToast("Invalid move!");
        }
    }

    private void displayWinningMove(String resultOfMove) {

        // winningMoveInfo consists of the x-y coordinates of winning move, and winning message.
        String[] winningMoveInfo = resultOfMove.split(",");

        // If user wins, no info about computer's last move is returned.
        if (winningMoveInfo.length == 1) {
            String winningMessage = winningMoveInfo[0];
            displayAndSaveScore(winningMessage);
        } else {
            final String computerFinalMove = winningMoveInfo[0];
            final String winningMessage = winningMoveInfo[1];
            // Displaying computer's winning move with a small delay.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    displayComputerMove(computerFinalMove);
                }
            }, 300);
            // Launching the activity that displays and asks to save score with a delay.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    displayAndSaveScore(winningMessage);
                }
            }, 400);
        }
    }

    private void displayAndSaveScore(String winningMessage) {

        Intent intent = new Intent(this, DisplayScoreboard.class);

        intent.putExtra("score", connectGame.getScore());
        intent.putExtra("userName", userName);
        intent.putExtra("gameName", connectGame.getName());
        intent.putExtra("msg", winningMessage);

        startActivityForResult(intent, 1);
    }

    /**
     * Update the computer's move.
     *
     * @param computerMove the result of the move
     */
    private void displayComputerMove(String computerMove) {

        for (int row = 0; row < tableLayout.getChildCount(); row++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(row);
            for (int col = 0; col < tableRow.getChildCount(); col++) {
                ImageView imageView = (ImageView) tableRow.getChildAt(col);
                if (imageView.getTag().toString().equals(computerMove)) {
                    imageView.setImageResource(aiModel);
                }
            }
        }
    }

    /**
     * Clears the board.
     */
    private void clearBoard() {

        for (int row = 0; row < tableLayout.getChildCount(); row++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(row);
            for (int col = 0; col < tableRow.getChildCount(); col++) {
                ImageView imageView = (ImageView) tableRow.getChildAt(col);
                imageView.setImageResource(R.drawable.grey);
            }
        }
        connectGame.resetBoard();
        startClock();
    }

    /**
     * Starts the clock
     */
    private void startClock() {

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    /**
     * Raise a Toast
     *
     * @param msg the message for the Toast
     */
    private void raiseToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Restart
        if (resultCode == 1) {
            clearBoard();
            // Quit game
        } else if (resultCode == 2) {
            finish();
        }
    }

    /**
     * Handles the back button function.
     */
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

    /**
     * Handles the destroy function.
     */
    @Override
    protected void onDestroy() {
        connectGame.saveUserData();
        super.onDestroy();
    }
}
