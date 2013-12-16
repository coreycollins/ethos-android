package com.ethos.theethosapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class CustomAdapter extends ArrayAdapter<Comment>{
    private ArrayList<Comment> entries;
    private Activity activity;
 
    public CustomAdapter(Activity a, int textViewResourceId, ArrayList<Comment> entries) {
        super(a, textViewResourceId, entries);
        this.entries = entries;
        this.activity = a;
    }
 
    public static class ViewHolder{
        public TextView message;
        public TextView date;
        public TextView fromUsername;
        public ImageView icon;
        public ImageView locked;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if (v == null) {
            LayoutInflater vi =
                (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.rowlayout, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) v.findViewById(R.id.icon);
            holder.locked = (ImageView) v.findViewById(R.id.locked);
            holder.message = (TextView) v.findViewById(R.id.message);
            holder.fromUsername = (TextView) v.findViewById(R.id.fromUserName);
            holder.date = (TextView) v.findViewById(R.id.date);
            v.setTag(holder);
        }
        else
            holder=(ViewHolder)v.getTag();
 
        final Comment comment = entries.get(position);
        if (comment != null) {
            holder.message.setText(comment.getMessage());
            holder.date.setText(String.valueOf(comment.getDateCreated()));
            if (comment.isAngel()) {
            	holder.icon.setImageResource(R.drawable.angel_icon);
            } else {
            	holder.icon.setImageResource(R.drawable.devil_icon);
            }
            if (comment.isLocked()) {
            	holder.locked.setImageResource(R.drawable.locked);
            }
            if (!comment.isHidden()) {
            	holder.fromUsername.setText(comment.getFromUsername());
            } else {
            	holder.fromUsername.setText("Anonymous");
            }
        }
        return v;
    }
 
}