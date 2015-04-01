package com.aidanoleary.personaltrainer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.aidanoleary.personaltrainer.helpers.DBAdapter;
import com.aidanoleary.personaltrainer.models.Exercise;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;


public class LauncherActivity extends Activity {

    private static final String TAG = LauncherActivity.class.getSimpleName();

    // A static variable that contains the web address the web service is located on.
    private static final String API_URL = "https://gymbot.herokuapp.com/";
    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        /*
         * This is the first activity that the program will launch
         * It will determine if the user is logged in by checking if shared preferences contains
         * Their token. It will then check if exercises have been loaded to the database, and
         * create the database and load them if they haven't. It will also check if the user has a workout and if they don't
         * redirect them to the generate routine activity. If exerything has been initialised the
         * user will be redirected to the main activity.
         */


        // =====================
        // Add API url to shared preferences if it doesn't exist
        // =====================
        SharedPreferences defaultPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Check if the default preferences already contains the api url
        if(!defaultPreferences.contains("ApiUrl")) {
            // It doesn't contain it so add the api url to shared preferences.
            SharedPreferences.Editor editor = defaultPreferences.edit();
            editor.putString("ApiUrl", API_URL);
            editor.commit();

        }

        Log.v(TAG, defaultPreferences.getString("ApiUrl", "") + " has been added to the default shared preferences.");
        // =====================

        // =====================
        // Check if the user is logged in, if they aren't redirect them to the login activity.
        // =====================

        // Get shared preferences for the current user
        SharedPreferences mPreferences = getApplicationContext().getSharedPreferences("CurrentUser", MODE_PRIVATE);

        // Check if the user is logged in by checking shared preferences for a authorization token
        if(!mPreferences.contains("AuthToken")) {
            // User isn't logged in so go to the login screen
            navigateToLogin();
        }
        else {

            // ***************
            // Create the database if it doesn't already exist.
            // ***************

            // Load the database from the files
            createDB();
            Log.v(TAG, "database created");

            // ***************

            // ***************
            // Check if the database contains exercises if it doesn't load them from the server.
            // and then send the user to the Generate Routine activity.
            // ***************
            db = new DBAdapter(this);
            db.open();
            boolean isExercisesEmpty = db.isTableEmpty("exercise");
            Log.v(TAG, "is exercise table empty: " + isExercisesEmpty);
            db.close();

            if(isExercisesEmpty) {
                // Exercises are empty so load the exercises from the server
                addExercisesLoadGenerator();
            }
            else {
                // Exercises have already been added so send the user to either the MainActivity or
                // GenerateRoutineActivity depending on whether they already have a workout.
                // ----------------
                // Check if the user already has a workout
                // ----------------

                db.open();
                boolean isUserInDb = db.isDataInDb("user", "email", "'" + getSharedPreferences("CurrentUser", MODE_PRIVATE).getString("Email", "") + "'");
                Log.v(TAG, "Is user in the database: " + isUserInDb);
                db.close();
                if(isUserInDb) {
                    // User does exist in the database so go to the main activity.
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
                else {
                    // User doesn't exist in the database so load the generate routine activity.
                    Intent intent = new Intent(this, GenerateRoutineActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }

                // ------------------
            }

            // ***************




        }


        // =================

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.launcher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // ===================
    // Helper functions
    // ===================

    // A method for navigating to the log in screen.
    public void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        //Pass the api url to the intent.
        //intent.putExtra("apiUrl", API_URL);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }


    // SQLite Database creation

    // A method that will be used to copy the database from one location to another
    // it uses input and output streams
    private void copyDB(InputStream input, OutputStream output) throws IOException {
        // --- copy 1K bytes at a time ---
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        input.close();
        output.close();
    }

    // A method that will be used to create the initial database
    private void createDB() {
        String destDir = "/data/data/" + getPackageName() +
                "/databases/";

        String destPath = destDir + "MyDB";
        File f = new File(destPath);

        // Check if the database already exists if not create it.
        // This stops the database from being overridden resulting in data being deleted.
        if (!f.exists()) {
            File directory = new File(destDir);
            directory.mkdirs();

            // Copy the database from the assets folder into the database folder
            try {
                copyDB(getBaseContext().getAssets().open("mydb"), new FileOutputStream(destPath));
                Log.v("Database created", "Database created");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // A method that checks if the exercises have been added to the sqlite database and if they haven't adds them
    // from the server.
    private void addExercisesLoadGenerator() {
        // Check if exercises have been added
        // if they haven't run a async task to get the exercises from the server and load them into the database
        db = new DBAdapter(this);

        // If the exercise table is empty use async task to populate it with exercises from the
        // webserver
        // run async task to add exercises.
        // Create the url with the users email and auth token.
        SharedPreferences preferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        String userEmail = preferences.getString("Email", "");
        String userToken = preferences.getString("AuthToken", "");

        String exercisesUrl = API_URL + "exercises.json?user_email=" + userEmail + "&user_token=" + userToken;
        // Do the Async task for loading the exercises.
        new GetExercisesLoadGeneratorTask().execute(exercisesUrl);

    }

    // A inner class to retrieve exercises from the personal trainer api
    // and upload them to local sqlite database
    private class GetExercisesLoadGeneratorTask extends AsyncTask<String, Void, String> {

        private final ProgressDialog dialog = new ProgressDialog(LauncherActivity.this);


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
                    Log.v(TAG, "Is Barbell Squat in the database: " + db.isDataInDb("exercise", "name", "'barbell squat'"));

                    // Close the connection to the database
                    db.close();

                    // Send the user to the generate routine activity.
                    Intent intent = new Intent(LauncherActivity.this, GenerateRoutineActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);

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
