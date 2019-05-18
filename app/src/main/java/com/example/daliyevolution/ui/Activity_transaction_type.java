package com.example.daliyevolution.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daliyevolution.R;
import com.example.daliyevolution.base.BaseActivity;
import com.example.daliyevolution.function.Adapter_transaction_type;
import com.example.daliyevolution.model.Tb_transaction;
import com.example.daliyevolution.util.Db_config;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.rgb;

/***
 * Activity_transaction_type
 * activity for show data analyze page
 * @author Guanjie Huang
 * @ID u6532079
 */
@ContentView(R.layout.fragment_transaction_type)
public class Activity_transaction_type extends BaseActivity {
    private ImageView back;
    private Spinner inorout_spinner;
    private PieChart pieChart;
    private TextView tv_type;
    private TextView tv_transaction;
    private ListView type_listview;

    private List<List<Tb_transaction>> list_money;
    private List<Tb_transaction> list_item;
    private Adapter_transaction_type adapter_money_type;
    private List<Tb_transaction> list_inorout;
    private String nowDate;
    private String inorout;
    private String[] myType;
    private Double[] myValue;
    private int[] getValue = new int[]{0, 0, 0, 0, 0, 0, 0};
    private String Total;//Total income/spending
    private int[] outColorList = {rgb(60, 179, 113), rgb(131, 111, 255), rgb(255, 185, 15),
            rgb(30, 144, 255), rgb(99, 184, 255), rgb(255, 69, 0), rgb(205, 205, 205)};
    private int[] inColorList = {rgb(60, 179, 113), rgb(30, 144, 255), rgb(114, 188, 223), rgb(255, 185, 15),
            rgb(205, 205, 205)};

