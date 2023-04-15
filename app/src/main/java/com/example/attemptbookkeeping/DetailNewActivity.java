package com.example.attemptbookkeeping;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.attemptbookkeeping.Database.NotebookDBhelper;
import com.example.attemptbookkeeping.DetailPage.LogListAdapter;
import com.example.attemptbookkeeping.DetailPage.notelog;
import com.example.attemptbookkeeping.tools.DataHolder;
import com.example.attemptbookkeeping.tools.TranslateTool;
import com.example.attemptbookkeeping.ui.user.CreateLogDialog;
import com.example.attemptbookkeeping.ui.user.UserViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class DetailNewActivity extends AppCompatActivity {
    LogListAdapter logAdapter;
    ArrayList<notelog> log_list;
    String[] log_type_list;
    CreateLogDialog createLogD;
    Context tc;
    NotebookDBhelper NDB;
    String notebook;
    String[] days,months,years,type,inoutcome;//Spinner
    String sel_day,sel_month,sel_year,sel_type,sel_inoutcome;//save Spinner result
    float food=0,Transport=0,Health=0,SocialLife=0,Entertainment=0,Living=0,sum=0,inout=0;
    TranslateTool tl;
    HashMap HME2C, HMC2E;
    String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_new);

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        tc = this;

        notebook = DataHolder.getInstance().getItem();


        NDB = new NotebookDBhelper(tc);
        NDB.setTable(notebook);

        this.log_list = viewAllLogs();
        this.log_type_list = this.getResources().getStringArray(R.array.log_type);

        //翻译键值对
        tl= new TranslateTool();
        HME2C = tl.getHMEng2Ch();
        HMC2E = tl.getHMCh2Eng();

        // 获取语言
        Locale locale = getResources().getConfiguration().locale;
