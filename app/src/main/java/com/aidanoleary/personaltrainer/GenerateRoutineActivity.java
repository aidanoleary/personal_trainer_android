package com.aidanoleary.personaltrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aidanoleary.personaltrainer.models.User;

import java.util.ArrayList;


public class GenerateRoutineActivity extends Activity {

    private static String TAG = GenerateRoutineActivity.class.getSimpleName();

    private int currentQuestionNumber;
    private String[] questionList;
    private TextView questionNumberText;
    private TextView questionText;
    private LinearLayout answersLayout;
    private Button confirmButton;
    private Button backButton;
    private ViewGroup mainContent;
    private RadioGroup goalGroup;
    private LinearLayout workoutDaysWrapper;
    private EditText ageField;
    private RadioGroup genderGroup;
    private EditText heightField;
    private EditText weightField;
    private RadioGroup timeGroup;

    // Variables used to generate the workout
    // ======================================

    // Workout variables
    // An integer variable to represent the users goal
    // Variables used to store question information until end where workout is generated.

    // Used to determine the number of reps and initial weight
    private int goal;
    // Determines the amount of workouts in the routine, and how exercises and muscle groups are divided
    private ArrayList<String> workoutDays;
    // Used to determine the weight the user should be lifting
    private int usersAge;
    // Used to determine the weight the user should be lifting
    private String usersGender;

    // Used to determine the usersBMI
    private double usersHeight;
    private double usersWeight;
    // Used to determine the number of exercises
    private int workoutTime;

    // Set the initial weight dependent on usersGoal, usersAge, usersGender
    private double initialWeight;
    // Set the number of exercises dependent on the workout time
    private int numberOfExercises;
    // Set the number of reps dependent on the users goal
    private int numberOfReps;
    // Set the number of sets dependent on the users goal
    private int numberOfSets;
    // Set the usersBMI depending on the usersHeight and usersWeight
    // TODO deal with BMI later if I have time
    private int usersBMI;

    // user variables
    // private int usersFitnessLevel;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_routine);
        mainContent = (ViewGroup) findViewById(R.id.generateContentLayout);

        // Set the current question
        currentQuestionNumber = 1;

        // set the initial question number text
        questionNumberText = (TextView) findViewById(R.id.generateQuestionNumberText);
        questionNumberText.setText(questionNumberText.getText().toString() + currentQuestionNumber);

        // Initialize variables
        // =========
        // initialise workout days variables including layout containing checkboxes
        workoutDays = new ArrayList<String>();

        // Add the initial sub question layout to the activity
        mainContent.addView(View.inflate(this, R.layout.activity_generate_routine_q1, null));

