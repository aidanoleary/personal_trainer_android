package com.aidanoleary.personaltrainer;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by aidanoleary on 24/12/2014.
 */
public class PersonalTrainerApplication extends Application {

    private static final String TAG = PersonalTrainerApplication.class.getSimpleName();
    // A static variable that contains the web address the web service is located on.
    private static String API_URL = "https://personal-trainer-api.herokuapp.com/";

    public void onCreate() {
        Log.v(TAG, "Application has started successfully");


        // =====================
        // Add API url to shared preferences if it doesn't exist
        // =====================
        // TODO maybe move this and other parts into the application initialization.
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

    }
}
