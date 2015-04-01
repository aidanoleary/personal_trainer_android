package com.aidanoleary.personaltrainer;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aidanoleary.personaltrainer.helpers.DBAdapter;
import com.aidanoleary.personaltrainer.models.MainSingleton;
import com.aidanoleary.personaltrainer.models.Workout;
import com.squareup.timessquare.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class LogsFragment extends Fragment {

    View rootView;
    private CalendarPickerView calendar;
    private DBAdapter db;
    private String userEmail;
    private ArrayList<Workout> performedWorkouts;
    private ArrayList<Date> workoutDates;

    public LogsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_logs, container, false);

        // Initialise the workout dates list
        workoutDates = new ArrayList<Date>();

        // Get the users email
        userEmail = MainSingleton.get(getActivity()).getUser().getEmail();
        String userId = Long.toString(MainSingleton.get(getActivity()).getUser().getId());

        // Set the start date to today just in case there are no workouts.
        Date startDate = new Date();

        // Initialise the database adapter
        db = new DBAdapter(getActivity());

        db.open();
        // Check if the user has any workouts.
        if (db.isDataInDb("user_workout","user_id", userId)) {

            // Get the users workouts from the database.
            performedWorkouts = db.getUserWorkoutsByEmail(userEmail);

            // Create a simple date format to parse the returned dates
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss") ;


            // Loop through the returned workouts.
            for(int i = 0; i < performedWorkouts.size(); i++) {
                // Add the workout date to the list of workout dates.
                try {
                    workoutDates.add(dateTimeFormat.parse(performedWorkouts.get(i).getDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            // Set the start date to the date of the first workout.
            startDate = workoutDates.get(0);
        }
        db.close();



        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        calendar = (CalendarPickerView) rootView.findViewById(R.id.logsCalendar);
        calendar.init(startDate, nextYear.getTime()).withHighlightedDates(workoutDates).displayOnly();
        return rootView;
    }

}
