package com.aidanoleary.personaltrainer.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by aidanoleary on 14/01/2015.
 */
public class DBAdapter {

    static final String KEY_ROWID = "_id";
    static final String KEY_NAME = "name"; static final String KEY_EMAIL = "email"; static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "contacts";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE = "create table contacts (_id integer primary key autoincrement, " + "name text not null, email text not null);";
    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;


    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION); }
        @Override
        public void onCreate(SQLiteDatabase db) {
            //db.execSQL(DATABASE_CREATE);


            ContentValues initialValues = new ContentValues();
            initialValues.put("email", "aidanoleary92@gmail.com");
            initialValues.put("password", "hello123");

            db.insert("user", null, initialValues);

            Cursor query = db.query("user", new String[] {"email", "password"}, null, null, null, null, null);
            String queryEmail = "";
            //while (query.moveToNext()) {
            //    queryEmail = query.getString(query.getColumnIndex("email"));
            //}
            query.moveToFirst();
            queryEmail = query.getString(query.getColumnIndex("email"));
            Log.v("DBHelper", queryEmail);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
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


    //---insert a contact into the database---
    public long insertContact(String name, String email) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_EMAIL, email);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }



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
}
