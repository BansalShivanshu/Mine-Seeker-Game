/**
 * Author: Shivanshu Bansal
 * MainActivity.java is the code for the welcome screen of the game
 * when the game launches a sound is played and also the image rotates 360 degrees.
 * The MainMenu activity starts after the animation + 4 sec automatically
 * But if the user clicks skip button then the sound is stopped and the MainMenu activity is started.
 */

package com.example.assn3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.assn3.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mRing;
    private ImageView mLogo;
    private Button mSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // plays sound when the game is loaded.
        mRing = MediaPlayer.create(MainActivity.this, R.raw.windows_2000);
        mRing.start();

        // adds a rotating animation to the image
        mLogo = findViewById(R.id.welcomeLogo);
        mLogo.animate().rotationBy(360).setDuration(5000);



        // start the main menu activity after animations are over + 4 seconds.
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                start();
            }
        }, 9000);

        mSkip = findViewById(R.id.welcomeSkip);
        mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                mRing.stop();
                start();
            }
        });
    }

    private void start() {
        Intent intent = new Intent(MainActivity.this, MainMenu.class);
        startActivity(intent);
        finish();
    }
}