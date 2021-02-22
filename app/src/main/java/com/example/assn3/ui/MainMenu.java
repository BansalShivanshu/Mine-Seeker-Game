/**
 * Author: SHivanshu bansal
 * This is mainMenu.java
 * This activity launches after the welcome screen
 * Here the user can select between:
 *  1. play game
 *  2. options menu
 *  3. help menu
 */

package com.example.assn3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.assn3.R;

public class MainMenu extends AppCompatActivity {

    private Button mPlay, mOptions, mHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent intent = getIntent();

        mPlay = findViewById(R.id.mmPlayGame);
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playIntent = new Intent(MainMenu.this, GameBoard.class);
                startActivity(playIntent);
            }
        });

        mOptions = findViewById(R.id.mmOptions);
        mOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent optionsIntent = new Intent(MainMenu.this, Options.class);
                startActivity(optionsIntent);
            }
        });

        mHelp = findViewById(R.id.mmHelp);
        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helpIntent = new Intent(MainMenu.this, Help.class);
                startActivity(helpIntent);
            }
        });
    }
}