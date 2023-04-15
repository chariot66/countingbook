package com.example.attemptbookkeeping.MainPage;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.attemptbookkeeping.R;
import com.example.attemptbookkeeping.MainPage.notebook;

import java.util.ArrayList;

public class NoteListAdapter extends ArrayAdapter<notebook> {
    public NoteListAdapter(Activity context, ArrayList<notebook> tortoises){
        super(context, 0, tortoises);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        //listItemView init
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.notebook_item_layout, parent, false);
        }

        // get data at position
        notebook currentName = getItem(position);

        TextView note_name = listItemView.findViewById(R.id.noteName);
        note_name.setText(currentName.getName());

        TextView note_info = listItemView.findViewById(R.id.noteDescription);
        note_info.setText(currentName.getDescription());

        return listItemView;
    }
}