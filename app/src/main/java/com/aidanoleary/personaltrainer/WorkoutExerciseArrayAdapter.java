package com.aidanoleary.personaltrainer;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by aidanoleary on 11/02/2015.
 */
public class WorkoutExerciseArrayAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] exerciseNames;
    private final String[] setsList;
    private final String[] repsList;
    private final boolean[] completedList;

    public WorkoutExerciseArrayAdapter(Activity context, String[] exerciseNames, String[] setsList, String[] repsList, boolean[] completedList) {
        super(context, R.layout.workout_exercise_lv_row, exerciseNames);
        this.context = context;
        this.exerciseNames = exerciseNames;
        this.setsList = setsList;
        this.repsList = repsList;
        this.completedList = completedList;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.workout_exercise_lv_row, null, true);

        //Customise the content of each row of the view dependent on the position
        ((TextView)rowView.findViewById(R.id.exerciseNameText)).setText(exerciseNames[position]);
        ((TextView)rowView.findViewById(R.id.exerciseRepsAndSetsText)).setText(setsList[position] + " Sets / " + repsList[position] + " reps");

        // Change the background color depending on whether the exercise has been completed or not.
        if(completedList[position]) {
            ((RelativeLayout) rowView).setBackgroundColor(Color.parseColor("#AAF200"));
        }

        return rowView;
    }

}

