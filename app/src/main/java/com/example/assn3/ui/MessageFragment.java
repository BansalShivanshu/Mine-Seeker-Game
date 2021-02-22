/**
 * Author: Shivanshu Bansal
 * This is MessageFragment.java
 * This is launched when the user wins on the game menu.
 * This class facilitates the code to make that inflator work and to finish the activity when ok is clicked.
 */

package com.example.assn3.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.assn3.R;

public class MessageFragment extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.message_layout, null);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
            }
        };

        return new AlertDialog.Builder(getActivity())
                .setView(v)
              //  .setCancelable(false)
                .setPositiveButton(android.R.string.ok, listener)
                .create();

 //       return super.onCreateDialog(savedInstanceState);
    }
}
