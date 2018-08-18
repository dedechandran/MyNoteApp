package com.example.genjeh.mynoteapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rv_notes)
    RecyclerView rvNotes;

    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;

    private Cursor listNote;
    private NoteAdapter noteAdapter;
    private NoteHelper noteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        noteHelper = new NoteHelper(this);
        noteHelper.open();

        noteAdapter = new NoteAdapter(this);
        noteAdapter.setListNote(listNote);

        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setHasFixedSize(true);
        rvNotes.setAdapter(noteAdapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FormAddUpdateActivity.class);
                startActivityForResult(intent,FormAddUpdateActivity.REQUEST_ADD);
            }
        });

        new LoadNoteAsync().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FormAddUpdateActivity.REQUEST_ADD){
            if(resultCode == FormAddUpdateActivity.RESULT_ADD){
                new LoadNoteAsync().execute();
                showSnackBar("Satu item berhasil ditambahkan");
            }
        }else if(requestCode== FormAddUpdateActivity.REQUEST_UPDATE){
            if(resultCode == FormAddUpdateActivity.RESULT_UPDATE){
                new LoadNoteAsync().execute();
                showSnackBar("Satu item berhasil diubah");
            }else if(resultCode == FormAddUpdateActivity.RESULT_DELETE){
                new LoadNoteAsync().execute();
                showSnackBar("Satu item berhasil dihapus");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(noteHelper!=null){
            noteHelper.close();
        }
    }

    private class LoadNoteAsync extends AsyncTask<Void,Void,Cursor>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            listNote = cursor;
            noteAdapter.setListNote(listNote);
            noteAdapter.notifyDataSetChanged();
            if(listNote.getCount()==0){
                showSnackBar("tidak ada data saat ini");
            }
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(DatabaseContract.CONTENT_URI,null,null,null,null);
        }
    }


    private void showSnackBar(String message){
        Snackbar.make(rvNotes,message,Snackbar.LENGTH_SHORT).show();
    }

}
