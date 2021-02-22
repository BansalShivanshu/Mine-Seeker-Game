/**
 * Author: Shivanshu Bansal
 * This is Options.java
 * Here the user can change the size of the grid, number of rotten apples to be placed, and can reset the number of times the game has been played
 */

package com.example.assn3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.assn3.R;
import com.example.assn3.model.Grid;

public class Options extends AppCompatActivity {

    Grid grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

     //   Intent optionsIntent = getIntent();

        grid = Grid.getInstance();

        createRadioButtons();

        createMinesMenu();

        setupResetButton();

        setupSaveButton();
    }

    private void createRadioButtons() {
        RadioGroup brdSize = (RadioGroup)findViewById(R.id.rdGroupSizes);
        String[] numSizes = getResources().getStringArray(R.array.brd_sizes);
        for (int i = 0; i < numSizes.length; i++) {
            String numSize = numSizes[i];

            final RadioButton button = new RadioButton(this);
            button.setText(numSize);
            button.setTextSize(16);
            button.setTextColor(getResources().getColor(R.color.white));

            brdSize.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s = button.getText().toString();

                    if (s.charAt(0) == '4') {
                        grid.setnRows(4);
                        grid.setnColumns(6);
                    } else if (s.charAt(0) == '5') {
                        grid.setnRows(5);
                        grid.setnColumns(10);
                    } else if (s.charAt(0) == '6') {
                        grid.setnRows(6);
                        grid.setnColumns(15);
                    } else {
                        grid.setnRows(4);
                        grid.setnColumns(6);
                    }
                }
            });

        }
    }

    private void createMinesMenu() {
        RadioGroup brdSize = (RadioGroup)findViewById(R.id.minesMenu);
        int[] numSizes = getResources().getIntArray(R.array.minesNumber);
        for (int i = 0; i < numSizes.length; i++) {
            int numSize = numSizes[i];

            final RadioButton button = new RadioButton(this);
            button.setText(numSize + " " + getResources().getString(R.string.apples));
            button.setTextSize(16);
            button.setTextColor(getResources().getColor(R.color.white));

            brdSize.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String selected = button.getText().toString();

                    if (selected.charAt(0) == '6') {
                        grid.setnMines(6);
                    } else if (selected.charAt(0) == '1' && selected.charAt(1) == '0') {
                        grid.setnMines(10);
                    } else if (selected.charAt(0) == '1' && selected.charAt(1) == '5') {
                        grid.setnMines(15);
                    } else if (selected.charAt(0) == '2' && selected.charAt(1) == '0') {
                        grid.setnMines(20);
                    } else {
                        grid.setnMines(6);
                    }
                }
            });

        }
    }

    private void setupResetButton() {
        Button reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grid.resetTimesPlayed();
                Toast.makeText(Options.this, "Times Played has been reset! ENJOY!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSaveButton() {
        Button save = findViewById(R.id.settingsSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}