        // Add the onclick listener for the confirm button
        confirmButton = (Button) findViewById(R.id.generateConfirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentQuestionNumber) {
                    case 1:
                        // *********
                        // What is your goal?
                        // ***********

                        // Perform actions for selection
                        // =============================

                        // Get current selection and update goal field
                        goalGroup = (RadioGroup) findViewById(R.id.generateGoalGroup);
                        goal = goalGroup.indexOfChild(findViewById(goalGroup.getCheckedRadioButtonId()));
                        Log.v(TAG, "Current Goal is " + goal);


                        // =============================

                        // Update view and move to next view by using a custom function
                        nextQuestion(R.layout.activity_generate_routine_q2);
                        break;


                    case 2:
                        // *********
                        // What days of the week do you exercises?
                        // *********

                        // Perform actions for selection
                        // =============================

                        //Loop through the array and clear the items.
                        workoutDays.clear();

                        // Add the selected items to Arraylist of weekday strings.
                        // Loop through all items in the weekday relative layout
                        // Checking if checkboxes are selected. If they are add them to the arraylist
                        // of selected weekdays.
                        workoutDaysWrapper = (LinearLayout) findViewById(R.id.generateWeekdaysLayout);
                        CheckBox currentCheckbox;

                        for(int i = 0; i < workoutDaysWrapper.getChildCount(); i++) {
                            currentCheckbox = (CheckBox) workoutDaysWrapper.getChildAt(i);
                            if(currentCheckbox.isChecked()) {
                                workoutDays.add(currentCheckbox.getText().toString());
                                Log.v(TAG, currentCheckbox.getText().toString() + " has been added to weekdays.");
                            }
                        }

                        // =============================

                        //Increment the current question
                        nextQuestion(R.layout.activity_generate_routine_q3);
                        break;

                    case 3:
                        // *********
                        // How old are you?
                        // *********

                        // Perform actions for selection
                        // =============================
                        ageField = (EditText) findViewById(R.id.generateAgeField);

                        // Check if the user has entered a valid age.
                        if(ageField.getText().toString().isEmpty()) {
                            //User has not entered a valid age so display a toast asking them to enter their age.
                            Toast.makeText(GenerateRoutineActivity.this, "Enter a valid age.", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            //User has entered a valid age, so save the age and move to next question.
                            usersAge = Integer.parseInt(ageField.getText().toString());
                            Log.v(TAG, "Age has been set to " + usersAge);
                            // =============================
                            nextQuestion(R.layout.activity_generate_routine_q4);

                        }
                        break;


                    case 4:
                        // ********
                        // Are you male or female?
                        // ********

                        // Perform actions for selection
                        // =============================
                        genderGroup = (RadioGroup) findViewById(R.id.generateGenderGroup);
                        if(genderGroup.indexOfChild(findViewById(genderGroup.getCheckedRadioButtonId())) == 0) {
                            usersGender = "male";
                        }
                        else {
                            usersGender = "female";
                        }
                        Log.v(TAG, "Gender has been set to " + usersGender);

                        // =============================
                        nextQuestion(R.layout.activity_generate_routine_q5);
                        break;


                    case 5:
                        // *********
                        // What is your height and weight?
                        // *********

                        // Perform actions for selection
                        // =============================
                        heightField = (EditText) findViewById(R.id.generateHeightField);
                        weightField = (EditText) findViewById(R.id.generateWeightField);

                        // Check if the user has entered a valid height and weight
                        if(heightField.getText().toString().isEmpty() || weightField.getText().toString().isEmpty()) {
                            //User has not entered a valid height or weight so display a toast.
                            Toast.makeText(GenerateRoutineActivity.this, "Enter a valid height and weight.", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            // User has entered a valid height and weight so save them and move to next question
                            usersHeight = Double.parseDouble(heightField.getText().toString());
                            Log.v(TAG, "Users height has been set to " + usersHeight + " centimeters");
                            usersWeight = Double.parseDouble(weightField.getText().toString());
                            Log.v(TAG, "Users weight has been set to " + usersWeight + " kilograms");


                            // =============================
                            nextQuestion(R.layout.activity_generate_routine_q6);

                        }
                        break;

                    case 6:
                        // **********
                        // How long do you spend in the gym?
                        // **********

                        timeGroup = (RadioGroup) findViewById(R.id.generateTimeGroup);
                        workoutTime = timeGroup.indexOfChild(findViewById(timeGroup.getCheckedRadioButtonId()));
                        Log.v(TAG, "Current workout time choice is " + workoutTime);
                        currentQuestionNumber++;


                    default:
                        // Create the users workout depending on the variables that have been
                        // generated.
                        generateTheRoutine();

                        // Update the Database

                        // Load the Main Activity
                        // Set flags so user can't retreat
                        Intent intent = new Intent(GenerateRoutineActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                }
            }
        });

