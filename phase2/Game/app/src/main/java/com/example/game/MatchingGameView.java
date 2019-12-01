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
import android.widget.AdapterView;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

import java.lang.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MatchingGameView extends AppCompatActivity{
    Chronometer chronometer;
    GridView gridView;
    TableLayout layout;
    TextView titleTextView;
    TextView mistakesTextView;

    final static int[] animal_drawable = new int[]{R.drawable.animals_0, R.drawable.animals_1,
            R.drawable.animals_2, R.drawable.animals_3, R.drawable.animals_4, R.drawable.animals_5,
            R.drawable.animals_6, R.drawable.animals_7, R.drawable.animals_8, R.drawable.animals_9};

    final static int[] number_drawable = new int[]{R.drawable.numbers_0, R.drawable.numbers_1,
            R.drawable.numbers_2, R.drawable.numbers_3, R.drawable.numbers_4, R.drawable.numbers_5,
            R.drawable.numbers_6, R.drawable.numbers_7, R.drawable.numbers_8, R.drawable.numbers_9};

    final static int[] fruit_drawable = new int[]{R.drawable.fruits_0, R.drawable.fruits_1,
            R.drawable.fruits_2, R.drawable.fruits_3, R.drawable.fruits_4, R.drawable.fruits_5,
            R.drawable.fruits_6, R.drawable.fruits_7, R.drawable.fruits_8, R.drawable.fruits_9};

    final static HashMap<String, int[]> drawables = new HashMap<>();
    static {
        drawables.put("Animals", animal_drawable);
        drawables.put("Numbers", number_drawable);
        drawables.put("Fruits", fruit_drawable);
    }

    final static HashMap<String, Integer> cardBackMap = new HashMap<>();
    static {
        cardBackMap.put("White", R.drawable.whitequestionmark);
        cardBackMap.put("Blue", R.drawable.bluequestionmark);
        cardBackMap.put("Red", R.drawable.redquestionmark);
    }


    private MatchingPresenter matchingPresenter;
    private String difficulty;
    private String cardBack;
    private String symbols;
    private ImageView card1 = null;
    private ImageView card2 = null;
    private int pairsFound;
    private int numPairs;
    private boolean isBusy = false;
    private int mistakes;
    private String userName;


    private ArrayList<Integer> cards;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_memory_game_view);

        Intent intent = getIntent();
        difficulty = intent.getStringExtra(MatchingConfiguration.GAME_DIFFICULTY);
        cardBack = intent.getStringExtra(MatchingConfiguration.GAME_CARDS);
        symbols = intent.getStringExtra(MatchingConfiguration.GAME_SYMBOLS);
        userName = intent.getStringExtra("user");
        pairsFound = 0;

        cards = start(userName);
        mistakes = matchingPresenter.getMistakes();
        numPairs = matchingPresenter.getDifficulty();

        chronometer = findViewById(R.id.chronometer);
        titleTextView = findViewById(R.id.titleTextView);
        String title = getString(R.string.matching_outof_numpairs, pairsFound, numPairs);
        titleTextView.setText(title);
        mistakesTextView = findViewById(R.id.mistakesTextView);
        String mistakesMade = getString(R.string.mistakes_made, mistakes);
        mistakesTextView.setText(mistakesMade);

        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this, cards.size(), cardBackMap.get(cardBack)));
        gridView.setNumColumns(4);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Log.println(Log.DEBUG, "id/position", v.getId() + "/" + position);
                if (isBusy || v == card1 || v.getTag().equals("Flipped")) {
                    return;
                }
                flip(v);

            }

        });

        startClock();
        setBackgroundColor(R.id.parent);
    }

    private void setBackgroundColor(int layoutId) {

        int colorToSet = matchingPresenter.getBackgroundColor();
        View parent = findViewById(layoutId);
        parent.setBackgroundColor(colorToSet);
    }

    /**
     * Given two cards (ImageViews), use their ids to guess whether or not they match. If they do
     * match then the images displayed on the screen will be the same.
     *
     * @return true if they match, false otherwise
     */
    private boolean guess() {
        int num1 = card1.getId();
        int num2 = card2.getId();
        boolean g = matchingPresenter.guess(num1, num2);
        Log.println(Log.DEBUG, "cards array", cards.toString());
        Log.println(Log.DEBUG, "numPairs", String.valueOf(numPairs));
        if(g) {
            Log.println(Log.DEBUG, "Guessed these two values ", num1 + " == " + num2);
        } else {
            Log.println(Log.DEBUG, "Guessed these two values ", num1 + " != " + num2);
        }


        return g;
    }

    /**
     * Starts the matching game.
     *
     * @param user - the username of the user playing tha game
     * @return an arraylist of integers in random order that represents the pairs
     */
    public ArrayList<Integer>start(String user) {

        matchingPresenter = new MatchingPresenter(user, this, difficulty);
        return matchingPresenter.getDeck();
    }

    /**
     * Flips the chosen view to show what image it corresponds to and if it is the second
     * image to be flipped then no other card can be flipped until the logic for whether or
     * not the two cards matched is done.
     *
     * @param v - the imageview that is being flipped
     */
    private void flip(View v){
        if(card1 == null) {
            card1 = (ImageView) v;
            show(card1);
        } else if (card2 == null) {
            isBusy = true;
            card2 = (ImageView) v;
            show(card2);

            if(guess()) {
                correct();
            } else {
                incorrect();
            }
            isBusy = false;
        }
    }

    /**
     * Shows the drawable image based on the id of the ImageView.
     *
     * @param card - the imageview that is to be shown
     */
    private void show(ImageView card) {
        card.setImageResource(drawables.get(symbols)[cards.get(card.getId()) % numPairs]);
        Log.println(Log.DEBUG, "Image id of the image clicked", String.valueOf(cards.get(card.getId())));
    }

    /**
     * Flips the card back over by setting the image resource to drawable assoicated with
     * cardBack
     */
    private void hide() {
        if(card1 == null || card2 == null) {
            return;
        } else {
            card1.setImageResource(cardBackMap.get(cardBack));
            card2.setImageResource(cardBackMap.get(cardBack));
            card1 = null;
            card2 = null;
        }
    }

    /**
     * Runs only when the two cards match. Sets the tag of the images to flipped so that when they
     * are clicked, flip does not run. Updates the counter representing how many pairs have been
     * found.
     *
     */
    public void correct(){
        pairsFound = matchingPresenter.getPairsFound();
        Toast.makeText(getApplicationContext(),
                "Correct", Toast.LENGTH_SHORT).show();
        String title = getString(R.string.matching_outof_numpairs, pairsFound, numPairs);
        titleTextView.setText(title);
        card1.setTag("Flipped");
        card2.setTag("Flipped");
        card1 = null;
        card2 = null;
        checkGameWon();
    }

    /**
     * Checks whether or not the game is over.
     *
     */
    private void checkGameWon() {
        if (matchingPresenter.checkGameWon()) {
            chronometer.stop();
            displayAndSaveScore("You Won! You made " + matchingPresenter.getMistakes() + " total mistakes.");
        }

    }

    /**
     * Runs when the two cards do not match. After a short delay, the two images are flipped back
     * over and the counter keeping track of mistakes is updated.
     *
     */
    public void incorrect() {
        Toast.makeText(getApplicationContext(),
                "Incorrect", Toast.LENGTH_SHORT).show();
        mistakes = matchingPresenter.getMistakes();
        String mistakesMade = getString(R.string.mistakes_made, mistakes);
        mistakesTextView.setText(mistakesMade);
        final Handler handler = new Handler();

        handler.postDelayed( new Runnable() {
            @Override
            public void run() {
                hide();
            }
        }, 500);
    }

    /**
     * Starts the clock.
     *
     */
    private void startClock() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    /**
     * Runs when the game has been finished. Displays the scoreboard for the matching game.
     *
     * @param winningMessage - the message to be displayed when the game has been won
     */
    private void displayAndSaveScore(String winningMessage) {

        Intent intent = new Intent(this, DisplayScoreboard.class);

        intent.putExtra("score", matchingPresenter.getScore());
        intent.putExtra("gameName", matchingPresenter.getGameName());
        intent.putExtra("msg", winningMessage);

        startActivityForResult(intent, 1);
    }

    /**
     * Asks the user if they really want to exit the game upon pressing the back button.
     */
    @Override
    public void onBackPressed() {

        chronometer.stop();
        final long timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?");

        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MatchingGameView.super.onBackPressed();
            }
        });
        builder.setNegativeButton("Continue", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                chronometer.start();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Saves the users data in the game upon destruction.
     */
    @Override
    protected void onDestroy() {
        matchingPresenter.saveData();
        super.onDestroy();
    }

    /**
     * After the game has finished and the user has chosen either play again or quit in the display
     * scoreboard activity this function runs. If the user chose play again
     *
     * @param requestCode - the request code
     * @param resultCode - the code received from DisplayScoreboard
     * @param data - the intent received from DisplayScoreboard
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Restart
        if (resultCode == 1) {
            Intent intent = new Intent(getApplicationContext(), MatchingGameView.class);
            intent.putExtra("user", userName);
            intent.putExtra(MatchingConfiguration.GAME_DIFFICULTY, difficulty);
            intent.putExtra(MatchingConfiguration.GAME_CARDS, cardBack);
            intent.putExtra(MatchingConfiguration.GAME_SYMBOLS, symbols);
            startActivity(intent);
            finish();
        // Quit game
        } else if (resultCode == 2) {
            Intent intent = new Intent(getApplicationContext(), gameSelection.class);
            intent.putExtra("user", userName);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
    }
}
