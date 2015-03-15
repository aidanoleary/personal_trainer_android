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

    // Insert a user into the database
    // ==========
    public long insertUser(User user) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("email", user.getEmail());
        initialValues.put("authentication_token", user.getAuthorizationToken());
        initialValues.put("age", user.getAge());
        initialValues.put("gender", user.getGender());
        initialValues.put("routine_id", user.getRoutineId());
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
    public long insertWorkoutExercise(int workoutId, int exerciseId) {
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
        initialValues.put("id", exercise.getId());
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


    // Insert a user_workout_exercise
    // ==================
    /*
    public long insertUserWorkoutExercise(int userWorkoutId, int exerciseId, int numberOfReps, int numberOfSets, double weight) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("user_workout_id", userWorkoutId);
        initialValues.put("exercise_id", exerciseId);
        initialValues.put("number_of_reps", numberOfReps);
        initialValues.put("number_of_sets", numberOfSets);
        initialValues.put("weight", weight);
        return db.insert("user_workout", null, initialValues);
    }
    */

    public long insertUserWorkoutExercise(UserWorkoutExercise userWorkoutExercise) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("user_workout_id", userWorkoutExercise.getUserWorkoutId());
        initialValues.put("exercise_id", userWorkoutExercise.getExerciseId());
        initialValues.put("number_of_reps", userWorkoutExercise.getNumberOfReps());
        initialValues.put("number_of_sets", userWorkoutExercise.getNumberOfSets());
        initialValues.put("weight", userWorkoutExercise.getWeight());
        return db.insert("user_workout", null, initialValues);
    }


    /*
    // Retrieving items from the database
    // ==================================
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
