package com.example.genjeh.mynoteapp;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_NAME = "note";
    public static String CREATE_TABLE = String.format(
      "CREATE TABLE %s"+
      " (%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
      " %s TEXT NOT NULL,"+
      " %s TEXT NOT NULL,"+
      " %s TEXT NOT NULL);"
            ,TABLE_NAME,NotesColumns._ID,NotesColumns.TITLE,NotesColumns.DESCRIPTION,NotesColumns.DATE
    );

    public static final class NotesColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String DATE = "date";
    }
}
