package com.aidanoleary.personaltrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class GenerateRoutineActivity extends Activity {

    private int currentQuestionNumber;
    private String[] questionList;
    private TextView questionNumberText;
    private TextView questionText;
    private LinearLayout answersLayout;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_routine);



        // Initialise the list of questions
        questionList = new String[]{
                "How fit do you consider yourself?",
                "Are you male or female?",
                "How old are you?",
                "How many times a week do you exercise?",
                "What is your main goal?",
        };

        final ArrayList answersList = new ArrayList();

        // Set the question number to 1.
        currentQuestionNumber = 1;

        // Get Interface Items
        questionNumberText = (TextView) findViewById(R.id.generateQuestionNumberText);
        questionText = (TextView) findViewById(R.id.generateQuestionText);
        answersLayout = (LinearLayout) findViewById(R.id.answersLayout);
        confirmButton = (Button) findViewById(R.id.generateConfirmButton);

        // Initialise first values of on screen objects
        // ===========================================
        // Set the question number text with the current question
        questionNumberText.setText(questionNumberText.getText().toString() + currentQuestionNumber);
        // Set the questionText to the first question in the question list
        questionText.setText(questionList[0]);
        // Add the answer radio buttons
        final RadioGroup question1Answers = new RadioGroup(this);
        String[] responseList = new String[]{"answer 1", "answer 2", "Answer3", "Answer4", "Answer5"};
        for(int i = 0; i < responseList.length; i++) {
            RadioButton rb = new RadioButton(this);
            rb.setText(responseList[i]);
            question1Answers.addView(rb);
        }
        answersLayout.addView(question1Answers);


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use a switch statement to determine what action should be performed.
                switch (currentQuestionNumber) {
                    // Question 1 has been submitted
                    case 1:
                        // Get users selections and perform correct actions.
                        // =================================================

                        // Add the index of the selected radio button to
                        answersList.add(question1Answers.indexOfChild(findViewById(question1Answers.getCheckedRadioButtonId())));

                        Log.v("Hello", "" + answersList.get(0));
                        // Increase current question number
                        currentQuestionNumber++;

                        // Update Display contents
                        // =======
                        // Change the question number and question text
                        questionNumberText.setText("Question: " + currentQuestionNumber);
                        questionText.setText(questionList[currentQuestionNumber]);

                        // Change the view attached to the answers layout.
                        answersLayout.removeAllViews();


                        break;
                    // Question 2 has been submitted.
                    case 2:
                        break;


                    default:
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
}
