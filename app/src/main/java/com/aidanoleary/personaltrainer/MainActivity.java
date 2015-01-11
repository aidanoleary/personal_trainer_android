package com.aidanoleary.personaltrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.ParseUser;


public class MainActivity extends Activity {

    private static String TAG = MainActivity.class.getSimpleName();
    protected TextView mWelcomeText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check if user is logged in and get the current user.
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            //If nobody is logged in navigate to the login screen
            navigateToLogin();
        }
        else {
            Log.i(TAG, currentUser.getUsername());
            //Set the text for the welcome text field
            mWelcomeText = (TextView) findViewById(R.id.welcomeText);
            mWelcomeText.setText("Hello " + currentUser.getUsername());

            //Change this later on but for now leave it as it is
            // ***********************************************************************
            Intent intent = new Intent(this, NavigationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        else if (id == R.id.action_logout) {
            //If the logout button has been pressed logout of the application
            ParseUser.logOut();
            navigateToLogin();
        }
        return super.onOptionsItemSelected(item);
    }

    // A method for navigating to the log in screen.
    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
