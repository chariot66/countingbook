package com.example.attemptbookkeeping.ui.user;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.attemptbookkeeping.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateLogDialog extends Dialog {

    /**
     * 上下文对象 *
     */
    Activity context;


    String[] type_list;

    public RadioGroup rg_log;

    public Button log_save, log_cancel, radio_spend, radio_income;

    public EditText log_amount, log_date;

    public Spinner tSpinner;

    public View view_t;

    private View.OnClickListener mClickListener;

    public CreateLogDialog(Activity context) {
        super(context);
        this.context = context;
    }

    public CreateLogDialog(Activity context, int theme, View.OnClickListener clickListener, String[] list, View v) {
        super(context, theme);
        this.context = context;
        this.mClickListener = clickListener;
        this.type_list = list;
        this.view_t = v;
    }

    public CreateLogDialog(Activity context, int theme, View.OnClickListener clickListener, String[] list) {
        super(context, theme);
        this.context = context;
        this.mClickListener = clickListener;
        this.type_list = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.create_log);

        /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = this.getWindow();

        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);






        rg_log = (RadioGroup) findViewById(R.id.rg_log);

        log_amount = (EditText) findViewById(R.id.log_amount);

        log_date = (EditText) findViewById(R.id.log_date);

        // 根据id在布局中找到控件对象
        log_save = (Button) findViewById(R.id.log_save);
        log_cancel = (Button) findViewById(R.id.log_cancel);

        radio_spend = (Button) findViewById(R.id.radio_spend);
        radio_income = (Button) findViewById(R.id.radio_income);

        //        log_type = (EditText) findViewById(R.id.log_type);
        tSpinner = (Spinner) findViewById(R.id.log_type);
        List<String> typeas = new ArrayList<String>(Arrays.asList(type_list));
        ArrayAdapter<String> tadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, typeas);
        tSpinner.setAdapter(tadapter);


        // 为按钮绑定点击事件监听器
        log_save.setOnClickListener(mClickListener);
        log_cancel.setOnClickListener(mClickListener);

        this.setCancelable(true);
    }
}