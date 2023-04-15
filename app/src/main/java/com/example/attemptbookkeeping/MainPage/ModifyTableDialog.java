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

public class ModifyTableDialog extends Dialog {

    Activity context;

    private Button btn_save, btn_cancel, btn_get_info;

    public EditText old_table_name;

    public EditText table_info;

    private View.OnClickListener mClickListener;

    public ModifyTableDialog(Activity context) {
        super(context);
        this.context = context;
    }

    public ModifyTableDialog(Activity context, int theme, View.OnClickListener clickListener) {
        super(context, theme);
        this.context = context;
        this.mClickListener = clickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // layout set
        this.setContentView(R.layout.modify_table);

        old_table_name = (EditText) findViewById(R.id.old_table_name_m);
        table_info = (EditText) findViewById(R.id.table_info_m);
        Window dialogWindow = this.getWindow();

        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // screen config, h & w
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // get current attribute
        // p.height = (int) (d.getHeight() * 0.6);
        p.width = (int) (d.getWidth() * 0.8); // set the width of the windows to total's 0.8
        dialogWindow.setAttributes(p);

        // get component by id
        btn_save = (Button) findViewById(R.id.btn_save_m);
        btn_cancel = (Button) findViewById(R.id.btn_cancel_m);
        btn_get_info = (Button) findViewById(R.id.btn_modify_del);

        // add event
        btn_save.setOnClickListener(mClickListener);
        btn_cancel.setOnClickListener(mClickListener);
        btn_get_info.setOnClickListener(mClickListener);

        this.setCancelable(true);
    }
}
