package com.aidanoleary.personaltrainer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by aidanoleary on 11/01/2015.
 */
public class WorkoutFragment extends Fragment {
    View rootView;

    private String[] days = {
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_workout, container, false);

        // Initialise the workout list by getting the ListView and setting the ArrayAdapter on it.
        // Also set the click listener on the items.
        ListView workoutList = (ListView) rootView.findViewById(R.id.listView);
        workoutList.setAdapter(new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, days));
        workoutList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("WorkoutFragment", days[position] + " was pressed!");
            }
        });

        //setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, days));
        return rootView;
    }
}
