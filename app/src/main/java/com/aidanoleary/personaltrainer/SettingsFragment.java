package com.aidanoleary.personaltrainer;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 *
 *
 */
public class SettingsFragment extends Fragment {
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        Button generateButton = (Button) rootView.findViewById(R.id.settingsGenerateButton);

        //Add a onclick listener to the generate workout button
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create a dialog box that ask the user to confirm the generation of a new workout.
                // Use the Builder class for convenient dialog construction
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you wish to generate a new workout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User has said they want to generate a new workout
                                Intent intent = new Intent(getActivity(), GenerateRoutineActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create();
                builder.show();
            }
        });

        // Hide the button and don't make it clickable because this functionality hasn't been implemented
        // yet and I don't know what will happen if we try to generate a workout for a user who already has one.
        generateButton.setVisibility(View.INVISIBLE);
        generateButton.setClickable(false);


        return rootView;

    }
}
