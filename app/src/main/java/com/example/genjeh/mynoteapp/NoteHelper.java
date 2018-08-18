package com.example.genjeh.mynoteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class NoteHelper {
    private Context context;
    private SQLiteDatabase database;

    public NoteHelper(Context context) {
        this.context = context;
    }

    public NoteHelper open() throws SQLException{
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        database.close();
    }

    public ArrayList<Note> query(){
        ArrayList<Note> notes = new ArrayList<>();
        Cursor cursor = database.query(DatabaseContract.TABLE_NAME,null,null,null,null,null,DatabaseContract.NotesColumns._ID+" DESC",null);
        cursor.moveToFirst();
        Note note;
        if(cursor.getCount()>0){
            do{
                note = new Note();
                note.setNoteID(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.NotesColumns._ID)));
                note.setNoteTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NotesColumns.TITLE)));
                note.setNoteDesc(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NotesColumns.DESCRIPTION)));
                note.setNoteDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.NotesColumns.DATE)));

                notes.add(note);
                cursor.moveToNext();
            }while(!cursor.isAfterLast());
        }

        cursor.close();
        return notes;
    }

    public long insert(Note note){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.NotesColumns.TITLE,note.getNoteTitle());
        contentValues.put(DatabaseContract.NotesColumns.DESCRIPTION,note.getNoteDesc());
        contentValues.put(DatabaseContract.NotesColumns.DATE,note.getNoteDate());
        return database.insert(DatabaseContract.TABLE_NAME,null,contentValues);
    }

    public int delete(int id){
        return database.delete(DatabaseContract.TABLE_NAME,DatabaseContract.NotesColumns._ID + " = '"+id+"'",null);
    }

    public int update(Note note){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.NotesColumns.TITLE,note.getNoteTitle());
        contentValues.put(DatabaseContract.NotesColumns.DESCRIPTION,note.getNoteDesc());
        contentValues.put(DatabaseContract.NotesColumns.DATE,note.getNoteDate());
        return database.update(DatabaseContract.TABLE_NAME,contentValues,DatabaseContract.NotesColumns._ID + " = '"+note.getNoteID()+"'",null);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DatabaseContract.TABLE_NAME,null,DatabaseContract.NotesColumns._ID + " = ?" , new String[]{id},null,null,null,null);
    }

    public Cursor queryProvider(){
        return database.query(DatabaseContract.TABLE_NAME,null,null ,null,null,null,DatabaseContract.NotesColumns._ID + " DESC",null);
    }

    public long insertProvider(ContentValues contentValues){
        return database.insert(DatabaseContract.TABLE_NAME,null,contentValues);
    }

    public int updateProvider(String id,ContentValues contentValues){
        return database.update(DatabaseContract.TABLE_NAME,contentValues,DatabaseContract.NotesColumns._ID + " = ?",new String[]{id});
    }

    public int deleteProvider(String id){
        return database.delete(DatabaseContract.TABLE_NAME,DatabaseContract.NotesColumns._ID + " = ?",new String[]{id});
    }


}
