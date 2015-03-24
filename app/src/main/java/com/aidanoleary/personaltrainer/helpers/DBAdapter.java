package com.aidanoleary.personaltrainer.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.aidanoleary.personaltrainer.models.Exercise;
import com.aidanoleary.personaltrainer.models.Routine;
import com.aidanoleary.personaltrainer.models.User;
import com.aidanoleary.personaltrainer.models.UserWorkout;
import com.aidanoleary.personaltrainer.models.UserWorkoutExercise;
import com.aidanoleary.personaltrainer.models.Workout;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by aidanoleary on 14/01/2015.
 */
public class DBAdapter {

    //static final String KEY_ROWID = "_id";
    //static final String KEY_NAME = "name";
    //static final String KEY_EMAIL = "email";
    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "MyDB";
    //static final String DATABASE_TABLE = "contacts";
    static final int DATABASE_VERSION = 1;
    //static final String DATABASE_CREATE = "create table contacts (_id integer primary key autoincrement, " + "name text not null, email text not null);";
    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;


    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //db.execSQL(DATABASE_);
            //db.execSQL(DATABASE_CREATE);
            /*
            try {

                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            */
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
            //db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database--- public void close()
    public void close() {
        DBHelper.close();
    }

    /*

    //---deletes a particular contact--- public boolean deleteContact(long rowId) {
    public boolean deleteContact(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    // --- retrieves all the contacts ---
    public Cursor getAllContacts() {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME, KEY_EMAIL}, null, null, null, null, null);
    }

    // --- retrieves a particular contacts ---
    public Cursor getContact(long rowId) throws SQLException {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME, KEY_EMAIL}, KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    // --- updates a contact ---
    public boolean updateContact(long rowId, String name, String email) {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_EMAIL, email);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
    */

    // Check if a record exists in the database
    // ===========
    public boolean isDataInDb(String tableName, String dbfield, String fieldValue) {
        String dbQuery = "SELECT * FROM " + tableName + " WHERE " + dbfield + " = " + fieldValue;
        Cursor cursor = db.rawQuery(dbQuery, null);
        if(cursor.getCount() <= 0) {
            return false;
        }
        return true;
    }

    // Check if a table is empty
    // Returns true if the table is empty
    // ==========
    public boolean isTableEmpty(String tableName) {
        String query = "SELECT count(*) FROM " + tableName;
        Cursor mcursor = db.rawQuery(query, null);
        mcursor.moveToFirst();
        if(mcursor.getInt(0) > 0) {
            // table is populated return false
            return false;
        }
        else {
            // return true if the table is empty.
            return true;
        }
    }

    // Inserting items into the database
    // =================================
    // ****************

