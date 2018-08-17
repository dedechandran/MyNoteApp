package com.example.genjeh.mynoteapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private int noteID;
    private String noteTitle;
    private String noteDesc;
    private String noteDate;

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

    public Note() {
    }

    protected Note(Parcel in) {
        this.noteID = in.readInt();
        this.noteTitle = in.readString();
        this.noteDesc = in.readString();
        this.noteDate = in.readString();
    }

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
