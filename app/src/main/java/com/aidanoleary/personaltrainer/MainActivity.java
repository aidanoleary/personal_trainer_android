package com.aidanoleary.personaltrainer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.aidanoleary.personaltrainer.helpers.DBAdapter;
import com.aidanoleary.personaltrainer.models.User;


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

    // Stores a object to interact with the local sql database
    DBAdapter db;

    // A static variable that contains the web address the web service is located on.
    private static String API_URL = "https://personal-trainer-api.herokuapp.com/";

    // A shared preferences variable used to store the users email and auth token
    private SharedPreferences mPreferences;

    // A object to store details for the current user
    // This will be the main data structure that will be passed around the application.
    private User currentUser;

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

        // Close the navigation drawer when the main activity is first accessed.
        mNavigationDrawerFragment.closeDrawer();


        // =====================
        // SQLite Database tasks
        // ======================

        // Initialise DBAdapter to communicate with the pre configured database
        /*
        db = new DBAdapter(this);
        Log.v(TAG, "DBAdapter initialised.");

        // Test the database connection
        try {
            db.open();
            Log.v(TAG, "Database opened.");
            db.close();
            Log.v(TAG, "Database closed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        // =======================

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
            case 4:
                objFragment = new SettingsFragment();
                break;
        }

        // update the nav bar to display the selected button
        onSectionAttached(position + 1);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, objFragment)
                .commit();
    }

    // used to change the navbar title when a new fragment is attached
    public void onSectionAttached(int number) {
        String[] stringArray = getResources().getStringArray(R.array.section_titles);
        if (number >= 1) {
            mTitle = stringArray[number - 1];
        }
    }

    //old
    /*
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
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
        }
    }
    */

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    // This changes the action bar at the top of the page
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.navigation, menu);
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
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_logout) {
            //ParseUser.logOut();

            // Delete the shared preferences values for the local user.
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.remove("Email");
            editor.remove("AuthToken");
            editor.commit();

            navigateToLogin();
            return true;
        }
        */
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
    public void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        //Pass the api url to the intent.
        //intent.putExtra("apiUrl", API_URL);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }


    // Methods for passing and accessing the main data structure from fragments.
    // ==============================

    // A helper method for accessing the currentUser object from fragments
    public User getCurrentUser() {
        return currentUser;
    }

    // Get the API url from fragments
    public String getAPI_URL() {
        return API_URL;
    }

    // A helper method for setting the currentUser object from fragments
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }


}
