package com.aidanoleary.personaltrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aidanoleary.personaltrainer.helpers.DBAdapter;
import com.aidanoleary.personaltrainer.models.Exercise;
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
    private boolean[] exerciseCompletedList;

    private DBAdapter db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_exercise_list);

        // Store the workout number
        numOfWorkout = getIntent().getIntExtra("workoutNumber", 0);

        currentUser = MainSingleton.get(this).getUser();
        Workout workout = currentUser.getRoutine().getWorkouts().get(numOfWorkout);
        int exerciseListSize = workout.getExerciseList().size();
        exerciseNames = new String[exerciseListSize];
        exerciseSetsList = new String[exerciseListSize];
        exerciseRepsList = new String[exerciseListSize];
        exerciseCompletedList = new boolean[exerciseListSize];

        // Get the variables from the workout.
        for(int i = 0; i < exerciseListSize; i++) {
            exerciseNames[i] = workout.getExercise(i).getName();
            exerciseSetsList[i] = Integer.toString(workout.getExercise(i).getSets());
            exerciseRepsList[i] = Integer.toString(workout.getExercise(i).getReps());
            exerciseCompletedList[i] = workout.getExercise(i).isCompleted();
        }



        // Initialise the view objects
        // =======
        ((TextView) findViewById(R.id.exerciseListTitleText)).setText(workout.getName());

        ListView exerciseList = (ListView) findViewById(R.id.listView);
        exerciseList.setAdapter(new WorkoutExerciseArrayAdapter(this, exerciseNames, exerciseSetsList, exerciseRepsList, exerciseCompletedList));
        final Intent intent = new Intent(this, ExerciseActivity.class);

        // Set flag to no history so activity doesn't end up on the stack.
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        exerciseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v(TAG, exerciseNames[position] + " was clicked!");
                intent.putExtra("workoutNumber", numOfWorkout);
                intent.putExtra("exerciseNumber", position);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        // Check if all the exercises have been completed if they have save the workout to the database
        // also set a saved variable workout to true so it doesn't keep saving
        // notify the user the workout has been saved.
        currentUser = MainSingleton.get(this).getUser();
        Workout currentWorkout = currentUser.getRoutine().getWorkouts().get(numOfWorkout);

        // Check if the current workout hasn't already been saved.
        if(!currentWorkout.getIsSaved()) {
            int numCompleted = 0;
            for (Exercise exercise : currentWorkout.getExerciseList()) {
                if (exercise.isCompleted())
                    numCompleted++;
            }
            // Check if the number of completed exercises is equal to the number of exercises
            if (numCompleted == currentWorkout.getExerciseList().size()) {
                // Save the workout to the database. By inserting a user workout entry.
                db = new DBAdapter(this);
                db.open();
                long userWorkoutId = db.insertUserWorkout(currentUser,currentWorkout);

                // Loop through exercises in the workout saving each one to the database.
                for(int i = 0; i < currentWorkout.getExerciseList().size(); i++) {
                    db.insertUserWorkoutExercise(userWorkoutId, currentWorkout.getExerciseList().get(i));
                }

                db.close();

                // Set the is saved variable to true.
                currentWorkout.setIsSaved(true);

                Intent intent = new Intent(WorkoutExerciseListActivity.this, MainActivity.class);
                // I don't want the intent to appear on the stack so set the clear top flag.
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                // Show a toast to let the user know their workout has been saved.
                Toast.makeText(this, "Workout has been saved.", Toast.LENGTH_SHORT).show();
            }
        }
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
