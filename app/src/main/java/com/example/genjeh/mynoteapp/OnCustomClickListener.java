package com.example.genjeh.mynoteapp;

import android.view.View;

public class OnCustomClickListener implements View.OnClickListener {
    private OnItemClickListener onItemClickListener;
    private int position;

    public OnCustomClickListener(int position,OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.position = position;
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.OnItemClicked(position,v);
    }

    interface OnItemClickListener{
        void OnItemClicked(int position,View view);
    }
}
