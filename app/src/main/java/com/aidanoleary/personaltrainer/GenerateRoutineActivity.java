package com.aidanoleary.personaltrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;


public class GenerateRoutineActivity extends Activity {

    private int currentQuestionNumber;
    private String[] questionList;
    private TextView questionNumberText;
    private TextView questionText;
    private LinearLayout answersLayout;
    private Button confirmButton;
    private Button backButton;
    private ViewGroup mainContent;

    // Variables used to generate the workout
    // ======================================

    // Workout variables
    // An integer variable to represent the users goal
    private int goal;
    private String[] workoutDays;
    private int numberOfExercises;
    private int numberOfReps;
    private int numberOfSets;

    // user variables
    private int usersFitnessLevel;
    private String usersGender;
    private int usersAge;
    private int usersHeight;
    private int usersWeight;
    private int usersBMI;



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
                        RadioGroup goalGroup = (RadioGroup) findViewById(R.id.generateGoalGroup);




                        // =============================

                        // Update view and move to next view by using a custom function
                        nextQuestion(R.layout.activity_generate_routine_q2);
                        break;


                    case 2:
                        // Perform actions for selection
                        // =============================


                        // =============================

                        //Increment the current question
                        nextQuestion(R.layout.activity_generate_routine_q3);
                        break;

                    case 3:
                        // Perform actions for selection
                        // =============================


                        // =============================
                        nextQuestion(R.layout.activity_generate_routine_q4);
                        break;

                    case 4:
                        // Perform actions for selection
                        // =============================


                        // =============================
                        nextQuestion(R.layout.activity_generate_routine_q5);
                        break;


                    case 5:
                        // Perform actions for selection
                        // =============================


                        // =============================
                        nextQuestion(R.layout.activity_generate_routine_q6);
                        break;

                    default:
                        // Create the users workout depending on the variables that have been
                        // generated.

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
}
