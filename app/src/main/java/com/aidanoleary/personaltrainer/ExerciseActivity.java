package com.aidanoleary.personaltrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aidanoleary.personaltrainer.helpers.DBAdapter;
import com.aidanoleary.personaltrainer.models.Exercise;
import com.aidanoleary.personaltrainer.models.MainSingleton;
import com.aidanoleary.personaltrainer.models.User;
import com.squareup.picasso.Picasso;


public class ExerciseActivity extends Activity {

    private static String TAG = ExerciseActivity.class.getSimpleName();

    private int numOfWorkout;
    private int numOfExercise;
    private SeekBar difficultyBar;
    private Button doneButton;
    private Exercise exercise;
    private DBAdapter db;

    // TODO change this when the application gets moved to the new website url.
    // private String apiUrl = "http://personal-trainer-api.herokuapp.com";
    private String imageUrl;

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

        // Set the description text
        TextView descriptionText = (TextView) findViewById(R.id.exerciseDescriptionText);
        descriptionText.setText(exercise.getDescription());
        // make the description textview scrollable.
        descriptionText.setMovementMethod(new ScrollingMovementMethod());

        // Set the weight
        TextView weightText = (TextView) findViewById(R.id.exerciseWeightText);
        weightText.setText(weightText.getText().toString() + exercise.getWeight());

        // Set the sets and reps
        TextView setAndRepsText = (TextView) findViewById(R.id.exerciseSetsAndRepsText);
        setAndRepsText.setText("Sets: " + exercise.getSets() + " / " + "Reps: " + exercise.getReps());

        // Set the image view
        // Load the image from the web server using the picasso
        ImageView exerciseImage = (ImageView) findViewById(R.id.exerciseImage);
        // Set the image url by firstly getting the api url from shared preferences.
        String apiUrl = PreferenceManager.getDefaultSharedPreferences(this).getString("ApiUrl", "");
        imageUrl = apiUrl + exercise.getImageUrl();
        // Load the image using the picasso library.
        Picasso.with(this).load(imageUrl).placeholder(R.drawable.placeholder_image).into(exerciseImage);

        //exerciseImage.setImageResource(R.drawable.placeholder_image);

        // Set the initial value for the slider
        difficultyBar = (SeekBar) findViewById(R.id.exerciseDifficultySeekBar);
        difficultyBar.setProgress(2);

        // Get the done button and set the onclick listener
        // This button deals with increasing and decreasing the users weight.
        doneButton = (Button) findViewById(R.id.exerciseDoneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Check the value of the progress bar to determine how to adjust the weight of the exercise.
                switch (difficultyBar.getProgress()) {

                    case 0:
                        for(int i = 0; i < 2; i++)
                            exercise.increaseWeight();
                        break;

                    case 1:
                        exercise.increaseWeight();
                        break;

                    case 2:
                        // Just leave the weight how it is.
                        break;

                    case 3:
                        exercise.decreaseWeight();
                        break;

                    case 4:
                        for(int i = 0; i < 2; i++)
                            exercise.decreaseWeight();
                        break;

                }

                // Specify that the exercise has been completed by updating it's completed variable
                exercise.makeCompleted();
                if(exercise.isCompleted()) {
                    Log.v(TAG, "exercise has been set to completed");
                }

                // update the exercise in the sqlite database
                db = new DBAdapter(ExerciseActivity.this);
                db.open();

                User currentUser = MainSingleton.get(ExerciseActivity.this).getUser();

                boolean updated = db.updateUserExercise(currentUser, exercise);
                if(!updated)
                    Log.v(TAG, "Exercise data failed to update");

                // Update the users points and stats
                currentUser.setPoints(currentUser.getPoints() + 10);
                currentUser.setTotalWeight(currentUser.getTotalWeight() + exercise.getWeight());
                currentUser.setTotalReps(currentUser.getTotalReps() + exercise.getReps() * exercise.getSets());
                currentUser.setTotalSets(currentUser.getTotalSets() + exercise.getSets());

                boolean updatedUserStat = db.updateUserStat(currentUser);
                if(!updatedUserStat)
                    Log.v(TAG, "Stats failed to update");


                Toast.makeText(getApplicationContext(), "+10 points", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ExerciseActivity.this, WorkoutExerciseListActivity.class);

                // Set the workout number to go to
                intent.putExtra("workoutNumber", numOfWorkout);
                // I don't want the intent to appear on the stack so set the clear top flag.
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

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
