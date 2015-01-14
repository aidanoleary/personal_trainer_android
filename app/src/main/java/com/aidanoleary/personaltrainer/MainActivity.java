package com.aidanoleary.personaltrainer;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.ParseUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends Activity {

    private static String TAG = MainActivity.class.getSimpleName();
    protected TextView mWelcomeText;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check if the database exists and create it if it doesn't
        createDB();
        Log.v("Database Exists?", "" + checkDataBase());

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

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase("/data/data/" + getPackageName() + "/databases" + "MyDb", null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null ? true : false;
    }
}
