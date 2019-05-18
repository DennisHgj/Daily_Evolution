package com.example.daliyevolution.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daliyevolution.R;
import com.example.daliyevolution.base.BaseActivity;
import com.example.daliyevolution.model.Tb_transaction;
import com.example.daliyevolution.util.CashierInputFilter;
import com.example.daliyevolution.util.Db_config;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.x;



/***
 * Activity_transaction_add
 * add transaction activity
 * @author Guanjie Huang
 * @ID u6532079
 */
@ContentView(R.layout.fragment_transaction_add)
public class Activity_transaction_additem extends BaseActivity {
    private ImageView back;
    private Spinner inorout_spinner;
    private EditText money_edittext;
    private Spinner type_spinner;
    private LinearLayout time_line;
    private TextView tv_time;
    private EditText label_edittext;
    private Button done;

    private String inorout;
    private Double money;
    private String type;
    private String time;
    private String label;
    private String[][] areaData = new String[][] {
            {"food", "shopping", "entertainment", "transport", "communication", "medical", "others"},
            {"salary", "part_time job", "interests", "gift", "others"}};
    private ArrayAdapter<CharSequence> adapterArea;

    private DbManager.DaoConfig daoConfig = Db_config.getDaoConfig();
    private DbManager db = x.getDb(daoConfig);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        inoroutOnclick();
        typeOnclick();
        timeOnclick();
        doneOnclick();
        backOnclick();
    }
    private void initView() {
        back = (ImageView)findViewById(R.id.account_back);
        inorout_spinner = (Spinner)findViewById(R.id.money_inorout_spinner);
        money_edittext = (EditText)findViewById(R.id.money_editText);
        type_spinner = (Spinner)findViewById(R.id.money_type_spinner);
        time_line = (LinearLayout)findViewById(R.id.money_line_time);
        tv_time = (TextView)findViewById(R.id.money_tv_time);
        label_edittext = (EditText)findViewById(R.id.money_label_edit);
        done = (Button)findViewById(R.id.money_add_save);
        //Limited money input
        InputFilter[] is = {new CashierInputFilter()};
        money_edittext.setFilters(is);
    }

    private void inoroutOnclick(){
        inorout_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inorout = (String)inorout_spinner.getSelectedItem();
                adapterArea = new ArrayAdapter<CharSequence>(Activity_transaction_additem.this,android.R.layout.simple_spinner_dropdown_item,areaData[position]);
                type_spinner.setAdapter(adapterArea);// add adapterArea to type_spinner
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void typeOnclick(){
        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = (String)type_spinner.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void timeOnclick(){
        time_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(Activity_transaction_additem.this).inflate(R.layout.data_picker, null);
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
                AlertDialog.Builder alert = new AlertDialog.Builder(Activity_transaction_additem.this);
                alert.setView(view);
                alert.setTitle("Please set the date");
                alert.setCancelable(false);
                alert.setPositiveButton("confirm",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth()+1;
                        int day = datePicker.getDayOfMonth();
                        time = year+"-"+String.format("%02d",month)+"-"+String.format("%02d",day);
                        tv_time.setText(time);
                    }
                });
                alert.show();
            }
        });
    }

    private void doneOnclick(){
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_money = money_edittext.getText().toString().trim();
                label = label_edittext.getText().toString().trim();
                //check information complete
                if(str_money.equals("")){
                    Toast.makeText(Activity_transaction_additem.this,"please input the money",Toast.LENGTH_SHORT).show();
                }else if(time==null){
                    Toast.makeText(Activity_transaction_additem.this,"please set the date",Toast.LENGTH_SHORT).show();
                }else{
                    money = Double.parseDouble(str_money);
                    Tb_transaction tb_money = new Tb_transaction(inorout,money,type,time,label);
                    try {
                        db.save(tb_money);
                        Toast.makeText(Activity_transaction_additem.this,"save success",Toast.LENGTH_SHORT).show();
                        sendResult();
                        //close current Activity
                        finish();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void backOnclick(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close current Activity
                finish();
            }
        });
    }

    private void sendResult(){
        Intent intent = new Intent();
        intent.putExtra("isClear","1");
        setResult(Activity.RESULT_OK,intent);
    }
}
