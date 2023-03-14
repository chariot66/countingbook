package com.example.attemptbookkeeping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.attemptbookkeeping.Database.DBTableHelper;
import com.example.attemptbookkeeping.MainPage.CreateTableDialog;
import com.example.attemptbookkeeping.MainPage.ModifyTableDialog;
import com.example.attemptbookkeeping.MainPage.NoteAdapter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView noteRecyclerView;
    NoteAdapter noteAdapter;

    // 新建表的弹窗
    CreateTableDialog createTableD;

    // 修改表的弹窗
    ModifyTableDialog modifyTableD;

    DBTableHelper DBtable;

    ArrayList<com.example.attemptbookkeeping.notebook> notebooks_list;

    Context mc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mc = this;

        // 数据库
        DBtable = new DBTableHelper(this);
        this.notebooks_list = viewAllRecords();

        // 列表显示
        noteRecyclerView = findViewById(R.id.noteRecyclerView);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(this, this.notebooks_list, DBtable);
        noteRecyclerView.setAdapter(noteAdapter);
    }

    /**
     * 读数据库 获取所有账本名&info,主要用于的更新显示recycleview
     */
    public ArrayList<com.example.attemptbookkeeping.notebook> viewAllRecords() {
        Cursor res = DBtable.getAllData();
        ArrayList<com.example.attemptbookkeeping.notebook> models = new ArrayList<>();
        if (res.getCount() == 0) {
            return models;
        }
        while (res.moveToNext()) {
            com.example.attemptbookkeeping.notebook p = new com.example.attemptbookkeeping.notebook();
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

    private View.OnClickListener onModifyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_save_m:
                    String old_table_name = modifyTableD.old_table_name.getText().toString().trim();
                    String new_table_name = modifyTableD.new_table_name.getText().toString().trim();
                    if(old_table_name.length() == 0 || new_table_name.length() == 0){
                        Toast.makeText(mc,"名不可为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String table_info = modifyTableD.table_info.getText().toString().trim();
                    DBtable.updateData(old_table_name, new_table_name, table_info);

                    //还需要修改对应账本表名的逻辑




                    // 更新notebook的显示
                    noteAdapter.setNewData(viewAllRecords());
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


    private View.OnClickListener onCreateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_save:
                    String table_name = createTableD.table_name.getText().toString().trim();
                    if(table_name.length() == 0){
                        Toast.makeText(mc,"名不可为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String table_info = createTableD.table_info.getText().toString().trim();
                    boolean isInserted = DBtable.insertData(table_name, table_info);
                    if (!isInserted){
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("错误");
                        builder.setMessage("存在同名");
                        builder.setPositiveButton("OK",
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



                    // 更新notebook的显示
                    noteAdapter.setNewData(viewAllRecords());
                    noteAdapter.notifyDataSetChanged();
                    createTableD.dismiss();
                    break;
                case R.id.btn_cancel:
                    createTableD.dismiss();
                    break;
            }
        }
    };


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