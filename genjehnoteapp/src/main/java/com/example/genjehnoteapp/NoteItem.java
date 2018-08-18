package com.example.genjehnoteapp;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class NoteItem implements Parcelable {
    private int noteID;
    private String noteTitle;
    private String noteDesc;
    private String noteDate;

    public NoteItem (Cursor cursor){
        this.noteID = DatabaseContract.getColumnInt(cursor,DatabaseContract.NotesColumns._ID);
        this.noteTitle = DatabaseContract.getColumnString(cursor,DatabaseContract.NotesColumns.TITLE);
        this.noteDesc = DatabaseContract.getColumnString(cursor,DatabaseContract.NotesColumns.DESCRIPTION);
        this.noteDate = DatabaseContract.getColumnString(cursor,DatabaseContract.NotesColumns.DATE);
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDesc() {
        return noteDesc;
    }

    public void setNoteDesc(String noteDesc) {
        this.noteDesc = noteDesc;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.noteID);
        dest.writeString(this.noteTitle);
        dest.writeString(this.noteDesc);
        dest.writeString(this.noteDate);
    }

    protected NoteItem(Parcel in) {
        this.noteID = in.readInt();
        this.noteTitle = in.readString();
        this.noteDesc = in.readString();
        this.noteDate = in.readString();
    }

    public static final Parcelable.Creator<NoteItem> CREATOR = new Parcelable.Creator<NoteItem>() {
        @Override
        public NoteItem createFromParcel(Parcel source) {
            return new NoteItem(source);
        }

        @Override
        public NoteItem[] newArray(int size) {
            return new NoteItem[size];
        }
    };
}
