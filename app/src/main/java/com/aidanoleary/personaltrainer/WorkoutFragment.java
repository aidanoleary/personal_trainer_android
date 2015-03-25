package com.aidanoleary.personaltrainer;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.aidanoleary.personaltrainer.helpers.DBAdapter;
import com.aidanoleary.personaltrainer.models.Exercise;
import com.aidanoleary.personaltrainer.models.MainSingleton;
import com.aidanoleary.personaltrainer.models.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by aidanoleary on 11/01/2015.
 */
public class WorkoutFragment extends Fragment {
    private static String TAG = WorkoutFragment.class.getSimpleName();

    View rootView;

    private User currentUser;
    private DBAdapter db;

    private String[] workoutNames;
    private String[] workoutDays;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_workout, container, false);


        // Check if the exercises have been added if not download them and add them from the server
        addExercisesIfEmpty();

        // Check if the user is logged in


            // Check if the database has exercises
            // If it doesn't have exercises load the exercises



            // Check if the user has a workout already.
            // If they do load their exercises.
            // Else if they don't load exercise generator.




        // Check if the user already exists if they don't send them to Generate routine activity
        /*
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(db.isDataInDb("user", "email", "'" + getActivity().getSharedPreferences("CurrentUser", getActivity().MODE_PRIVATE).getString("Email", "") + "'")) {
            // User does exist in the database.
            db.close();

            // Get the Current User object from the MainSingleton
            currentUser = MainSingleton.get(getActivity()).getUser();

        }
        else {

            db.close();

            Intent intent = new Intent(getActivity(), GenerateRoutineActivity.class);
            startActivity(intent);
        }
        */

        // Get the Current User object from the MainSingleton
        currentUser = MainSingleton.get(getActivity()).getUser();

        // Set the current Users email address
        // TODO remove this when I find out why it doesn't display the users email when they first run the app and login.
        currentUser.setEmail(getActivity().getSharedPreferences("CurrentUser", getActivity().MODE_PRIVATE).getString("Email", ""));

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

    // A method that checks if the exercises have been added to the sqlite database and if they haven't adds them
    // from the server.
    private void addExercisesIfEmpty() {
        // ==================
        // Run this the first time the app is run
        // ===============

        // Check if exercises have been added
        // if they haven't run a async task to get the exercises from the server and load them into the database
        db = new DBAdapter(getActivity());

        // TODO MAYBe change the initial value of this to false, we will see.
        Boolean isExercisesEmpty = true;

        db.open();
        isExercisesEmpty = db.isTableEmpty("exercise");
        Log.v(TAG, "is exercise table empty: " + isExercisesEmpty);
        db.close();


        // If the exercise table is empty use async task to populate it with exercises from the
        // webserver
        if(isExercisesEmpty) {
            // run async task to add exercises.
            // Create the url with the users email and auth token.
            SharedPreferences preferences = getActivity().getSharedPreferences("CurrentUser", getActivity().MODE_PRIVATE);
            String userEmail = preferences.getString("Email", "");
            String userToken = preferences.getString("AuthToken", "");

            String exercisesUrl = ((MainActivity)getActivity()).getAPI_URL() + "exercises.json?user_email=" + userEmail + "&user_token=" + userToken;
            new GetExercisesTask().execute(exercisesUrl);
            // TODO CONTINUE HERE 000000000000000000000
        }


        // ===============
    }

    // A inner class to retrieve exercises from the personal trainer api
    // and upload them to local sqlite database
    private class GetExercisesTask extends AsyncTask<String, Void, String> {

        private final ProgressDialog dialog = new ProgressDialog(getActivity());


        // Make a progress dialog appear when the task starts, so user has to wait for completion.
        protected void onPreExecute() {
            dialog.setTitle("Downloading Exercises");
            dialog.setMessage("Loading...");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            StringBuilder stringBuilder = new StringBuilder();
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(urls[0]);

            // Set the headers
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-Type", "application/json");


            try {
                // Execute the Get request to the server and get a response
                HttpResponse response = httpClient.execute(httpGet);
                InputStream inputStream = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                // Loop through the content of the response appending lines to the string builder
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return stringBuilder.toString();

        }

        @Override
        protected void onPostExecute(String result) {
            try {
                //Create a JSON array of the retrieved results
                JSONArray exercisesArray = new JSONArray(result);

                // Print the contents of the JSON feed
                Log.v(TAG, "The exercises array has " + exercisesArray.length() + " items.");


                //Add the exercises to the local sqlite database
                // ===============

                //Create a placeholder exercise to use in the loop.
                Exercise currentExercise = new Exercise();
                JSONObject currentJsonExercise = new JSONObject();


                try {
                    //Open the connection to the database
                    db.open();

                    // Loop over the JSON array adding the exercises to the sqlite database
                    for (int i = 0; i < exercisesArray.length(); i++) {

                        //Get the current JSON exercise
                        currentJsonExercise = exercisesArray.getJSONObject(i);

                        //Create the current exercise object
                        currentExercise.setServerId(currentJsonExercise.getInt("id"));
                        currentExercise.setName(currentJsonExercise.getString("name"));
                        currentExercise.setDescription(currentJsonExercise.getString("description"));
                        currentExercise.setLevel(currentJsonExercise.getString("level"));
                        currentExercise.setMainMuscle(currentJsonExercise.getString("main_muscle"));
                        currentExercise.setOtherMuscles(currentJsonExercise.getString("other_muscles"));
                        currentExercise.setEquipment(currentJsonExercise.getString("equipment"));
                        currentExercise.setType(currentJsonExercise.getString("e_type"));
                        currentExercise.setMechanics(currentJsonExercise.getString("mechanics"));
                        currentExercise.setImageUrl(currentJsonExercise.getString("e_image"));

                        // Add the exercise to sqlite database
                        db.insertExerciseWithId(currentExercise);
                    }

                    // Check if one of the entries exists in the database
                    Log.v(TAG, "Is Barbell Squat in the database: " + db.isDataInDb("exercise", "name", "'Barbell Squat'"));
                    Log.v(TAG, "Is Exercise with ID 1275 in the datbase: " + db.isDataInDb("exercise", "id", "1281"));

                    // Close the connection to the database
                    db.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            dialog.dismiss();
        }
    }
}