        // Onclick listeners added to the back button that controls navigating backwards
        // =============================================================================
        backButton = (Button) findViewById(R.id.generateBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentQuestionNumber) {
                    case 2:
                        previousQuestion(R.layout.activity_generate_routine_q1);
                        break;

                    case 3:
                        previousQuestion(R.layout.activity_generate_routine_q2);
                        break;


                    case 4:
                        previousQuestion(R.layout.activity_generate_routine_q3);
                        break;


                    case 5:
                        previousQuestion(R.layout.activity_generate_routine_q4);
                        break;

                    case 6:
                        previousQuestion(R.layout.activity_generate_routine_q5);
                        break;

                    default:

                        // Load the Main Activity
                        // Set flags so user can't retreat
                        Intent intent = new Intent(GenerateRoutineActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.generate_routine, menu);
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


    // Helper methods.
    // ===============

    //A method used to clear the current view and initialise a new view
    private void changeView(int layoutId) {
        mainContent.removeAllViews();
        mainContent.addView(View.inflate(GenerateRoutineActivity.this, layoutId, null));
    }

    //A method to move to the next question
    private void nextQuestion(int layoutId) {
        currentQuestionNumber++;
        questionNumberText.setText("Question: " + currentQuestionNumber);
        changeView(layoutId);
    }

    // A method to move to the previous question
    private void previousQuestion(int layoutId) {
        currentQuestionNumber--;
        questionNumberText.setText("Question: " + currentQuestionNumber);
        changeView(layoutId);
    }

    // Generate workout method - the logic :-)
    private User generateTheRoutine() {

        // Initialise the workout variables
        // numberOfExercises = 4;
        // initialWeight = 10;
        // numberOfReps = 10;
        // numberOfSets = 3;

        // Create the user
        User user = new User();
        user.setAge(usersAge);
        user.setHeight(usersHeight);
        user.setWeight(usersWeight);
        user.setGender(usersGender);

        // Make changes dependent on the users goal
        // ==============
        switch (goal) {
            case 0:
                //build muscle
                initialWeight = 15;
                numberOfReps = 6;
                numberOfSets = 3;
                break;
            case 1:
                //lose weight
                initialWeight = 10;
                numberOfReps = 12;
                numberOfSets = 4;
                break;
            case 2:
                //general fitness
                initialWeight = 10;
                numberOfReps = 10;
                numberOfSets = 3;
                break;
            case 3:
                //get stronger
                initialWeight = 12.5;
                numberOfReps = 8;
                numberOfSets = 3;
                break;
        }

        // Make changes dependent on the workout time
        // ===========
        // Set the number of exercises dependent on the workout time
        // The specifies the number of exercises that will be in each routine.

        //TODO come back and work on this later if I have time or think of a way of doing this.

        switch (workoutTime) {
            case 0:
                //0m - 30m
                numberOfExercises = 4;
                break;
            case 1:
                //31m - 1h
                numberOfExercises = 6;
                break;
            case 2:
                // This was the first amount of exercises to be calculated due to how long I spend in the gym
                // and how many exercises I do.
                //1h - 1h30m
                numberOfExercises = 8;
                break;
            case 3:
                //over 1h30m
                numberOfExercises = 10;
                break;
        }

        // Make changes dependent on the users gender.
        // ===========
        if(usersGender == "female") {
            initialWeight -= 2.5;
        }

        // Make changes dependent on the users age.
        // ===========
        if(usersAge >= 60 || usersAge <= 16) {
            initialWeight -= 2.5;
        }


        // Set the amount of exercises for each muscle group by examining the workout time (numberOfExercises) and number of workout days (length of workout days).
        int numLegs = 0;
        int numBack = 0;
        int numChest = 0;
        int numShoulders= 0;
        int numAbs = 0;
        int numBiceps = 0;
        int numTriceps = 0;

        // Create a string array of the muscle groups.
        //String[] muscleGroups = {"legs", "back", "chest", "shoulders", "abs", "biceps", "triceps"};
        String[] muscleGroups = {"chest", "triceps", "back", "biceps", "legs", "abs", "shoulders"};


        // Calculate the total number of exercises
        int totalNumOfExercises = numberOfExercises * workoutDays.size();

        // Get the number of exercises per muscle group
        int numExercisesPerMuscle = (int) Math.ceil((totalNumOfExercises) / muscleGroups.length);
        // If the numExercisesPerMuscle is 0 make it one exercise.
        if(numExercisesPerMuscle == 0) numExercisesPerMuscle = 1;
        //int numExercisesPerMuscle = (int) Math.floor((numberOfExercises * workoutDays.size() + 1) / numberOfMuscleGroups);


        Log.v(TAG, "Total number of exercises: " + (totalNumOfExercises));
        Log.v(TAG, "number of exercise per muscle: " + numExercisesPerMuscle);

        // These variable are used below in the workoutDays case statement
        // ========
        // Create a counter to keep track of the number of exercises per muscle.
        // Create a currentMuscleIndex variable to keep track of the current muscle
        int counter = 1;
        int currentMuscleIndex = 0;
        int currentWorkoutIndex = 0;
        //boolean stopper = false;
        String currentMuscle = "";


        //Create a counter to keep track of the number of exercises per muscle.
        counter = 1;
        currentMuscleIndex = 0;

        for(int i = 0; i < totalNumOfExercises; i++) {

            if (i % numberOfExercises == 0) {
                Log.v(TAG, "current Workout day is: " + currentWorkoutIndex);
                currentWorkoutIndex++;
                //currentMuscleIndex++;
                //stopper = true;
            }

            // Retrieve random exercise for muscle group
            // To get the current muscle group get the current muscle index modulo the length of muscle groups.
            currentMuscle = muscleGroups[currentMuscleIndex % muscleGroups.length];
            Log.v(TAG, "current muscle group is: " + currentMuscle);

            if ((counter == numExercisesPerMuscle)) {
                counter = 1;
                currentMuscleIndex++;
            } else {
                counter++;
            }
            //stopper = false;
        }

        switch (workoutDays.size()) {
            case 1:
                /*
                //Create a counter to keep track of the number of exercises per muscle.
                counter = 1;
                currentMuscleIndex = 0;
                for(int i = 0; i < totalNumOfExercises; i++) {

                    if(i%numberOfExercises == 0) {
                        Log.v(TAG, "current Workout day is: " + currentWorkoutIndex);
                        currentWorkoutIndex++;
                    }

                    // Retrieve random exercise for muscle group
                    // To get the current muscle group get the current muscle index modulo the length of muscle groups.
                    currentMuscle = muscleGroups[currentMuscleIndex % muscleGroups.length];
                    Log.v(TAG, "current muscle group is: " + currentMuscle);

                    if(counter == numExercisesPerMuscle) {
                        counter = 1;
                        currentMuscleIndex++;
                    }
                    else {
                        counter++;
                    }



                    //workout.addExercise(new Exercise());
                }
                */

                // Loop and create exercises until the numberOfExercises has been reached.
                //Workout workout = new Workout("Workout", "Full Body", workoutDays.get(0));


                /*
                for(int i = 0; i < numberOfExercises; i++) {
                    // Retrieve random exercise for muscle group
                    // To get the current muscle group get the current muscle index modulo the length of muscle groups.
                    Log.v(TAG, "current muscle group is: " + muscleGroups[currentMuscleIndex % muscleGroups.length]);

                    if(counter == numExercisesPerMuscle) {
                        counter = 1;
                        currentMuscleIndex++;
                    }
                    else {
                        counter++;
                    }


                    //workout.addExercise(new Exercise());
                }
                */

                break;
            case 2:
                /*
                //Create a counter to keep track of the number of exercises per muscle.
                counter = 1;
                currentMuscleIndex = 0;
                for(int i = 0; i < totalNumOfExercises; i++) {

                    if(i%numberOfExercises == 0) {
                        Log.v(TAG, "current Workout day is: " + currentWorkoutIndex);
                        currentWorkoutIndex++;
                    }

                    // Retrieve random exercise for muscle group
                    // To get the current muscle group get the current muscle index modulo the length of muscle groups.
                    currentMuscle = muscleGroups[currentMuscleIndex % muscleGroups.length];
                    Log.v(TAG, "current muscle group is: " + currentMuscle);

                    if(counter == numExercisesPerMuscle) {
                        counter = 1;
                        currentMuscleIndex++;
                    }
                    else {
                        counter++;
                    }



                    //workout.addExercise(new Exercise());
                }
                */

                break;

            case 3:
                Log.v(TAG, "case 3 has been entered. ");
                //day 1 = chest, tris, and shoulders

                //day 2 = back and bis

                //day 3 = legs and abs

                break;

            case 4:
                //day 1 = chest and tris

                //day 2 = back and bis

                //day 3 = legs and abs

                //day 5 = shoulders

                break;

            case 5:



                break;

            case 6:



                break;

            case 7:



                break;
        }




        return user;



        // Start generating the workout







    }
}
