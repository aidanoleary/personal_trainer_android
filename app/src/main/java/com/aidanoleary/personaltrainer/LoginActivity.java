package com.aidanoleary.personaltrainer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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


public class LoginActivity extends Activity {

    private static String TAG = LoginActivity.class.getSimpleName();

    protected EditText mEmailText;
    protected EditText mPasswordText;
    protected Button mLoginButton;
    protected TextView mSignUpText;
    private String apiUrl;
    private SharedPreferences mPreferences;
    private String mEmail;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Enable progress indicator
        //RequestWindowFeature needs to be called before setContentView()
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_login);
        //Get the apiUrl that was passed to the intent.
        apiUrl = getIntent().getStringExtra("apiUrl");
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);

        //Get user interface objects
        mEmailText = (EditText) findViewById(R.id.loginEmailField);
        mPasswordText = (EditText) findViewById(R.id.loginPasswordField);
        mLoginButton = (Button) findViewById(R.id.loginButton);
        mSignUpText = (TextView) findViewById(R.id.loginSignupText);

        //Set onclick listener for the login button
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailText.getText().toString();
                String password = mPasswordText.getText().toString();

                mEmail = email.trim();
                mPassword = password.trim();

                //Check if Username and Password is empty, If it is show a dialog box
                if(email.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage(R.string.login_error_message);
                    builder.setTitle(R.string.login_error_title);
                    builder.setPositiveButton(android.R.string.ok, null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    // Login
                    // ========
                    // Make progress indicator visible
                    setProgressBarIndeterminateVisibility(true);
                    new LoginTask().execute(apiUrl + "users/sign_in");
                    setProgressBarIndeterminateVisibility(false);
                    /*
                    //Login in the background using parse
                    ParseUser.logInInBackground(email, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            //Hide progress indicator
                            setProgressBarIndeterminateVisibility(false);

                            if (e == null) {
                                //Login was successful
                                // The new user was successfully created
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(e.getMessage());
                                builder.setTitle(R.string.login_error_title);
                                builder.setPositiveButton(android.R.string.ok, null);

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
                    */

                }
            }
        });


        //Set the onclick listener for signup text
        mSignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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

    // A inner Async task class for logging the user in and saving the user email and auth token to
    // Shared preferences.
    private class LoginTask extends AsyncTask <String, Void, String> {

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
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.has("success")) {
                    // Check if the response is successful
                    if (jsonObject.getBoolean("success")) {
                        // The response was successful.
                        SharedPreferences.Editor editor = mPreferences.edit();

                        // Saved the returned auth_token and the users email to shared preferences
                        editor.putString("Email", mEmail);
                        editor.putString("AuthToken", jsonObject.getJSONObject("data").getString("auth_token"));
                        editor.commit();

                        // Launch the main activity
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(LoginActivity.this, jsonObject.getString("info"), Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, jsonObject.getString("info"), Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, jsonObject.getString("error"), Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                // Show a toast displaying what went wrong
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(result);
            }
        }
    }
}
