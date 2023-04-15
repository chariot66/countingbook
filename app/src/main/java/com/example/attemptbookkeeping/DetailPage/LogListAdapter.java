package com.example.attemptbookkeeping.DetailPage;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.attemptbookkeeping.MainPage.notebook;
import com.example.attemptbookkeeping.R;
import com.example.attemptbookkeeping.tools.TranslateTool;

import java.util.ArrayList;
import java.util.Locale;

public class LogListAdapter extends ArrayAdapter<notelog> {

    TranslateTool tl;
    String language;

    public LogListAdapter(Activity context, ArrayList<notelog> tortoises, String lan){
        super(context, 0, tortoises);
        tl = new TranslateTool();
        language = lan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        //listItemView layout init
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.log_layout, parent, false);
        }
//        View listItemView = LayoutInflater.from(getContext()).inflate(R.layout.log_layout, parent, false);

        notelog currentName = getItem(position);

        TextView log_date = listItemView.findViewById(R.id.datetime);
        log_date.setText(currentName.getTime());


        TextView log_amount = listItemView.findViewById(R.id.amount);
        log_amount.setText(String.valueOf(currentName.getAmount()));

        TextView log_typeS = listItemView.findViewById(R.id.typeS);
        TextView log_type = listItemView.findViewById(R.id.type);

        if(language.equals("zh-CN")){
            log_typeS.setText(tl.getTransE(currentName.getTypeS()));
            log_type.setText(tl.getTransE(currentName.getType()));
        }
        else{
            log_typeS.setText(currentName.getTypeS());
            log_type.setText(currentName.getType());
        }

        return listItemView;
    }
}