package com.example.daliyevolution.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daliyevolution.R;
import com.example.daliyevolution.base.BaseFragment;
import com.example.daliyevolution.function.Adapter_transaction;
import com.example.daliyevolution.model.Tb_transaction;
import com.example.daliyevolution.ui.Activity_transaction_additem;
import com.example.daliyevolution.ui.Activity_transaction_type;
import com.example.daliyevolution.util.Db_config;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;

/***
 * Fragment_transaction
 * transaction part basic activities
 * @author Guanjie Huang
 * @ID u6532079
 * @Check&Modify Chao Zhang
 * @ID u6545192
 */


public class Fragment_transaction extends BaseFragment {

    private TextView tv_money_time;
    private TextView tv_money_out;
    private TextView tv_money_in;
    private SegmentedGroup segmentedGroup;
    private ListView listview_money;
    private LinearLayout line_add;
    private List<List<Tb_transaction>> list_money;
    private Adapter_transaction adapter_transaction;
    private String nowDate;//Current date
    private double inTotal;//Total income in a month
    private double outTotal;//Total spending in a month
    private int i = 0;// recording back time, avoid twice back
    private DbManager.DaoConfig daoConfig = Db_config.getDaoConfig();
    private DbManager db = x.getDb(daoConfig);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        initView(view);
        //add adapter to ListView
        initAdapter();
        showData();
        dateOnclick();
        segmentSetListener();
        addOnclick();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        } else {
            if (data.getStringExtra("isClear") != null) {
                //refresh
                refreshListView(nowDate);
            }
            if (data.getStringExtra("isType") != null) {
                segmentedGroup.check(R.id.bt_detail);
                i = 0;
            }
        }
    }

    private void initView(View view) {
        tv_money_time = (TextView) view.findViewById(R.id.tv_money_time);
        tv_money_out = (TextView) view.findViewById(R.id.tv_money_out);
        tv_money_in = (TextView) view.findViewById(R.id.tv_money_in);
        segmentedGroup = (SegmentedGroup) view.findViewById(R.id.seg_money);
        listview_money = (ListView) view.findViewById(R.id.lv_money);
        line_add = (LinearLayout) view.findViewById(R.id.line_add);
        //get current Date
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM");
        nowDate = format.format(date);
    }

    private void initAdapter() {
        list_money = getMyDate(nowDate);
        adapter_transaction = new Adapter_transaction(context, R.layout.fragment_transaction_listview, list_money, this);
        listview_money.setAdapter(adapter_transaction);
        if (list_money.isEmpty()) {
            Toast.makeText(context, "No data yet", Toast.LENGTH_SHORT).show();
        }
    }

    //get set date's List<List<Tb_transaction>>
    private List<List<Tb_transaction>> getMyDate(String myDate) {

        List<List<Tb_transaction>> list_money = new ArrayList<List<Tb_transaction>>();
        for (int i = 1; i < 32; i++) {
            String date = myDate + "-" + String.format("%02d", i);
            try {
                List<Tb_transaction> list_money_item = db.selector(Tb_transaction.class).where("time", "=", date).findAll();
                if (list_money_item != null) {
                    if (!list_money_item.isEmpty()) {
                        list_money.add(list_money_item);
                    }
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        return list_money;
    }

    private void showData() {
        //show current month
        tv_money_time.setText(nowDate);
        //Calculate total income and spending in this month
        inTotal = 0;
        outTotal = 0;
        for (List<Tb_transaction> list : list_money) {
            for (Tb_transaction tb_money : list) {
                if (tb_money.getInorout().equals("Income")) {
                    inTotal = inTotal + tb_money.getAmount();
                } else {
                    outTotal = outTotal + tb_money.getAmount();
                }
            }
        }
        //Show total income and spending in this month
        String str_in = Double.toString(inTotal);
        String str_out = Double.toString(outTotal);
        tv_money_in.setText("Income:" + str_in.substring(0, str_in.indexOf(".") +2));
        tv_money_out.setText("Spend:" + str_out.substring(0, str_out.indexOf(".") +2));
    }

    private void dateOnclick() {
        tv_money_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(context).inflate(R.layout.data_picker_year, null);
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker_year);
                //hidden date
                View dayPickerView = datePicker.findViewById(getContext().getResources().getIdentifier("android:id/day", null, null));
                if (dayPickerView != null) {
                    dayPickerView.setVisibility(View.GONE);
                }
                //date
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setView(view);
                alert.setTitle("Please choose a date.");
                alert.setCancelable(false);
                alert.setPositiveButton(" Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth() + 1;
                        nowDate = year + "-" + String.format("%02d", month);
                        refreshListView(nowDate);
                    }
                });
                alert.show();
            }
        });
    }

    private void segmentSetListener() {
        segmentedGroup.setOnCheckedChangeListener(new myOnCheckedChangeListener());
        //Sets the default check detail
        segmentedGroup.check(R.id.bt_detail);
    }

    class myOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup Group, @IdRes int checkedId) {
            switch (checkedId) {
                case R.id.bt_detail:
                    break;
                case R.id.bt_type:
                    if (i == 0) {
                        i = 1;
                        //initial Activity
                        Intent intent = new Intent(context, Activity_transaction_type.class);
                        intent.putExtra("nowDate", nowDate);
                        startActivityForResult(intent, 0);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void addOnclick() {
        line_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_transaction_additem.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    //Refresh List view
    private void refreshListView(String date) {
        list_money.clear();
        adapter_transaction.notifyDataSetChanged();
        List<List<Tb_transaction>> myList = getMyDate(date);
        for (List<Tb_transaction> list : myList) {
            list_money.add(list);
        }
        adapter_transaction.notifyDataSetChanged();
        showData();
    }


}
