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
import com.aidanoleary.personaltrainer.models.Workout;

import java.sql.SQLException;

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
        String dbQuery = "SELECT * FROM " + tableName + " WHERE " + dbfield + " = '" + fieldValue + "'";
        Cursor cursor = db.rawQuery(dbQuery, null);
        if(cursor.getCount() <= 0) {
            return false;
        }
        return true;
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
    public long insertWorkoutExercise(Workout workout, Exercise exercise) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("workout_id", workout.getId());
        initialValues.put("exercise_id", exercise.getId());
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











    // Check if a user has a workout
    // ******* continue here
    //public boolean hasWorkout(String userEmail) {
    //String dbQuery = "Select"
    //}
}
