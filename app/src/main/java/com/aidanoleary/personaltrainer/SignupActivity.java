package com.aidanoleary.personaltrainer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class SignupActivity extends Activity {

    private static String TAG = SignupActivity.class.getSimpleName();

    protected EditText mEmailText;
    protected EditText mPasswordText;
    protected EditText mPasswordConfirmationText;
    protected Button mSignupButton;
    protected TextView mLoginText;
    private String signupUrl;
    private String mEmail;
    private String mPassword;
    private String mPasswordConfirmation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_signup);

        // Get the api url from the shared preferences.
        signupUrl = PreferenceManager.getDefaultSharedPreferences(this).getString("ApiUrl", "") + "users";

        //Get User interface objects
        mEmailText = (EditText) findViewById(R.id.signupEmailField);
        mPasswordText = (EditText) findViewById(R.id.signupPasswordField);
        mPasswordConfirmationText = (EditText) findViewById(R.id.signupPasswordConfirmationField);
        mSignupButton = (Button) findViewById(R.id.signupButton);
        mLoginText = (TextView) findViewById(R.id.signupLoginText);

        // Set the signup screen logo
        // ImageView exerciseImage = (ImageView) findViewById(R.id.signupLogoImage);
        // exerciseImage.setImageResource(R.drawable.logo1);

        //Add an onclick listener for the signup button
        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEmail = mEmailText.getText().toString().trim();
                mPassword = mPasswordText.getText().toString().trim();
                mPasswordConfirmation = mPasswordConfirmationText.getText().toString().trim();

                //Check if any of the fields are empty
                if(mEmail.isEmpty() || mPassword.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setMessage(R.string.signup_error_message);
                    builder.setTitle(R.string.signup_error_title);
                    builder.setPositiveButton(android.R.string.ok, null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    // Check if the password matches the confirmation
                    if (!mPassword.equals(mPasswordConfirmation)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                        builder.setMessage(getString(R.string.signup_error_password_match));
                        builder.setTitle(R.string.signup_error_title);
                        builder.setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    else {
                        //Set the loading spinner to visible
                        setProgressBarIndeterminateVisibility(true);
                        mSignupButton.setText("Signing up...");
                        mSignupButton.setClickable(false);
                        new SignupTask().execute(signupUrl);
                        setProgressBarIndeterminateVisibility(false);

                    }
                }
            }
        });

        mLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                intent.putExtra("apiUrl", getIntent().getStringExtra("apiUrl"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.signup, menu);
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

    // A inner Async task for signing the user up to the application
    private class SignupTask extends AsyncTask <String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            JSONObject userJson = new JSONObject();
            JSONObject holderJson = new JSONObject();

            StringBuilder stringBuilder = new StringBuilder();
            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urls[0]);

                // Set the headers
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-Type", "application/json");

                // JSON items to post to the server
                // Post the users email and password.
                userJson.put("email", mEmail);
                userJson.put("password", mPassword);
                holderJson.put("user", userJson);

                StringEntity stringEntity = new StringEntity(holderJson.toString());
                httpPost.setEntity(stringEntity);

                // Post to the server and get a response
                HttpResponse response = httpClient.execute(httpPost);

                InputStream inputStream = response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                // Loop through the output of the stream reader and concatenate it to the string builder object
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                inputStream.close();

            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return stringBuilder.toString();
        }

        protected void onPostExecute(String result) {
            Log.v(TAG, result);
            // Convert the response to a JSON object
            try {
                // Set the Json object to initial values just in case
                // something goes wrong.
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("success", false);
                jsonObject.put("info", "Something went wrong, Retry!");

                // Make the jsonObject a new Json object of the results.
                jsonObject = new JSONObject(result);
                if (jsonObject.has("success")) {
                    // Check if the response is successful
                    if (jsonObject.getBoolean("success")) {

                        // Launch the main activity
                        Intent intent = new Intent(getApplicationContext(), LauncherActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(SignupActivity.this, jsonObject.getString("info"), Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(SignupActivity.this, jsonObject.getString("info"), Toast.LENGTH_LONG).show();
                        mSignupButton.setText("Signup");
                        mSignupButton.setClickable(true);
                    }
                }
                else {
                    Toast.makeText(SignupActivity.this, jsonObject.getString("error"), Toast.LENGTH_LONG).show();
                    mSignupButton.setText("Signup");
                    mSignupButton.setClickable(true);
                }
            } catch (Exception e) {
                // Show a toast displaying what went wrong
                Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(result);
            }
        }
    }
}
