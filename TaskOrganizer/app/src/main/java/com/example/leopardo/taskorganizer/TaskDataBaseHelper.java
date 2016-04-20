package com.example.leopardo.taskorganizer;

import android.database.sqlite.*;
import android.content.Context;

public class TaskDataBaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EncryptedTaskDB.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_USERS =
            "CREATE TABLE " + TaskDataBase.UserEntry.TABLE_NAME + " (" +
                    TaskDataBase.UserEntry._ID + " INTEGER PRIMARY KEY," +
                    TaskDataBase.UserEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    TaskDataBase.UserEntry.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
                    TaskDataBase.UserEntry.COLUMN_NAME_PASSWORD + TEXT_TYPE  +
                    " )";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TaskDataBase.TaskEntry.TABLE_NAME + " (" +
                    TaskDataBase.TaskEntry._ID + " INTEGER PRIMARY KEY," +
                    TaskDataBase.TaskEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    TaskDataBase.TaskEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    TaskDataBase.TaskEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                    TaskDataBase.TaskEntry.COLUMN_NAME_DETAILS + TEXT_TYPE +
                    " )";
    /*private static final String SQL_CREATE_TESTUSER =
            "INSERT INTO " + TaskDataBase.UserEntry.TABLE_NAME + "(" +
                    TaskDataBase.UserEntry.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
                    TaskDataBase.UserEntry.COLUMN_NAME_PASSWORD + TEXT_TYPE  +
                    " )  VALUES (bla@bla.com, 317b32c143692b9939c197f6a5df54f9698df9a4882fe8bf19608968662be4fa)";*/

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TaskDataBase.TaskEntry.TABLE_NAME;
    private static final String SQL_DELETE_USERS =
            "DROP TABLE IF EXISTS " + TaskDataBase.UserEntry.TABLE_NAME;

    public TaskDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_ENTRIES);
        //db.execSQL(SQL_CREATE_TESTUSER);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_USERS);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
