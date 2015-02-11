package com.aidanoleary.personaltrainer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.aidanoleary.personaltrainer.models.MainSingleton;
import com.aidanoleary.personaltrainer.models.User;
import com.aidanoleary.personaltrainer.models.Workout;


public class WorkoutExerciseListActivity extends Activity {

    private static String TAG = WorkoutExerciseListActivity.class.getSimpleName();

    // This is a variable passed to the intent. It is used to determine the workout to display.
    private int numOfWorkout;

    private User currentUser;
    private String[] exerciseNames;
    private String[] exerciseSetsList;
    private String[] exerciseRepsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_exercise_list);

        // Store the workout number
        numOfWorkout = getIntent().getIntExtra("workoutNumber", 0);

        // Maybe change this class to only load up the workout
        // ================== 0000000000000000
        // 000000000000



        currentUser = currentUser = MainSingleton.get(this).getUser();
        Workout workout = currentUser.getRoutine().getWorkouts().get(numOfWorkout);
        int exerciseListSize = workout.getExerciseList().size();
        exerciseNames = new String[exerciseListSize];
        exerciseSetsList = new String[exerciseListSize];
        exerciseRepsList = new String[exerciseListSize];

        // Get the variables from the workout.
        for(int i = 0; i < exerciseListSize; i++) {
            exerciseNames[i] = workout.getExercise(i).getName();
            exerciseSetsList[i] = Integer.toString(workout.getExercise(i).getSets());
            exerciseRepsList[i] = Integer.toString(workout.getExercise(i).getReps());
        }



        // Initialise the view objects
        // =======
        ((TextView) findViewById(R.id.exerciseListTitleText)).setText(workout.getName());

        ListView exerciseList = (ListView) findViewById(R.id.listView);
        exerciseList.setAdapter(new WorkoutExerciseArrayAdapter(this, exerciseNames, exerciseSetsList, exerciseRepsList));
        exerciseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v(TAG, exerciseNames[position] + " was clicked!");
            }
        });
        //exerciseList.setAdapter(new WorkoutExerciseArrayAdapter(this, workout.get));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.workout_exercise_list, menu);
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
}