    private DbManager.DaoConfig daoConfig = Db_config.getDaoConfig();
    private DbManager db = x.getDb(daoConfig);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initial Fragment control panel
        initView();
        //receive data from forward page
        receiveData();
        //add issue in inorout_spinner
        inoroutOnclick();
        //add issues to pie chart
        pieChartOnclick();
        //add back activity for click bottom
        backOnclick();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.type_back);
        inorout_spinner = (Spinner) findViewById(R.id.type_inorout_spinner);
        pieChart = (PieChart) findViewById(R.id.spread_pie_chart);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_transaction = (TextView) findViewById(R.id.tv_money);
        type_listview = (ListView) findViewById(R.id.money_type_lv);
        //initialization ListView
        list_item = new ArrayList<>();
        list_inorout = new ArrayList<>();
        adapter_money_type = new Adapter_transaction_type(Activity_transaction_type.this, R.layout.fragment_transaction_type_listview, list_item);
        type_listview.setAdapter(adapter_money_type);
    }

    private void receiveData() {
        String mNowDate = getIntent().getStringExtra("nowDate");
        if (mNowDate == null) {
            Log.d("test", "...............failure to receive");
        } else {
            nowDate = mNowDate;
        }
        //get data i current date
        list_money = getMyDate(nowDate);
    }

    //get data in figured date List<List<Tb_money>>
    private List<List<Tb_transaction>> getMyDate(String myDate) {
        List<List<Tb_transaction>> list_money = new ArrayList<List<Tb_transaction>>();
        for (int i = 1; i <= 31; i++) {
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

    private void inoroutOnclick() {
        inorout_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inorout = (String) inorout_spinner.getSelectedItem();
                //get data of spend/income in this month
                list_inorout = getInorOut(inorout);
                if (!list_inorout.isEmpty()) {
                    //refresh ListView,tv_type and tv_money
                    tv_type.setText("");
                    tv_transaction.setText("");
                    list_item.clear();
                    adapter_money_type.notifyDataSetChanged();

                    drawPieChart(list_inorout);
                } else {
                    Toast.makeText(Activity_transaction_type.this, "no data", Toast.LENGTH_SHORT).show();
                    //if no data, refresh pie chart
                    pieChart.clear();
                    tv_type.setText("");
                    tv_transaction.setText("");
                    list_item.clear();
                    adapter_money_type.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //loop list_money，get spend/income data
    private List<Tb_transaction> getInorOut(String inorout) {
        List<Tb_transaction> mylist = new ArrayList<Tb_transaction>();
        for (List<Tb_transaction> list : list_money) {
            for (Tb_transaction tb_money : list) {
                if (tb_money.getInorout().equals(inorout)) {
                    mylist.add(tb_money);
                }
            }
        }
        return mylist;
    }

    private void drawPieChart(List<Tb_transaction> list) {
        List<List<String>> list_typeValue = getTypeAndValue(list);
        PieData mPieData = getPieData(list_typeValue);
        showPieChart(pieChart, mPieData);
    }

    //get different data
    private List<List<String>> getTypeAndValue(List<Tb_transaction> list) {
        List<List<String>> list_typeValue = new ArrayList<>();
        Double total = 0.0;
        if (inorout.equals("Spending")) {
            myType = new String[]{"food", "shopping", "entertainment", "transport", "communication", "medical", "others"};
            myValue = new Double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        } else {
            myType = new String[]{"salary", "part_time job", "interests", "gift", "others"};
            myValue = new Double[]{0.0, 0.0, 0.0, 0.0, 0.0};
        }
        //Loop list，Classify the type and total value of the type
        for (Tb_transaction tb_transaction : list) {
            for (int i = 0; i < myType.length; i++) {
                if (tb_transaction.getType().equals(myType[i])) {
                    myValue[i] = myValue[i] + tb_transaction.getAmount();
                }
            }
            total = total + tb_transaction.getAmount();
        }
        //Calculate the percentage of each type
        int percentTotal = 0;
        for (int i = 0; i < myType.length; i++) {
            List<String> list_str = new ArrayList<>();
            if (total != 0.0) {
                int mPercent = (int) (myValue[i] * 100000.0 / total);
                percentTotal = percentTotal + mPercent;
                list_str.add(myType[i]);
                list_str.add(Integer.toString(mPercent));
            }
            list_typeValue.add(list_str);
        }
        //save the total data
        String totalStr = Double.toString(total);
        Total = totalStr.substring(0, totalStr.indexOf(".") + 2);
        return list_typeValue;
    }

    private PieData getPieData(List<List<String>> list) {
        ArrayList<String> xValues = new ArrayList<String>(); //show different category data
        ArrayList<Entry> yValue = new ArrayList<Entry>(); //show actual data
        ArrayList<Integer> colors = new ArrayList<Integer>();//pie chart colour
        //Loop list,Get the type and corresponding value inside
        int i = 0;
        int j = 0;
        for (List<String> strList : list) {
            float yStrValue = Float.parseFloat(strList.get(1)) / 1000;
            if (yStrValue != 0.0) {
                getValue[i] = j;
                xValues.add(strList.get(0));
                yValue.add(new Entry(yStrValue, i));
                if (inorout.equals("Spending")) {
                    colors.add(outColorList[j]);
                } else {
                    colors.add(inColorList[j]);
                }
                i++;
            }
            j++;
        }
        PieDataSet pieDataSet = new PieDataSet(yValue, nowDate + "Type statistics");
        pieDataSet.setSliceSpace(0f);

        pieDataSet.setColors(colors); //set colour
        pieDataSet.setValueTextSize(8f);
        pieDataSet.setValueTextColor(Color.WHITE);
        //pieDataSet.setValueTypeface(mTf); //set word type
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); //
        PieData pieData = new PieData(xValues, pieDataSet);
        return pieData;
    }

    private void showPieChart(PieChart pieChart, PieData pieData) {
        pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(40f); //radius
        pieChart.setTransparentCircleRadius(50f); //Translucent ring

        pieChart.setDescription("");

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); //initial angle
        pieChart.setRotationEnabled(true); //Rotation Enabled
        pieChart.setUsePercentValues(true); //show percentage

        pieChart.setDrawCenterText(true); //pie chart add text
        pieChart.setCenterText("Total" + inorout + ":" + Total);

        pieChart.setCenterTextColor(Color.GRAY);
        //pieChart.setCenterTextTypeface(mTf);

        pieChart.setData(pieData);

        Legend mLegend = pieChart.getLegend();
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        mLegend.setXEntrySpace(10f);
        mLegend.setYEntrySpace(5f);
        //mLegend.setTypeface(mTf);
        mLegend.setTextColor(Color.GRAY);

        pieChart.animateXY(1000, 1000);
    }

    private void pieChartOnclick() {
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                //Display the specific value of the selected type
                int p = entry.getXIndex();
                int k = getValue[p];
                tv_type.setText(myType[k] + "(" + entry.getVal() + "%" + ")");
                String valStr = Double.toString(myValue[k]);
                if (inorout.equals("Spending")) {
                    tv_transaction.setText("-" + valStr.substring(0, valStr.indexOf(".") + 2));
                } else {
                    tv_transaction.setText("+" + valStr.substring(0, valStr.indexOf(".") + 2));
                }
                //refresh ListView
                list_item.clear();
                adapter_money_type.notifyDataSetChanged();
                for (Tb_transaction tb_money : list_inorout) {
                    if (tb_money.getType().equals(myType[k])) {
                        list_item.add(tb_money);
                    }
                }
                adapter_money_type.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected() {
            }
        });
    }

    private void sendResult() {
        Intent intent = new Intent();
        intent.putExtra("isType", "1");
        setResult(Activity.RESULT_OK, intent);
    }

    private void backOnclick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Return parameters, tell the previous selection details
                sendResult();
                //close current Activity
                finish();
            }
        });
    }
}
