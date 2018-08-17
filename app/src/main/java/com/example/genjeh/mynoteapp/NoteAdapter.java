package com.example.genjeh.mynoteapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{
    private Activity activity;
    private ArrayList<Note> listNote = new ArrayList<>();

    public NoteAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<Note> getListNote() {
        return listNote;
    }

    public void setListNote(ArrayList<Note> listNote) {
        this.listNote = listNote;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,parent,false);

        return new NoteViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.viewList.get(0).setText(getListNote().get(position).getNoteDate());
        holder.viewList.get(1).setText(getListNote().get(position).getNoteTitle());
        holder.viewList.get(2).setText(getListNote().get(position).getNoteDesc());
        holder.cvNote.setOnClickListener(new OnCustomClickListener(position, new OnCustomClickListener.OnItemClickListener() {
            @Override
            public void OnItemClicked(int position, View view) {
                Intent intent = new Intent(activity,FormAddUpdateActivity.class);
                intent.putExtra(FormAddUpdateActivity.EXTRA_NOTE,getListNote().get(position));
                intent.putExtra(FormAddUpdateActivity.EXTRA_POSITION,position);
                activity.startActivityForResult(intent,FormAddUpdateActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return getListNote().size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        @BindViews({R.id.tv_note_date,R.id.tv_note_title,R.id.tv_note_description})
        List<TextView> viewList;
        @BindView(R.id.cv_note)
        CardView cvNote;

        NoteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
