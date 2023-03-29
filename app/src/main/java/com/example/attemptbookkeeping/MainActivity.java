package com.example.attemptbookkeeping;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.attemptbookkeeping.Database.DBTableHelper;
import com.example.attemptbookkeeping.Database.NotebookDBhelper;
import com.example.attemptbookkeeping.MainPage.CreateTableDialog;
import com.example.attemptbookkeeping.MainPage.ModifyTableDialog;
import com.example.attemptbookkeeping.MainPage.NoteListAdapter;
import com.example.attemptbookkeeping.tools.DataHolder;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    CreateTableDialog createTableD;// 新建表的弹窗
    ModifyTableDialog modifyTableD;// 修改表的弹窗
    DBTableHelper DBtable;
    NotebookDBhelper logDB;
    ArrayList<com.example.attemptbookkeeping.MainPage.notebook> notebooks_list;
    Context mc;
    static ArrayList<String> tasks = new ArrayList<>();
    static NoteListAdapter noteAdapter;

    Button ModifyCancel;
    Button ModifyDelete;
    Button Modifysave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mc = this;
        // 数据库
        logDB = new NotebookDBhelper(mc);
        DBtable = new DBTableHelper(this);
        this.notebooks_list = viewAllRecords();

        noteAdapter = new NoteListAdapter(this, this.notebooks_list);
        ListView listView = findViewById(R.id.noteListView);
        listView.setAdapter(noteAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long
                    id) {
                com.example.attemptbookkeeping.MainPage.notebook currentNote = notebooks_list.get(position);
                String click_name = currentNote.getName();

                DataHolder.getInstance().setItem(click_name);

                Intent intent = new Intent(mc, DetailNewActivity.class);
                intent.putExtra("nName", click_name);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int
                    position, long id) {
                com.example.attemptbookkeeping.MainPage.notebook currentNote = notebooks_list.get(position);
                String click_name = currentNote.getName();
                show(click_name);
                ModifyCancel = testView.findViewById(R.id.btn_cancel_m1);
                ModifyCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hide();
                    }
                });
                ModifyDelete = testView.findViewById(R.id.btn_delete);
                ModifyDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DBtable.deleteData(click_name);
                        // 还需要删除对应账本表的逻辑

                        logDB.deleteTable(click_name);

                        notebooks_list.clear();
                        notebooks_list.addAll(viewAllRecords());
