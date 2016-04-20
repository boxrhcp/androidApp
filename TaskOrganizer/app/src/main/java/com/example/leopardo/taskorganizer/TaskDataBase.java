package com.example.leopardo.taskorganizer;

import android.provider.BaseColumns;

public class TaskDataBase {

    public TaskDataBase(){}

    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "Users";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
    }

    public static abstract class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "Task";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_DETAILS = "details";

    }
}
