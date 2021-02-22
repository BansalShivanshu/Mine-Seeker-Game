/**
 * author: Shivanshu Bansal
 * This is grid.java
 * This is the game logic for apple-seeker but could be used by any similar game with a different theme.
 * This class is responsible for keeping the data regarding the grid size, mines to be placed, and the number of times the game has been played
 * This class is made singleton so that only one instance of it can be instantiated.
 */

package com.example.assn3.model;

import android.util.Log;

public class Grid {
    private int nRows, nColumns, nMines, nTimesPlayed;

    private static Grid instance;
    private Grid() {
        // private constructor bcoz of singleton support.
        // all the fields are initialized to default values. them being:
        this.nRows = 4;
        this.nColumns = 6;
        this.nMines = 6;
        this.nTimesPlayed = 0;
    }

    public static Grid getInstance() {
        if (instance == null) {
            instance = new Grid();
        }
        return instance;
    }

    public int getnRows() {
        return this.nRows;
    }

    public int getnColumns() {
        return this.nColumns;
    }

    public int getnMines() {
        return this.nMines;
    }

    public int getnTimesPlayed() {
        return this.nTimesPlayed;
    }

    public void setnRows(int nRows) {
        this.nRows = nRows;
    }

    public void setnColumns(int nColumns) {
        this.nColumns = nColumns;
    }

    public void setnMines(int nMines) {
        this.nMines = nMines;
    }

    public void incrementTimesPlayed() {
        this.nTimesPlayed++;
    }

    public void resetTimesPlayed() {
        this.nTimesPlayed = 0;
    }
}
