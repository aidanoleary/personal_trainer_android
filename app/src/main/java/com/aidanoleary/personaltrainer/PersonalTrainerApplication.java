package com.aidanoleary.personaltrainer;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;

/**
 * Created by aidanoleary on 24/12/2014.
 */
public class PersonalTrainerApplication extends Application {

    private static final String TAG = PersonalTrainerApplication.class.getSimpleName();

    public void onCreate() {
        Log.v(TAG, "Application has started successfully");

        //Initialise Parse with the appID and clientKey
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "DudHQ1INQFxXETZAqgU3zPxspEsyELfKUZ6uPrYw", "nMfnRuGExd2IeCWp7DnEIx3Jv3s4ILzVXC6AwB3C");
    }
}
