package com.example.genjeh.mynoteapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class FormAddUpdateActivity extends AppCompatActivity {
    @BindViews({R.id.edt_title,R.id.edt_description})
    List<EditText> form;

    @BindView(R.id.btn_submit)
    Button btnSubmit;

    public static String EXTRA_NOTE = "extra_note";
    public static String EXTRA_POSITION = "extra_position";

    private boolean isEdit = false;
    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;
    public static int REQUEST_UPDATE = 200;
    public static int RESULT_UPDATE = 201;
    public static int RESULT_DELETE = 301;

    private Note note;
    private int position;
    private NoteHelper noteHelper;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(noteHelper!=null){
            noteHelper.close();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_add_update);
        ButterKnife.bind(this);

        noteHelper = new NoteHelper(this);
        noteHelper.open();

        Uri uri = getIntent().getData();
        if(uri !=null){
            Log.d("Check:URI",uri.toString());
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            Log.d("Check:Cursor",cursor.toString());
            if(cursor!=null){
                if(cursor.moveToFirst()){
                    note = new Note(cursor);

                    cursor.close();
                }
            }
        }

        String actionBarTitle;
        String btnTitle;

        if(note!=null){
            isEdit=true;
            actionBarTitle = "Ubah";
            btnTitle = "Update";
            form.get(0).setText(note.getNoteTitle());
            form.get(1).setText(note.getNoteDesc());
        }else{
            actionBarTitle = "Tambah";
            btnTitle = "Simpan";
        }

        getSupportActionBar().setTitle(actionBarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnSubmit.setText(btnTitle);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(isEdit){
            getMenuInflater().inflate(R.menu.menu,menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
            case R.id.action_delete:
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
    }

    final int ALERT_DIALOG_CLOSE = 10;
    final int ALERT_DIALOG_DELETE = 20;

    private void showAlertDialog(int type){
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle,dialogMessage;

        if(isDialogClose){
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?";
        }else{
            dialogTitle = "Hapus Note";
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(dialogTitle);
        builder.setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(isDialogClose){
                            finish();
                        }else{
                            getContentResolver().delete(getIntent().getData(),null,null);
                            setResult(RESULT_DELETE);
                            finish();
                        }
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveData(){
        String title = form.get(0).getText().toString().trim();
        String description = form.get(1).getText().toString().trim();
        boolean isEmpty = false;

        if(TextUtils.isEmpty(title)){
            isEmpty = true;
            form.get(0).setError("Field cannot be blank");
        }

        if(TextUtils.isEmpty(description)){
            isEmpty = true;
            form.get(1).setError("Field cannot be blank");
        }

        if(!isEmpty){
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.NotesColumns.TITLE,title);
            contentValues.put(DatabaseContract.NotesColumns.DESCRIPTION,description);

            if(isEdit){
                getContentResolver().update(getIntent().getData(),contentValues,null,null);
                setResult(RESULT_UPDATE);
                finish();
            }else{
                contentValues.put(DatabaseContract.NotesColumns.DATE,getCurrentDate());
                getContentResolver().insert(DatabaseContract.CONTENT_URI,contentValues);
                setResult(RESULT_ADD);
                finish();
            }
        }

    }

    private String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
        Date date = new Date();

        return dateFormat.format(date);
    }
}
