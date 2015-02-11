package com.aidanoleary.personaltrainer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by aidanoleary on 11/02/2015.
 */
public class WorkoutExerciseArrayAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] exerciseNames;
    private final String[] setsList;
    private final String[] repsList;

    public WorkoutExerciseArrayAdapter(Activity context, String[] exerciseNames, String[] setsList, String[] repsList) {
        super(context, R.layout.workout_exercise_lv_row, exerciseNames);
        this.context = context;
        this.exerciseNames = exerciseNames;
        this.setsList = setsList;
        this.repsList = repsList;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.workout_exercise_lv_row, null, true);

        //Customise the content of each row of the view dependent on the position
        ((TextView)rowView.findViewById(R.id.exerciseNameText)).setText(exerciseNames[position]);
        ((TextView)rowView.findViewById(R.id.exerciseRepsAndSetsText)).setText(setsList[position] + " Sets / " + repsList[position] + " reps");

        return rowView;
    }

}