    // Insert a user into the database
    // ==========
    public long insertUser(User user) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("email", user.getEmail());
        initialValues.put("authentication_token", user.getAuthorizationToken());
        initialValues.put("age", user.getAge());
        initialValues.put("gender", user.getGender());
        initialValues.put("weight", user.getWeight());
        initialValues.put("height", user.getHeight());
        initialValues.put("routine_id", user.getRoutine().getId());
        return db.insert("user", null, initialValues);
    }

    // Insert a routine into the database
    // ==========
    public long insertRoutine(Routine routine) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("name", routine.getName());
        initialValues.put("description", routine.getDescription());
        return db.insert("routine", null, initialValues);
    }

    // Insert a workout into the database
    // ==========
    public long insertWorkout(Workout workout) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("name", workout.getName());
        initialValues.put("description", workout.getDescription());
        initialValues.put("routine_id", workout.getRoutineId());
        return db.insert("workout", null, initialValues);
    }

    // Insert a workout_exercise
    // ==========
    public long insertWorkoutExercise(long workoutId, long exerciseId) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("workout_id", workoutId);
        initialValues.put("exercise_id", exerciseId);
        return db.insert("workout_exercise", null, initialValues);
    }

    // Insert a exercise into the database
    // ==========
    public long insertExercise(Exercise exercise) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("name", exercise.getName());
        initialValues.put("description", exercise.getDescription());
        initialValues.put("level", exercise.getLevel());
        initialValues.put("main_muscle", exercise.getMainMuscle());
        initialValues.put("other_muscles", exercise.getOtherMuscles());
        initialValues.put("equipment", exercise.getEquipment());
        initialValues.put("type", exercise.getType());
        initialValues.put("mechanics", exercise.getMechanics());
        initialValues.put("image_url", exercise.getImageUrl());
        return db.insert("exercise", null, initialValues);
    }

    public long insertExerciseWithId(Exercise exercise) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("server_id", exercise.getServerId());
        initialValues.put("name", exercise.getName());
        initialValues.put("description", exercise.getDescription());
        initialValues.put("level", exercise.getLevel());
        initialValues.put("main_muscle", exercise.getMainMuscle());
        initialValues.put("other_muscles", exercise.getOtherMuscles());
        initialValues.put("equipment", exercise.getEquipment());
        initialValues.put("type", exercise.getType());
        initialValues.put("mechanics", exercise.getMechanics());
        initialValues.put("image_url", exercise.getImageUrl());
        return db.insert("exercise", null, initialValues);
    }

    // Insert a user_workout
    // ==================

    // Maybe change this to use the method without an object
    /*
    public long insertUserWorkout(int userId, int workoutId, double timeTaken) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("user_id", userId);
        initialValues.put("workout_id", workoutId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        initialValues.put("workout_date", dateFormat.format(date));
        initialValues.put("time_taken", timeTaken);
        return db.insert("user_workout", null, initialValues);
    }
    */

    public long insertUserWorkout(UserWorkout userWorkout) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("user_id", userWorkout.getUserId());
        initialValues.put("workout_id", userWorkout.getWorkoutId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        initialValues.put("workout_date", dateFormat.format(userWorkout.getWorkoutDate()));
        initialValues.put("time_taken", userWorkout.getTimeTaken());
        return db.insert("user_workout", null, initialValues);
    }

    public long insertUserWorkoutExercise(UserWorkoutExercise userWorkoutExercise) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("user_workout_id", userWorkoutExercise.getUserWorkoutId());
        initialValues.put("exercise_id", userWorkoutExercise.getExerciseId());
        initialValues.put("number_of_reps", userWorkoutExercise.getNumberOfReps());
        initialValues.put("number_of_sets", userWorkoutExercise.getNumberOfSets());
        initialValues.put("weight", userWorkoutExercise.getWeight());
        return db.insert("user_workout", null, initialValues);
    }

    // Insert a user and their routine
    // ===================

    // This will be used when first inserting a user into the database after they have signed up.
    // And also when the user generates a new workout.
    public void insertUserAndRoutine(User user) {

        // Check if the user already exists in the database
        if(isDataInDb("user", "email", "'" + user.getEmail() + "'")) {
            // User exists so just update his current routine.
            Log.v(TAG, "User already exists in the database");

        }

        else {
            // The user doesn't exist in the local database so add him and his routine.
            Log.v(TAG, "User doesn't exist in the database, so he will be added.");

            // Insert the routine and get it's ID
            long routineId = insertRoutine(user.getRoutine());
            user.getRoutine().setId(routineId);

            // Insert the user then add his Id to the user object.
            user.setId(insertUser(user));

            // Create a variable to store the workout's ID's
            long currentWorkoutId = 0;

            // Insert the workouts and the workout exercises
            for(Workout workout : user.getRoutine().getWorkouts()) {

                // Set the routine ID of each workout
                workout.setRoutineId(routineId);

                // Insert the workout and get it's current workouts ID
                currentWorkoutId = insertWorkout(workout);

                // For each exercise in the workout insert a workout exercise relationship
                // Also insert a user exercise relationship to store the current weight, reps and sets
                // for that user
                for(Exercise exercise : workout.getExerciseList()) {
                    insertWorkoutExercise(currentWorkoutId, exercise.getId());
                    insertUserExercise(user, exercise);
                }
            }

            Log.v(TAG, "User and routine has been successfully added");

        }




    }

    // Insert a user's exercise including their reps and sets
    // This table is used to keep track of a users progress on a particular exercise.
    // ===================
    public long insertUserExercise(User user, Exercise exercise) {

        ContentValues initialValues = new ContentValues();
        initialValues.put("user_id", user.getId());
        initialValues.put("exercise_id", exercise.getId());
        initialValues.put("weight", exercise.getWeight());
        initialValues.put("reps", exercise.getReps());
        initialValues.put("sets", exercise.getSets());
        return db.insert("user_exercise", null, initialValues);

    }


    // TODO continue here add the update user and routine
    // public long insert



    // Retrieving items from the database
    // ==================================
    // *****************

    // Retrieve an Arraylist of all the exercises for a particular muscle group
    public ArrayList<Exercise> getExercisesByMainMuscle(String mainMuscle) {
        String selectQuery = "SELECT * FROM exercise WHERE main_muscle = '" + mainMuscle + "'";
        Cursor mCursor = db.rawQuery(selectQuery, null);

        //Create the ArrayList of exercises that will be returned.
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();

        // Check if the cursor has returned results
        if(mCursor != null) {
            //Move to the first element of the returned results.
            mCursor.moveToFirst();
            Exercise currentExercise;
            //Loop through the retrieved results adding the exercises to the arraylist.
            for(int i = 0; i < mCursor.getCount(); i++) {

                currentExercise = new Exercise();

                currentExercise.setId(mCursor.getLong(mCursor.getColumnIndex("id")));
                currentExercise.setName(mCursor.getString(mCursor.getColumnIndex("name")));
                currentExercise.setServerId(mCursor.getInt(mCursor.getColumnIndex("server_id")));
                currentExercise.setDescription(mCursor.getString(mCursor.getColumnIndex("description")));
                currentExercise.setLevel(mCursor.getString(mCursor.getColumnIndex("level")));
                currentExercise.setMainMuscle(mCursor.getString(mCursor.getColumnIndex("main_muscle")));
                currentExercise.setOtherMuscles(mCursor.getString(mCursor.getColumnIndex("other_muscles")));
                currentExercise.setEquipment(mCursor.getString(mCursor.getColumnIndex("equipment")));
                currentExercise.setType(mCursor.getString(mCursor.getColumnIndex("type")));
                currentExercise.setMechanics(mCursor.getString(mCursor.getColumnIndex("mechanics")));
                currentExercise.setImageUrl(mCursor.getString(mCursor.getColumnIndex("image_url")));

                exercises.add(currentExercise);
                mCursor.moveToNext();
            }
        }

        return exercises;
    }

    // Retrieve an exercise from the database
    // =======
    public Exercise getExercise(long id) {
        Exercise exercise = new Exercise();

        Cursor mCursor = db.query(true, "exercise", null, "id=" + id, null, null, null, null, null);
        if(mCursor != null) {
            mCursor.moveToFirst();

            exercise.setId(mCursor.getLong(mCursor.getColumnIndex("id")));
            exercise.setName(mCursor.getString(mCursor.getColumnIndex("name")));
            exercise.setServerId(mCursor.getInt(mCursor.getColumnIndex("server_id")));
            exercise.setDescription(mCursor.getString(mCursor.getColumnIndex("description")));
            exercise.setLevel(mCursor.getString(mCursor.getColumnIndex("level")));
            exercise.setMainMuscle(mCursor.getString(mCursor.getColumnIndex("main_muscle")));
            exercise.setOtherMuscles(mCursor.getString(mCursor.getColumnIndex("other_muscles")));
            exercise.setEquipment(mCursor.getString(mCursor.getColumnIndex("equipment")));
            exercise.setType(mCursor.getString(mCursor.getColumnIndex("type")));
            exercise.setMechanics(mCursor.getString(mCursor.getColumnIndex("mechanics")));
            exercise.setImageUrl(mCursor.getString(mCursor.getColumnIndex("image_url")));
        }

        return exercise;

    }


    public User getUserAndRoutine(String userEmail) {

        // Create a user pointer
        User user = new User();

        // Create the query to retrieve the user object.
        String selectQuery = "SELECT * FROM user WHERE email = '" + userEmail + "'";

        Cursor mCursor;
        mCursor = db.rawQuery(selectQuery, null);

        if(mCursor != null) {
            // Cursor returned the specified user object

            mCursor.moveToFirst();

            // Add the users information to the user object
            user.setId(mCursor.getLong(mCursor.getColumnIndex("id")));
            user.setEmail(mCursor.getString(mCursor.getColumnIndex("email")));
            user.setAuthorizationToken(mCursor.getString(mCursor.getColumnIndex("authentication_token")));
            user.setAge(mCursor.getInt(mCursor.getColumnIndex("age")));
            user.setGender(mCursor.getString(mCursor.getColumnIndex("gender")));
            user.setHeight(mCursor.getDouble(mCursor.getColumnIndex("height")));
            user.setWeight(mCursor.getDouble(mCursor.getColumnIndex("weight")));
            //user.setPoints(mCursor.getInt(mCursor.getColumnIndex("points")));

            // Get the users routine
            Routine routine = getRoutine(mCursor.getLong(mCursor.getColumnIndex("routine_id")));
            user.setRoutine(routine);

            // Get the user's individual exercise weights, reps, and sets.
            // Couldn't avoid the double loop.
            for(Workout workout : routine.getWorkouts()) {

                for(Exercise exercise: workout.getExerciseList()) {
                    // Pass in the current exercise and then return it from the function with the correct
                    // weight's, reps, and sets for the user.
                    exercise = getUserExerciseData(user, exercise);

                }
            }

        }
        return user;
    }


    // A method that returns the workout with a specified ID this includes all it's exercises.
    public Workout getWorkout(long id) {

        Workout workout = new Workout();

        String selectQuery = "SELECT * FROM workout WHERE id = " + id;
        Cursor mCursor = db.rawQuery(selectQuery, null);

        if(mCursor != null) {

            // The cursor returned a workout.
            mCursor.moveToFirst();

            // Add the workout information to the workout
            workout.setId(mCursor.getLong(mCursor.getColumnIndex("id")));
            workout.setName(mCursor.getString(mCursor.getColumnIndex("name")));
            workout.setDay(mCursor.getString(mCursor.getColumnIndex("description")));
            workout.setRoutineId(mCursor.getLong(mCursor.getColumnIndex("routine_id")));

            // Retrieve the workout's exercises
            //selectQuery = "SELECT * FROM exercise, workout_exercise, workout WHERE "

            // Create a query to retrieve the exercises
            selectQuery = "SELECT exercise.* FROM exercise " +
                          "JOIN workout_exercise ON exercise.id = workout_exercise.exercise_id " +
                          "JOIN workout ON workout.id = workout_exercise.workout_id " +
                          "WHERE workout.id = " + workout.getId();


            mCursor = db.rawQuery(selectQuery, null);

            // Check if the cursor has returned results
            if(mCursor != null) {
                //Move to the first element of the returned results.
                mCursor.moveToFirst();
                Exercise currentExercise;
                //Loop through the retrieved results adding the exercises to the workout.
                for(int i = 0; i < mCursor.getCount(); i++) {

                    currentExercise = new Exercise();

                    currentExercise.setId(mCursor.getLong(mCursor.getColumnIndex("id")));
                    currentExercise.setName(mCursor.getString(mCursor.getColumnIndex("name")));
                    currentExercise.setServerId(mCursor.getInt(mCursor.getColumnIndex("server_id")));
                    currentExercise.setDescription(mCursor.getString(mCursor.getColumnIndex("description")));
                    currentExercise.setLevel(mCursor.getString(mCursor.getColumnIndex("level")));
                    currentExercise.setMainMuscle(mCursor.getString(mCursor.getColumnIndex("main_muscle")));
                    currentExercise.setOtherMuscles(mCursor.getString(mCursor.getColumnIndex("other_muscles")));
                    currentExercise.setEquipment(mCursor.getString(mCursor.getColumnIndex("equipment")));
                    currentExercise.setType(mCursor.getString(mCursor.getColumnIndex("type")));
                    currentExercise.setMechanics(mCursor.getString(mCursor.getColumnIndex("mechanics")));
                    currentExercise.setImageUrl(mCursor.getString(mCursor.getColumnIndex("image_url")));
                    // currentExercise.setWeight(mCursor.getDouble(mCursor.getColumnIndex("weight")));
                    //currentExercise.setSets(mCursor.getInt(mCursor.getColumnIndex("sets")));
                    //currentExercise.setReps(mCursor.getInt(mCursor.getColumnIndex("reps")));

                    workout.addExercise(currentExercise);
                    mCursor.moveToNext();
                }
            }

        }

        // Return the workout
        return workout;
    }

    // Retrieve a routine including it's workouts and exercises
    public Routine getRoutine(long id) {

        // Create a new routine
        Routine routine = new Routine();

        // Retrieve the routine's information
        String selectQuery = "SELECT * FROM routine WHERE id = " + id;

        Cursor mCursor = db.rawQuery(selectQuery, null);
        if(mCursor != null) {

            mCursor.moveToFirst();

            routine.setId(mCursor.getLong(mCursor.getColumnIndex("id")));
            Log.v(TAG, "routine id is: " + routine.getId());
            routine.setName(mCursor.getString(mCursor.getColumnIndex("name")));
            routine.setDescription(mCursor.getString(mCursor.getColumnIndex("description")));

            selectQuery = "SELECT * FROM workout WHERE routine_id = " + routine.getId();
            mCursor = db.rawQuery(selectQuery, null);

            if(mCursor != null) {

                mCursor.moveToFirst();

                for(int i = 0; i < mCursor.getCount(); i++) {
                    routine.addWorkout(getWorkout(mCursor.getLong(mCursor.getColumnIndex("id"))));
                    mCursor.moveToNext();
                }
            }
        }

        return routine;
    }

    // Get the weight, reps , and sets of a particular exercise a user is doing.
    // It accepts a user object and a exercise object. Then it retrieves the user_exercise information
    // updates the exercise objects and returns it with weight, sets, and reps info.
    public Exercise getUserExerciseData(User user, Exercise exercise) {

        long userId = user.getId();
        long exerciseId = exercise.getId();

        // Retrieve the user_exercise
        String selectQuery = "SELECT user_exercise.weight, user_exercise.reps, user_exercise.sets FROM user_exercise " +
                             "WHERE user_exercise.user_id = " + userId + " AND user_exercise.exercise_id = " + exerciseId;

        Cursor mCursor = db.rawQuery(selectQuery, null);
        // Check if the cursor returned an entry.
        if(mCursor != null) {

            // entry was returned so proceed.
            mCursor.moveToFirst();

            // Set the passed in exercise's weight, reps, and sets.
            exercise.setWeight(mCursor.getDouble(mCursor.getColumnIndex("weight")));
            exercise.setReps(mCursor.getInt(mCursor.getColumnIndex("reps")));
            exercise.setSets(mCursor.getInt(mCursor.getColumnIndex("sets")));


        }
        else {
            Log.v(TAG, "getUserExerciseData() returned a null object");
        }

        // Return the passed in exercise.
        return exercise;

    }




    /*
    public User getUser(String userEmail) {

        String selectQuery = "SELECT * FROM user WHERE email = '" + userEmail + "'";
        Log.v("DBAdapter", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null)
            c.moveToFirst();

        User user = new User();
        user.setEmail(c.getString(c.getColumnIndex("email")));
        // continue here using this tutorial http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/

    }
    */










    // Check if a user has a workout
    // ******* continue here
    //public boolean hasWorkout(String userEmail) {
    //String dbQuery = "Select"
    //}
}