//            //another version to get the device language, saved here in case accident happened
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                locale = getResources().getConfiguration().getLocales().get(0);
//            } else {
//                locale = getResources().getConfiguration().locale;
//            }
        language = locale.getLanguage()+"-"+locale.getCountry();;

        //5 spinner for selecting
        days = getResources().getStringArray(R.array.days_array);//从string获取列表内容
        Spinner daySpinner = (Spinner)findViewById(R.id.spinner1);//定义spinner
        ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, days);//设置adapter
        daySpinner.setAdapter(dayadapter);//下同
        months = getResources().getStringArray(R.array.months_array);
        Spinner monthSpinner = (Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<String> monthadapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, months);
        monthSpinner.setAdapter(monthadapter);
        years = getResources().getStringArray(R.array.years_array);
        Spinner yearSpinner = (Spinner)findViewById(R.id.spinner3);
        ArrayAdapter<String> yearadapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, years);
        yearSpinner.setAdapter(yearadapter);
        type = getResources().getStringArray(R.array.type_array);
        Spinner typeSpinner = (Spinner)findViewById(R.id.spinner4);
        ArrayAdapter<String> typeadapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, type);
        typeSpinner.setAdapter(typeadapter);
        inoutcome = getResources().getStringArray(R.array.inoutcome);
        Spinner inoutcomeSpinner = (Spinner)findViewById(R.id.spinner5);
        ArrayAdapter<String> inoutcomeadapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, inoutcome);
        inoutcomeSpinner.setAdapter(inoutcomeadapter);
        daySpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        int index = arg0.getSelectedItemPosition();
                        sel_day = days[index];
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
        monthSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        int index = arg0.getSelectedItemPosition();
                        sel_month = months[index];
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
        yearSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        int index = arg0.getSelectedItemPosition();
                        sel_year = years[index];
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });

        typeSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        int index = arg0.getSelectedItemPosition();
                        sel_type = type[index];
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
        inoutcomeSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        int index = arg0.getSelectedItemPosition();
                        sel_inoutcome = inoutcome[index];
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
        //5 spinner for selecting


        // the listview create logic, adapter
        logAdapter = new LogListAdapter(this, this.log_list, language);
        ListView listView = findViewById(R.id.userInputList);
        listView.setAdapter(logAdapter);

        // long click event, for list view del
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int
                    position, long id) {


                AlertDialog.Builder builder = new AlertDialog.Builder(tc);
                builder.setIcon(null);
                builder.setTitle(R.string.title_delete);
                builder.setMessage(R.string.check_delete);

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        notelog currentLog = log_list.get(position);
                        String date = currentLog.getTime();
                        String typeS = currentLog.getTypeS();
                        String type = currentLog.getType();
                        Double amount = currentLog.getAmount();

                        NDB.deleteData(date, typeS, type, amount);
                        log_list.clear();
                        log_list.addAll(viewAllLogs());
                        logAdapter.notifyDataSetChanged();

                    }
                });

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                AlertDialog b = builder.create();
                b.show();
                return true;
            }

        });
    }

    public void showCLogDialog(View view) {
        createLogD = new CreateLogDialog(this, androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog,onCreateClickListener, log_type_list);
        createLogD.show();
    }

    private final View.OnClickListener onCreateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.log_save:
                    String temp = createLogD.log_amount.getText().toString().trim();
                    if(temp.length() == 0){
                        Toast.makeText(tc, R.string.alert_amount_empty, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    double amount = Double.parseDouble(temp);

                    String log_type = createLogD.tSpinner.getSelectedItem().toString().trim();
                    if(language.equals("zh-CN")){
                        log_type = tl.getTransC(log_type);
                    }

                    String log_date = createLogD.log_date.getText().toString().trim();
                    if(log_date.length() != 8){
                        Toast.makeText(tc, R.string.alert_date_format, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    double log_day = Double.parseDouble(log_date.substring(0,2));//截取Date前两位
                    double log_month = Double.parseDouble(log_date.substring(2,4));
                    double log_year = Double.parseDouble(log_date.substring(4,8));

                    if(log_day>31||log_month>12||log_year>2023||log_year<2021){
                        Toast.makeText(tc, R.string.alert_date_invalid, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(log_day>30&&(log_month==4||log_month==6||log_month==9||log_month==11)){
                        Toast.makeText(tc,R.string.alert_date_invalid, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(log_day>28&&log_month==2){
                        Toast.makeText(tc,R.string.alert_date_invalid, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String log_typeS = "";

                    if (createLogD.rg_log.getCheckedRadioButtonId() == R.id.radio_income){
                        log_typeS = getString(R.string.income);
                        if(language.equals("zh-CN")){
                            log_typeS = tl.getTransC(log_typeS);
                        }
                    }
                    else if (createLogD.rg_log.getCheckedRadioButtonId() == R.id.radio_spend){
                        log_typeS = getString(R.string.spend);
                        if(language.equals("zh-CN")){
                            log_typeS = tl.getTransC(log_typeS);
                        }
                    }

                    if(log_typeS.length() == 0){
                        Toast.makeText(tc,R.string.alert_select_type, Toast.LENGTH_SHORT).show();
                        return;
                    }


                    boolean isInserted = NDB.insertData(amount,log_typeS ,log_type,log_date);
                    if (!isInserted){
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(tc);
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
                    // 更新notebook的显示

                    log_list.clear();
                    log_list.addAll(viewAllLogs());
//                        setNewData(viewAllRecords());
                    logAdapter.notifyDataSetChanged();

                    createLogD.dismiss();
                    break;
                case R.id.log_cancel:
                    createLogD.dismiss();
                    break;
            }
        }
    };
    ArrayList<notelog> models = new ArrayList<>();

    private ArrayList<notelog> viewAllLogs() {
        Cursor res = NDB.getAllData();
        models = new ArrayList<>();
        if (res.getCount() == 0) {
            return models;
        }
        while (res.moveToNext()) {
            notelog p = new notelog();
            p.setTime(res.getString(1));
            p.setTypeS(res.getString(2));
            p.setType(res.getString(3));
            p.setAmount(res.getDouble(4));
            models.add(p);
        }
        return models;
    }

    // search by specific confic (date, class, type)
    public ArrayList<notelog> Search(View view) {
        food=0;Transport=0;Health=0;SocialLife=0;Entertainment=0;Living=0;sum=0;inout=0;
        Cursor res = NDB.getAllData();
        models = new ArrayList<>();
        if (res.getCount() == 0) {
            Toast.makeText(tc, R.string.alert_no_record, Toast.LENGTH_SHORT).show();//空列表
            return models;
        }
        while (res.moveToNext()) {
            notelog p = new notelog();
            String day = res.getString(1).substring(0,2);//截取Date前两位，和spinner选中的结果比较，下同
            String month = res.getString(1).substring(2,4);
            String year = res.getString(1).substring(4,8);

            String types = res.getString(2);//in out
            String type = res.getString(3);


            if(language.equals("zh-CN")){
                types = tl.getTransE(types);
                type = tl.getTransE(type);
            }

            //compare the result and content
            if((sel_year.equals(year)||sel_year.equals(getString(R.string.all)))&&(sel_month.equals(month)||sel_month.equals(getString(R.string.all)))&&(sel_day.equals(day)||sel_day.equals(getString(R.string.all)))) {
                if(sel_type.equals(type)||sel_type.equals(getString(R.string.all))){
                    if(sel_inoutcome.equals(types)||sel_inoutcome.equals(getString(R.string.all))){
                        p.setTime(res.getString(1));
                        p.setTypeS(res.getString(2));
                        p.setType(res.getString(3));
                        p.setAmount(res.getDouble(4));
                        models.add(p);
                    }
                }
            }
            if((sel_year.equals(year)||sel_year.equals(getString(R.string.all)))&&(sel_month.equals(month)||sel_month.equals(getString(R.string.all)))&&(sel_day.equals(day)||sel_day.equals(getString(R.string.all)))) {
                if(types.equals(getString(R.string.income))&&sel_inoutcome.equals(getString(R.string.income))){
                    inout=1;
                    if(type.equals(getString(R.string.food))){
                        food = (float) (food + res.getDouble(4));
                    }else if (type.equals(getString(R.string.Transport))) {
                        Transport = (float) (Transport + res.getDouble(4));
                    }else if (type.equals(getString(R.string.Health))) {
                        Health = (float) (Health + res.getDouble(4));
                    }else if (type.equals(getString(R.string.SocialLife))) {
                        SocialLife = (float) (SocialLife + res.getDouble(4));
                    }else if (type.equals(getString(R.string.Entertainment))) {
                        Entertainment = (float) (Entertainment + res.getDouble(4));
                    }else if (type.equals(getString(R.string.Living))) {
                        Living = (float) (Living + res.getDouble(4));
                    }
                    sum = (float) (sum + res.getDouble(4));
                }else if(types.equals(getString(R.string.spend))&&sel_inoutcome.equals(getString(R.string.spend))){
                    inout=2;
                    if(type.equals(getString(R.string.food))){
                        food = (float) (food + res.getDouble(4));
                    }else if (type.equals(getString(R.string.Transport))) {
                        Transport = (float) (Transport + res.getDouble(4));
                    }else if (type.equals(getString(R.string.Health))) {
                        Health = (float) (Health + res.getDouble(4));
                    }else if (type.equals(getString(R.string.SocialLife))) {
                        SocialLife = (float) (SocialLife + res.getDouble(4));
                    }else if (type.equals(getString(R.string.Entertainment))) {
                        Entertainment = (float) (Entertainment + res.getDouble(4));
                    }else if (type.equals(getString(R.string.Living))) {
                        Living = (float) (Living + res.getDouble(4));
                    }
                    sum = (float) (sum + res.getDouble(4));
                }else if(sel_inoutcome.equals(getString(R.string.all))) {
                    inout=0;
                }

            }

        }
        log_list.clear();
        log_list.addAll(models);
        logAdapter.notifyDataSetChanged();//显示数据
        return models;
    }

    public void Pie(View view){
        if(inout==0){
            Toast.makeText(tc, R.string.alert_select_type, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(tc, PieChart.class);
        Bundle bundle = new Bundle();
        bundle.putFloat("inout", inout);
        bundle.putFloat("food", food);
        bundle.putFloat("Transport", Transport);
        bundle.putFloat("Health", Health);
        bundle.putFloat("SocialLife",SocialLife);
        bundle.putFloat("Entertainment", Entertainment);
        bundle.putFloat("Living", Living);
        bundle.putFloat("sum", sum);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}