package com.aidanoleary.personaltrainer;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by aidanoleary on 09/02/2015.
 */
public class WorkoutArrayAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] workoutNames;
    private final String[] workoutDays;
    private final boolean[] workoutSavedList;

    public WorkoutArrayAdapter(Activity context, String[] workoutNames, String[] workoutDays, boolean[] workoutSavedList) {
        super(context, R.layout.workout_lv_row, workoutNames);
        this.context = context;
        this.workoutNames = workoutNames;
        this.workoutDays = workoutDays;
        this.workoutSavedList = workoutSavedList;
    }

    public View getView(int position, View view, ViewGroup parent) {
        // Print the index of the row
        Log.d("WorkoutArrayAdapter", String.valueOf(position));

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.workout_lv_row, null, true);

        // get a reference to the view on the cml layout
        TextView workoutName = (TextView) rowView.findViewById(R.id.workoutNameText);
        TextView workoutDay = (TextView) rowView.findViewById(R.id.workoutDayText);


        // Customise the content of each row based on the position
        workoutName.setText(workoutNames[position]);
        workoutDay.setText(workoutDays[position]);

        // Change the background color depending on whether the workout has been saved or not.
        if(workoutSavedList[position]) {
            ((LinearLayout) rowView).setBackgroundColor(Color.parseColor("#AAF200"));
        }

        return rowView;
    }

}
