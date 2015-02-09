package com.aidanoleary.personaltrainer;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.aidanoleary.personaltrainer.models.Exercise;
import com.aidanoleary.personaltrainer.models.Routine;
import com.aidanoleary.personaltrainer.models.User;
import com.aidanoleary.personaltrainer.models.Workout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by aidanoleary on 11/01/2015.
 */
public class WorkoutFragment extends Fragment {
    private static String TAG = WorkoutFragment.class.getSimpleName();

    View rootView;

    private User currentUser;

    private String[] workoutNames;
    private String[] workoutDays;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_workout, container, false);

        // Due to this being the initial fragment that is loaded, it also has to set the parent activities initial
        // current user variable. If I try to do this from the main activity the program crashes, because I try to access it
        // from the fragment before the activity has created it.
        // =======================================================

        // TODO perform a check to see if the user already has a workout in the database

        // This will run when the application is first run and the current user still needs to be initialised.
        if(((MainActivity)getActivity()).getCurrentUser() == null) {
            // ===== testing purposes ======
            SharedPreferences preferences = getActivity().getSharedPreferences("CurrentUser", getActivity().MODE_PRIVATE);
            String userEmail = preferences.getString("Email", "");
            String userAuthToken = preferences.getString("AuthToken", "");

            // Create 6 exercises
            ArrayList<Exercise> exercises = new ArrayList<Exercise>();
            for (int i = 0; i < 6; i++) {
                exercises.add(new Exercise("test exercise " + i));
            }

            // Create 3 workouts and exercises to them
            ArrayList<Workout> workouts = new ArrayList<Workout>();
            for (int i = 0; i < 6; i++) {
                workouts.add(new Workout("test workout " + i, "test description " + i, "day " + i));
                workouts.get(i).addExerciseList(exercises);
            }


            // create a routine and add workouts to it
            Routine routine = new Routine("Workout routine 1", "This is the description of the routine", workouts);
            currentUser = new User(userEmail, 22, "male", routine, 100);
            ((MainActivity) getActivity()).setCurrentUser(currentUser);

            // Change this to use the data in the sqlite database

        }

        // Get the Current User from the main activity
        currentUser = ((MainActivity) getActivity()).getCurrentUser();

        // Load the details for the current workout
        int numOfWorkouts = currentUser.getRoutine().getWorkouts().size();
        workoutNames = new String [numOfWorkouts];
        workoutDays = new String[numOfWorkouts];
        for(int i = 0; i < numOfWorkouts; i++ ) {
            // Get the workout name from the data structure :-)
            workoutNames[i] = currentUser.getRoutine().getWorkouts().get(i).getName();
            workoutDays[i] = currentUser.getRoutine().getWorkouts().get(i).getDay();
        }

        // Update the items in the view
        // ===============
        TextView welcomeText = (TextView) rootView.findViewById(R.id.workoutWelcomeText);
        welcomeText.setText(welcomeText.getText() + currentUser.getEmail());

        TextView pointsText = (TextView) rootView.findViewById(R.id.workoutPointsText);
        pointsText.setText(pointsText.getText() + Integer.toString(currentUser.getPoints()));

        TextView dateText = (TextView) rootView.findViewById(R.id.workoutDateText);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(new Date());
        dateText.setText(dateText.getText() + dateString);


        // Initialise the workout list by getting the ListView and setting the ArrayAdapter on it.
        // Also set the click listener on the items.
        ListView workoutList = (ListView) rootView.findViewById(R.id.listView);
        workoutList.setAdapter(new WorkoutArrayAdapter(getActivity(), workoutNames, workoutDays));
        workoutList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("WorkoutFragment", workoutNames[position] + " was pressed!");
            }
        });

        //setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, days));
        return rootView;
    }
}
