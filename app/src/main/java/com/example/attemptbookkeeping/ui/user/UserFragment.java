package com.example.attemptbookkeeping.ui.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.attemptbookkeeping.Database.NotebookDBhelper;
import com.example.attemptbookkeeping.DetailPage.LogListAdapter;
import com.example.attemptbookkeeping.DetailPage.notelog;
import com.example.attemptbookkeeping.MainPage.notebook;
import com.example.attemptbookkeeping.R;
import com.example.attemptbookkeeping.databinding.FragmentUserBinding;
import com.example.attemptbookkeeping.tools.DataHolder;

import java.util.ArrayList;

public class UserFragment extends Fragment {

    private FragmentUserBinding binding;
    LogListAdapter logAdapter;

    ArrayList<notelog> log_list;

    String[] log_type_list;

    CreateLogDialog createLogD;

    Context tc;

    NotebookDBhelper NDB;

    String notebook;

    View view_temp;
    String[] days,months,years,type,inoutcome;//Spinner数据
    String sel_day,sel_month,sel_year,sel_type,sel_inoutcome;//用来存储Spinner结果

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        tc = getContext();

        notebook = DataHolder.getInstance().getItem();

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        view_temp = view;


        NDB = new NotebookDBhelper(tc);
        NDB.setTable(notebook);

        this.log_list = viewAllLogs();
        this.log_type_list = this.getActivity().getResources().getStringArray(R.array.log_type);

        // Inflate the layout for this fragment

        //定义五个Spinner
        days = getResources().getStringArray(R.array.days_array);//从string获取列表内容
        Spinner daySpinner = (Spinner) view.findViewById(R.id.spinner1);//定义spinner
        ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, days);//设置adapter
        daySpinner.setAdapter(dayadapter);//下同
        months = getResources().getStringArray(R.array.months_array);
        Spinner monthSpinner = (Spinner) view.findViewById(R.id.spinner2);
        ArrayAdapter<String> monthadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, months);
        monthSpinner.setAdapter(monthadapter);
        years = getResources().getStringArray(R.array.years_array);
        Spinner yearSpinner = (Spinner) view.findViewById(R.id.spinner3);
        ArrayAdapter<String> yearadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, years);
        yearSpinner.setAdapter(yearadapter);
        type = getResources().getStringArray(R.array.type_array);
        Spinner typeSpinner = (Spinner) view.findViewById(R.id.spinner4);
        ArrayAdapter<String> typeadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, type);
        typeSpinner.setAdapter(typeadapter);
        inoutcome = getResources().getStringArray(R.array.inoutcome);
        Spinner inoutcomeSpinner = (Spinner) view.findViewById(R.id.spinner5);
        ArrayAdapter<String> inoutcomeadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, inoutcome);
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
        //定义五个Spinner

        //logAdapter = new LogListAdapter(getActivity(), this.log_list, "test");
        ListView listView = view.findViewById(R.id.userInputList);
        listView.setAdapter(logAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int
                    position, long id) {
                notelog currentLog = log_list.get(position);
                String date = currentLog.getTime();
                String typeS = currentLog.getTypeS();
                String type = currentLog.getType();
                Double amount = currentLog.getAmount();

                AlertDialog.Builder builder = new AlertDialog.Builder(tc);
                builder.setIcon(null);//设置图标, 这里设为空值
                builder.setTitle("删除");
                builder.setMessage("确定要删除吗");

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        NDB.deleteData(date, typeS, type, amount);
                        log_list.clear();
                        log_list.addAll(viewAllLogs());
//                        setNewData(viewAllRecords());
                        logAdapter.notifyDataSetChanged();

                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                AlertDialog b = builder.create();
                b.show();//显示对话框
                return true;
            }

        });


        Button add = view.findViewById(R.id.log_add);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showCLogDialog();
            }
        });
        Button search = view.findViewById(R.id.searchbtn);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Search();
            }
        });

        return view;
    }

    public void showCLogDialog() {
        createLogD = new CreateLogDialog(this.getActivity(), androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog,onCreateClickListener, log_type_list, view_temp);
        createLogD.show();
    }

    private final View.OnClickListener onCreateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.log_save:
                    String temp = createLogD.log_amount.getText().toString().trim();
                    if(temp.length() == 0){
                        Toast.makeText(tc,"金额不可为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    double amount = Double.parseDouble(temp);

                    String log_type = createLogD.tSpinner.getSelectedItem().toString().trim();
                    String log_date = createLogD.log_date.getText().toString().trim();

                    String log_typeS = "income";

                    if (createLogD.rg_log.getCheckedRadioButtonId() == R.id.radio_income){
                        log_typeS = "income";
                    }
                    else if (createLogD.rg_log.getCheckedRadioButtonId() == R.id.radio_spend){
                        log_typeS = "spend";
                    }

                    if(log_typeS.length() == 0){
                        Toast.makeText(tc,"选择类型", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    boolean isInserted = NDB.insertData(amount,log_typeS ,log_type,log_date);
                    if (!isInserted){
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(tc);
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

    private ArrayList<notelog> viewAllLogs() {
        Cursor res = NDB.getAllData();
        ArrayList<notelog> models = new ArrayList<>();
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

    //按日期、类别、收支查找
    public ArrayList<notelog> Search() {
        Cursor res = NDB.getAllData();
        ArrayList<notelog> models = new ArrayList<>();
        if (res.getCount() == 0) {
            Toast.makeText(tc,"NO RECORD", Toast.LENGTH_SHORT).show();//空列表
            return models;
        }
        while (res.moveToNext()) {
            notelog p = new notelog();
            String day = res.getString(1).substring(0,2);//截取Date前两位，和spinner选中的结果比较，下同
            String month = res.getString(1).substring(2,4);
            String year = res.getString(1).substring(4,8);
            String types = res.getString(2);
            String type = res.getString(3);
            //if比较spinner的结果和内容
            if((sel_year.equals(year)||sel_year.equals("ALL"))&&(sel_month.equals(month)||sel_month.equals("ALL"))&&(sel_day.equals(day)||sel_day.equals("ALL"))) {
                if(sel_type.equals(type)||sel_type.equals("ALL")){
                    if(sel_inoutcome.equals(types)||sel_inoutcome.equals("ALL")){
                        p.setTime(res.getString(1));
                        p.setTypeS(res.getString(2));
                        p.setType(res.getString(3));
                        p.setAmount(res.getDouble(4));
                        models.add(p);
                    }
                }
            }
        }
        log_list.clear();
        log_list.addAll(models);
//                        setNewData(viewAllRecords());
        logAdapter.notifyDataSetChanged();//显示数据
        return models;
    }
    //按日期、类别、收支查找

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}