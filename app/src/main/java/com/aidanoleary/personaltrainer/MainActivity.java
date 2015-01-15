package com.aidanoleary.personaltrainer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;


public class MainActivity extends Activity
        implements MainDrawerFragment.NavigationDrawerCallbacks {

    private static String TAG = MainActivity.class.getSimpleName();

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private MainDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    /*
     * Stores a object to interact with the local sql database
     */
    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (MainDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        // =====================
        // SQLite Database tasks
        // ======================
        // Check if the database exists and create it if it doesn't
        createDB();

        // Initialise DBAdapter to communicate with the pre configured database
        db = new DBAdapter(this);

        // Test the database connection
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.close();
        // =======================


        // =======================
        // User Login Check
        // =======================

        //Check if user is logged in and get the current user.
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            //If nobody is logged in navigate to the login screen
            navigateToLogin();
        }
        else {

            // Add code to perform once user is logged in here
            /*
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
            */


            /*
            // Check if the database exists and create it if it doesn't
            createDB();

            // Get the local sqlite database object
            db = new DBAdapter(this);
            try {
                db.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.close();
            */
        }
    }



    // This is the method that deals with replacing the current fragment when the button is
    // pressed.
    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment objFragment = null;
        switch (position) {
            case 0:
                objFragment = new WorkoutFragment();
                break;
            case 1:
                objFragment = new ProfileFragment();
                break;
            case 2:
                objFragment = new AchievementsFragment();
                break;
            case 3:
                objFragment = new LogsFragment();
                break;
        }

        // update the nav bar to display the selected button
        onSectionAttached(position + 1);

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, objFragment)
                .commit();
    }

    // used to change the navbar title when a new fragment is attached
    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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
            ParseUser.logOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    // Helper functions
    // ===================

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

}
