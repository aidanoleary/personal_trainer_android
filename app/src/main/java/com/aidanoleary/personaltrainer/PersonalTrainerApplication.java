package com.aidanoleary.personaltrainer;

import android.app.Application;
import android.util.Log;

/**
 * Created by aidanoleary on 24/12/2014.
 */
public class PersonalTrainerApplication extends Application {

    private static final String TAG = PersonalTrainerApplication.class.getSimpleName();

    public void onCreate() {
        Log.v(TAG, "Application has started successfully");

    }
}
