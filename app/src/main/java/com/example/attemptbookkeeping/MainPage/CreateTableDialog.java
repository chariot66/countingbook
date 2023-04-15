package com.example.attemptbookkeeping.MainPage;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.attemptbookkeeping.R;

public class CreateTableDialog extends Dialog {

    /**
     *
     */
    Activity context;

    private Button btn_save, btn_cancel;

    public EditText table_name;

    public EditText table_info;

    private View.OnClickListener mClickListener;

    public CreateTableDialog(Activity context) {
        super(context);
        this.context = context;
    }

    public CreateTableDialog(Activity context, int theme, View.OnClickListener clickListener) {
        super(context, theme);
        this.context = context;
        this.mClickListener = clickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // layout set
        this.setContentView(R.layout.create_table);

        table_name = (EditText) findViewById(R.id.table_name);
        table_info = (EditText) findViewById(R.id.table_info);


        Window dialogWindow = this.getWindow();

        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // screen config, h & w
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // get current attribute
        // p.height = (int) (d.getHeight() * 0.6);
        p.width = (int) (d.getWidth() * 0.8); // set the width of the windows to total's 0.8
        dialogWindow.setAttributes(p);

        // get component by id
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        // add event
        btn_save.setOnClickListener(mClickListener);
        btn_cancel.setOnClickListener(mClickListener);

        this.setCancelable(true);
    }
}

