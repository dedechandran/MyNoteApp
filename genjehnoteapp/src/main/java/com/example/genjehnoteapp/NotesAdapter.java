package com.example.genjehnoteapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class NotesAdapter extends CursorAdapter {
    @BindViews({R.id.tv_note_date,R.id.tv_note_title,R.id.tv_note_description})
    List<TextView> noteProperties;


    public NotesAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_genjeh_note,parent,false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ButterKnife.bind(this,view);
        if(cursor!=null){
            noteProperties.get(0).setText(DatabaseContract.getColumnString(cursor,DatabaseContract.NotesColumns.TITLE));
            noteProperties.get(1).setText(DatabaseContract.getColumnString(cursor,DatabaseContract.NotesColumns.DESCRIPTION));
            noteProperties.get(2).setText(DatabaseContract.getColumnString(cursor,DatabaseContract.NotesColumns.DATE));
        }
    }
}
