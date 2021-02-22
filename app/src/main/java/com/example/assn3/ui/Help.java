/**
 *  Author: Shivanshu Bansal
 *  This is Help.java
 *  This class is the code for the help activity. The main purpose of this class is to facilitate the hyperlink capabilities for the text view
 */

package com.example.assn3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.example.assn3.R;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        TextView textView = findViewById(R.id.helpScreenText);

        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}