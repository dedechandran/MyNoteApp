package com.example.genjehnoteapp;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_NAME = "note";
    public static String CREATE_TABLE = String.format(
      "CREATE TABLE %s"+
      " (%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
      " %s TEXT NOT NULL,"+
      " %s TEXT NOT NULL,"+
      " %s TEXT NOT NULL);"
            ,TABLE_NAME, NotesColumns._ID, NotesColumns.TITLE, NotesColumns.DESCRIPTION, NotesColumns.DATE
    );

    public static final class NotesColumns implements BaseColumns {
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String DATE = "date";
    }

    public static final String AUTHORITY = "com.example.genjeh.mynoteapp";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static String getColumnString(Cursor cursor,String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor,String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor,String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
