package com.aidanoleary.personaltrainer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aidanoleary.personaltrainer.models.Exercise;
import com.aidanoleary.personaltrainer.models.MainSingleton;


public class ExerciseActivity extends Activity {

    private int numOfWorkout;
    private int numOfExercise;
    private SeekBar difficultyBar;
    private Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);


        numOfWorkout = getIntent().getIntExtra("workoutNumber", 0);
        numOfExercise = getIntent().getIntExtra("exerciseNumber", 0);

        // Get the current exercise
        exercise = MainSingleton.get(this).getUser().getRoutine().getWorkouts().get(numOfWorkout).getExercise(numOfExercise);


        // Set the View items
        // ==================


        // Set the exercise title
        ((TextView) findViewById(R.id.exerciseTitleText)).setText(exercise.getName());

        // Set the muscle text
        TextView muscleText = (TextView) findViewById(R.id.exerciseMainMuscleText);
        muscleText.setText(muscleText.getText() + exercise.getMainMuscle());


        // Set the equipment text
        TextView equipmentText = (TextView) findViewById(R.id.exerciseEquipmentText);
        equipmentText.setText(equipmentText.getText() + exercise.getEquipment());


        // Set the type text
        TextView typeText = (TextView) findViewById(R.id.exerciseTypeText);
        typeText.setText(typeText.getText() + exercise.getType());

        // Set the mechanics text
        TextView mechanicsText = (TextView) findViewById(R.id.exerciseMechanicsText);
        mechanicsText.setText(mechanicsText.getText() + exercise.getMechanics());

        // ============================= Continue here 000000000000000000
        // TODO finish this part of the exercise activity

        // Set the description text

        // Set the weight

        // Set the sets and reps





        // Set the image view
        ImageView exerciseImage = (ImageView) findViewById(R.id.exerciseImage);
        exerciseImage.setImageResource(R.drawable.placeholder_image);

        // Set the initial value for the slider
        difficultyBar = (SeekBar) findViewById(R.id.exerciseDifficultySeekBar);
        difficultyBar.setProgress(2);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.exercise, menu);
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
