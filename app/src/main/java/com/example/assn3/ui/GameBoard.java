/**
 * author: Shivanshu Bansal
 * This is GameBoard.java. This is the main activity code for the game
 * here lies the code that handles the making of the grid, its population, usage of the game logic.
 * The grid instance is created inside the onCreate method so that the grid is updated each time this activity launches.
 */

package com.example.assn3.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.assn3.R;
import com.example.assn3.model.Grid;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameBoard extends AppCompatActivity {

    Grid grid;

    private static int applesFound;
    private static int scansUsed;
    private static int timesPlayed;

    private TextView mFound, mScansUsed, mTimesPlayed;

    MediaPlayer bgMusic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);

        bgMusic = MediaPlayer.create(this, R.raw.marimba_boy);
        bgMusic.setLooping(true);
        bgMusic.setVolume(0.3f, 0.3f);
        bgMusic.start();

        final Vibrator vibe = (Vibrator) GameBoard.this.getSystemService(Context.VIBRATOR_SERVICE);

        grid = Grid.getInstance();

        applesFound = 0;
        scansUsed = 0;
        timesPlayed = grid.getnTimesPlayed();

        // instantiate the buttons matrix
        Button buttons[][] = new Button[grid.getnRows()][grid.getnColumns()];

        // instantiate timesClicked matrix
        int timesClicked[][] = new int[grid.getnRows()][grid.getnColumns()];

        // instantiate displaysScan matrix
        boolean displaysScan[][] = new boolean[grid.getnRows()][grid.getnColumns()];

        // instantiate displaysAppl matrix
        boolean displaysAppl[][] = new boolean[grid.getnRows()][grid.getnColumns()];

        // instantiate hasApple matrix
        boolean hasApple[][] = new boolean[grid.getnRows()][grid.getnColumns()];

        // increment the number of times the game has been played
        incrementTimesPlayed();

        // instantiate TextViews on the activity
        mFound = findViewById(R.id.ggFound);
        mScansUsed = findViewById(R.id.ggScansUsed);
        mTimesPlayed = findViewById(R.id.ggTimesPlayed);

        // display the text for each text view.
        showFound();
        showScansUsed();
        showTimesPlayed();

        // Assign which columns would have an apple randomly
        assignAppleToMatrix(hasApple);

        populateButtons(buttons, timesClicked, hasApple, displaysAppl, displaysScan, vibe);
    }

    // Method to increment the number of times the game has been played
    private void incrementTimesPlayed() {
        timesPlayed++;
        grid.incrementTimesPlayed();
    }

    private void showFound() {
        String display = getString(R.string.found) + ' ' + applesFound + ' ' + getString(R.string.of) + ' ' + grid.getnMines() + ' ' + getString(R.string.mines);
        mFound.setText(display);

    }

    private void showScansUsed() {
        String display = "# " + getString(R.string.scans) + ' ' + getString(R.string.used) + ": " + scansUsed;
        mScansUsed.setText(display);
    }

    private void showTimesPlayed() {
        String display = getString(R.string.timesPlayed) + ": " + timesPlayed;
        mTimesPlayed.setText(display);
    }

    private void assignAppleToMatrix(boolean hasApple[][]) {
        int minesPlaced = 0;
        int numRows = grid.getnRows();
        int numCols = grid.getnColumns();

        while (minesPlaced != grid.getnMines()) {
            Random random = new Random();
            int randmRow = random.nextInt(numRows);
            int randmCol = random.nextInt(numCols);

            if (hasApple[randmRow][randmCol]) {
                continue;
            }

            hasApple[randmRow][randmCol] = true;

            minesPlaced++;
        }
    }

    private void populateButtons(final Button buttons[][], final int timesClicked[][],
                                 final boolean hasApple[][], final boolean displaysAppl[][], final boolean displaysScan[][], final Vibrator vibe) {
        int NUM_ROWS = grid.getnRows();
        int NUM_COLS = grid.getnColumns();

        TableLayout table = findViewById(R.id.ggGrid);

        for (int row = 0; row < NUM_ROWS; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            ));
            table.addView(tableRow);

            for (int col = 0; col < NUM_COLS; col++) {
                final int FINAL_ROW = row;
                final int FINAL_COL = col;

                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                ));

                button.setPadding(0,0,0,0);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gridButtonClicked(FINAL_ROW, FINAL_COL, buttons, timesClicked, hasApple, displaysAppl, displaysScan, vibe);
                        updateScanOnButtons(FINAL_ROW, FINAL_COL, displaysAppl, displaysScan, buttons);
                        if (applesFound == grid.getnMines()) {
                            youWin();
                        }
                    }
                });

                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    private void gridButtonClicked(int row, int col, Button buttons[][], int timesClicked[][],
                                   boolean hasApple[][], boolean displaysAppl[][], boolean displaysScan[][], Vibrator vibe) {
        Button button = buttons[row][col];

        // lock size of the buttons
        lockButtonSizes(buttons);

        // If the button is being clicked for the first time
        // then check if it has the rotten apple
        // if it does, then show the rotten apple
        // else trigger a scan and increment the number of scans used.
        if (timesClicked[row][col] == 0) {
            if (hasApple[row][col]) {
                // Support for vibration and sound
                vibe.vibrate(450);
                playBlob();

                // scale image to button
                int newWidth = button.getWidth();
                int newHeight = button.getHeight();
                Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rotten_apple_dark);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
                Resources resource = getResources();
                button.setBackground(new BitmapDrawable(resource, scaledBitmap));

                // increment the number of mines found.
                applesFound = (applesFound + 1);

                // update the textView to show status on apples found
                showFound();

                displaysAppl[row][col] = true;
            } else {
                // Supoprt for vibration and sound
                vibe.vibrate(90);
                playBlob();

                triggerScan(row, col, hasApple, displaysAppl, buttons);
                displaysScan[row][col] = true;
            }
        }
        // Else if the button clicked is clicked for the second time, then
        // if it has the mine, trigger a scan,
        // else do nothing
        else if (timesClicked[row][col] == 1) {
            if (hasApple[row][col]) {
                // Support for vibration and sound
                vibe.vibrate(90);
                playBlob();

                button.setTextColor(ContextCompat.getColor(GameBoard.this, R.color.white));
                triggerScan(row, col, hasApple, displaysAppl, buttons);
                displaysScan[row][col] = true;
            }
        }

        // Increment the number of times the button has been clicked
        timesClicked[row][col]++;
    }

    private void lockButtonSizes(Button buttons[][]) {
        for (int row = 0; row < grid.getnRows(); row++) {
            for (int col = 0; col < grid.getnColumns(); col++) {
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);

            }
        }
    }

    private void triggerScan(int row, int col, boolean hasApple[][], boolean displaysAppl[][], final Button buttons[][]) {
        int totalScan = 0;
        boolean isScanning = false;

        for (int i = 0; i < grid.getnColumns(); i++) {
            if (hasApple[row][i] && !displaysAppl[row][i]) {
                isScanning = true;
                totalScan++;
            }
        }

        for (int i = 0; i < grid.getnRows(); i++) {
            if (hasApple[i][col] && !displaysAppl[i][col]) {
                totalScan++;
            }
        }

        Button button = buttons[row][col];
        button.setText("" + totalScan);


        // increment the number of scans used
        scansUsed++;

        // update the text view for scans
        showScansUsed();
    }

    // When a button is clicked update the scan on the buttons in same row and column if it has an apple
    private void updateScanOnButtons(int row, int col, boolean displaysAppl[][], boolean displaysScan[][], Button buttons[][]) {
        if (displaysAppl[row][col] && !displaysScan[row][col]) {
            // for rows
            for (int i = 0; i < grid.getnColumns(); i++) {
                if (displaysScan[row][i]) {
                    Button button = buttons[row][i];
                    int temp = Integer.parseInt(button.getText().toString());
                    temp--;
                    button.setText("" + temp);
                }
            }

            // for columns
            for (int i = 0; i < grid.getnRows(); i++) {
                if (displaysScan[i][col]) {
                    Button button = buttons[i][col];
                    int temp = Integer.parseInt(button.getText().toString());
                    temp--;
                    button.setText("" + temp);
                }
            }
        }
    }

    private void youWin() {
        FragmentManager manager = getSupportFragmentManager();
        MessageFragment dialog = new MessageFragment();
        dialog.show(manager, "MessageDialog");

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                bgMusic.stop();
            }
        }, 1000);
    }

    private void playBlob() {
        // plays sound when the game is loaded.
        MediaPlayer mRing = MediaPlayer.create(GameBoard.this, R.raw.blob_sound);
        mRing.start();
    }

    @Override
    public void onBackPressed() {
        bgMusic.stop();
        finish();
    }

}