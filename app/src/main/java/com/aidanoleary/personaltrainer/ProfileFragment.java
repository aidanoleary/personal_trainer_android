package com.aidanoleary.personaltrainer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aidanoleary.personaltrainer.models.MainSingleton;
import com.aidanoleary.personaltrainer.models.User;

/**
 * Created by aidanoleary on 11/01/2015.
 */
public class ProfileFragment extends Fragment {
    View rootView;

    private User currentUser;
    private TextView emailText;
    private TextView ageText;
    private TextView genderText;
    private TextView heightText;
    private TextView weightText;
    private TextView pointsText;
    private TextView weightLiftedText;
    private TextView repsPerformedText;
    private TextView setsText;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Set the current user by getting the main singleton user.
        currentUser = MainSingleton.get(getActivity()).getUser();

        // User Info
        // ==========

        // Set the email
        emailText = (TextView) rootView.findViewById(R.id.profileEmailText);
        emailText.setText(currentUser.getEmail());

        // Set the age
        ageText = (TextView) rootView.findViewById(R.id.profileAgeText);
        ageText.setText(ageText.getText() + Integer.toString(currentUser.getAge()));

        // Set the Gender
        genderText = (TextView) rootView.findViewById(R.id.profileGenderText);
        genderText.setText(genderText.getText() + currentUser.getGender());

        // Set the height
        heightText = (TextView) rootView.findViewById(R.id.profileHeightText);
        heightText.setText(heightText.getText() + Double.toString(currentUser.getHeight()) + " cm");

        // Set the weight
        weightText = (TextView) rootView.findViewById(R.id.profileWeightText);
        weightText.setText(weightText.getText() + Double.toString(currentUser.getWeight()) + " kg");

        // Stats
        // =====

        // Set the points earned.
        pointsText = (TextView) rootView.findViewById(R.id.profilePointsText);
        pointsText.setText(Integer.toString(currentUser.getPoints()));

        // Set the weight lifted.
        weightLiftedText = (TextView) rootView.findViewById(R.id.profileWeightLiftedText);
        weightLiftedText.setText(currentUser.getTotalWeight() + "kg");

        // Set the reps performed
        repsPerformedText = (TextView) rootView.findViewById(R.id.profileRepsText);
        repsPerformedText.setText(Integer.toString(currentUser.getTotalReps()));

        // Set the sets performed
        setsText = (TextView) rootView.findViewById(R.id.profileSetsText);
        setsText.setText(Integer.toString(currentUser.getTotalSets()));


        return rootView;
    }
}