//                        setNewData(viewAllRecords());
                        noteAdapter.notifyDataSetChanged();
                        hide();
                    }
                });
                Modifysave = testView.findViewById(R.id.btn_save_m1);
                Modifysave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText modifyname = testView.findViewById(R.id.new_table_name_m1);
                        EditText modifyinfo = testView.findViewById(R.id.new_table_info_m1);
                        String new_table_name = modifyname.getText().toString();
                        String new_table_info = modifyname.getText().toString();
                        if(modifyname.length() == 0){
                            Toast.makeText(mc, R.string.alert_empty_name, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(isStartWithNumber(new_table_name)){
                            Toast.makeText(mc, R.string.alert_name_number, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        DBtable.updateData(click_name, new_table_name, new_table_info);
                        //还需要修改对应账本表名的逻辑
                        logDB.renameTable(click_name, new_table_name);
                        // 更新notebook的显示
                        notebooks_list.clear();
                        notebooks_list.addAll(viewAllRecords());
//                        setNewData(viewAllRecords());
                        noteAdapter.notifyDataSetChanged();
                        hide();
                    }
                });
                /*
                AlertDialog.Builder builder = new AlertDialog.Builder(mc);
                builder.setIcon(null);//设置图标, 这里设为空值
                builder.setTitle(R.string.title_delete);
                builder.setMessage(getString(R.string.check_delete) + click_name );
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface arg0, int arg1){

                        DBtable.deleteData(click_name);
                        // 还需要删除对应账本表的逻辑

                        logDB.deleteTable(click_name);

                        notebooks_list.clear();
                        notebooks_list.addAll(viewAllRecords());
//                        setNewData(viewAllRecords());
                        noteAdapter.notifyDataSetChanged();



                    }
                });

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface arg0,int arg1){
                    }
                });
                AlertDialog b = builder.create();
                b.show();//显示对话框

                 */
                return true;

            }

        });
    }

    private WindowManager wm;
    private LinearLayout testView;

    public void show(String click_name)
    {
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);
        lp.gravity = Gravity.CENTER;
        lp.y = 0;
        lp.x = 0;
        testView = (LinearLayout) View.inflate(MainActivity.this,R.layout.layout_demo,null);
        wm.addView(testView,lp);


    }

    public void hide()
    {
        wm.removeView(testView);
    }




    /**
     * 读数据库 获取所有账本名&info,主要用于的更新显示recycleview
     */
    public ArrayList<com.example.attemptbookkeeping.MainPage.notebook> viewAllRecords() {
        Cursor res = DBtable.getAllData();
        ArrayList<com.example.attemptbookkeeping.MainPage.notebook> models = new ArrayList<>();
        if (res.getCount() == 0) {
            return models;
        }
        while (res.moveToNext()) {
            com.example.attemptbookkeeping.MainPage.notebook p = new com.example.attemptbookkeeping.MainPage.notebook();
            p.setName(res.getString(1));
            p.setDescription(res.getString(2));
            p.setImg(R.drawable.in_xinzi);
            models.add(p);
        }
        return models;
    }

    public void showEditDialog(View view) {
        createTableD = new CreateTableDialog(this, androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog,onCreateClickListener);
        createTableD.show();
    }

    public void showModified(View view) {
        modifyTableD = new ModifyTableDialog(this, androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog,onModifyClickListener);
        modifyTableD.show();
    }

    private final View.OnClickListener onModifyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_save_m:
                    String old_table_name = modifyTableD.old_table_name.getText().toString().trim();
                    String new_table_name = modifyTableD.new_table_name.getText().toString().trim();
                    if(old_table_name.length() == 0 || new_table_name.length() == 0){
                        Toast.makeText(mc, R.string.alert_empty_name, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(isStartWithNumber(new_table_name)){
                        Toast.makeText(mc, R.string.alert_name_number, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String table_info = modifyTableD.table_info.getText().toString().trim();
                    DBtable.updateData(old_table_name, new_table_name, table_info);

                    //还需要修改对应账本表名的逻辑
                    logDB.renameTable(old_table_name, new_table_name);


                    // 更新notebook的显示
                    notebooks_list.clear();
                    notebooks_list.addAll(viewAllRecords());
//                        setNewData(viewAllRecords());
                    noteAdapter.notifyDataSetChanged();

                    modifyTableD.dismiss();
                    break;
                case R.id.btn_get_info_m:
                    // 获取原来的描述
                    String old_table_name_2 = modifyTableD.old_table_name.getText().toString().trim();
                    if(old_table_name_2.length() == 0){
                        return;
                    }
                    String info = DBtable.getInfo(old_table_name_2);
                    modifyTableD.table_info.setText(info);
                    break;

                case R.id.btn_cancel_m:
                    modifyTableD.dismiss();
                    break;
            }
        }
    };


    private final View.OnClickListener onCreateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_save:
                    String table_name = createTableD.table_name.getText().toString().trim();
                    if(table_name.length() == 0){
                        Toast.makeText(mc,R.string.alert_empty_name, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(isStartWithNumber(table_name)){
                        Toast.makeText(mc,R.string.alert_name_number, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String table_info = createTableD.table_info.getText().toString().trim();
                    boolean isInserted = DBtable.insertData(table_name, table_info);
                    if (!isInserted){
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle(R.string.title_error);
                        builder.setMessage(R.string.alert_name_same);
                        builder.setPositiveButton(R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    { }
                                });
                        builder.create();
                        builder.show();
                        return;
                    }
                    //还需要新建对应账本表的逻辑
                    logDB.createTable(table_name);


                    // 更新notebook的显示

                    notebooks_list.clear();
                    notebooks_list.addAll(viewAllRecords());
//                        setNewData(viewAllRecords());
                    noteAdapter.notifyDataSetChanged();

                    createTableD.dismiss();
                    break;
                case R.id.btn_cancel:
                    createTableD.dismiss();
                    break;
            }
        }
    };

    public boolean isStartWithNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str.charAt(0)+"");
        return isNum.matches();
    }


    //表示当activity获取焦点时会调用的方法, 不知道为啥没有用
//    @Override
//    protected void onResume() {
//        super.onResume();
//        notebooks_list.clear();
//        notebooks_list.addAll(viewAllRecords());
//        noteAdapter.setNewData(viewAllRecords());
//        noteAdapter.notifyDataSetChanged();
//    }
}