package com.aidanoleary.personaltrainer;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.aidanoleary.personaltrainer.models.MainSingleton;
import com.aidanoleary.personaltrainer.models.User;

import java.text.SimpleDateFormat;
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the Current User object from the MainSingleton
        currentUser = MainSingleton.get(getActivity()).getUser();

        // Load the details for the current workout
        int numOfWorkouts = currentUser.getRoutine().getWorkouts().size();
        workoutNames = new String [numOfWorkouts];
        workoutDays = new String[numOfWorkouts];
        for(int i = 0; i < numOfWorkouts; i++ ) {
            // Get the workout name from the data structure :-)
            workoutNames[i] = currentUser.getRoutine().getWorkouts().get(i).getName();
            workoutDays[i] = currentUser.getRoutine().getWorkouts().get(i).getDay();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_workout, container, false);



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
        final Intent intent = new Intent(getActivity(), WorkoutExerciseListActivity.class);

        workoutList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("WorkoutFragment", workoutNames[position] + " was pressed!");
                intent.putExtra("workoutNumber", position);
                startActivity(intent);
            }
        });

        //setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, days));
        return rootView;
    }
}
