package com.example.attemptbookkeeping.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotebookDBhelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Notebook.db";
    private static final String COL_1 = "DATE";
    private static final String COL_2 = "TYPE"; // 收入支出
    private static final String COL_3 = "CLASS"; // 吃喝玩乐衣食住行
    private static final String COL_4 = "MONEY";
    private static final int DATABASED_VERSION = 1;

    String TABLE_NAME;
    public NotebookDBhelper(Context context) {
        // super(context, name, factor, version)
        super(context, DATABASE_NAME, null, DATABASED_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "DATE TEXT, TYPE TEXT, CLASS TEXT, MONEY REAL)");
    }

    public void createTable(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table " + name + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "DATE TEXT, TYPE TEXT, CLASS TEXT, MONEY REAL)");
    }

    public void deleteTable(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + name);
    }

    public void setTable(String name){
        TABLE_NAME = name;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    // Method to insert a record to the database
    public boolean insertData(/*String ID,*/Double amount, String ts, String t, String d) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        /*contentValues.put(COL_1,ID);*/
        contentValues.put(COL_1,d);
        contentValues.put(COL_2,ts);
        contentValues.put(COL_3,t);
        contentValues.put(COL_4,amount);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }
    // Method to show all the records
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public boolean renameTable(String old_n, String new_n){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("ALTER TABLE " + old_n + " RENAME TO " + new_n);
        return true;
    }

    // 更新还没写 下面这个表列没修改，对不上
//    // Method to update a record
//    public boolean updateData(String id, String DATE, String CLASS, String MONEY)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_1,id);
//        contentValues.put(COL_2,DATE);
//        contentValues.put(COL_3,CLASS);
//        contentValues.put(COL_4,MONEY);
//        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});
//        return true;
//    }
    // Method to delete a record
    public Integer deleteData (String date,String types, String type, Double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "DATE=? and TYPE=? and CLASS=? and MONEY=?", new String[] {date, types,type,String.valueOf(amount)});
    }
}
