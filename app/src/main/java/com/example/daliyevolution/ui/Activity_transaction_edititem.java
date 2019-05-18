package com.example.daliyevolution.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.util.Log;
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
import android.widget.SpinnerAdapter;
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
 * Activity_transaction_edititem
 * activity for edit existing transactions
 * @author Guanjie Huang
 * @ID u6532079
 */
@ContentView(R.layout.fragment_transaction_edititem)
public class Activity_transaction_edititem extends BaseActivity {
    private ImageView back;
    private Spinner inorout_spinner;
    private EditText transaction_edittext;
    private Spinner type_spinner;
    private LinearLayout time_line;
    private TextView tv_time;
    private EditText label_edittext;
    private Button done;
    private ImageView money_delete;

    private Tb_transaction tb_transaction;
    private String inorout;
    private double money;
    private String type;
    private String time;
    private String label;
    private String[][] areaData = new String[][]{
            {"food", "shopping", "entertainment", "transport", "communication", "medical", "others"},
            {"salary", "part_time job", "interests", "gift", "others"}};
    private ArrayAdapter<CharSequence> adapterArea;

    private DbManager.DaoConfig daoConfig = Db_config.getDaoConfig();
    private DbManager db = x.getDb(daoConfig);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        receiveData();
        showData();
        inoroutOnclick();
        typeOnclick();
        timeOnclick();
        doneOnclick();
        backOnclick();
        deleteOnclick();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.account_back);
        inorout_spinner = (Spinner) findViewById(R.id.money_inorout_spinner);
        transaction_edittext = (EditText) findViewById(R.id.money_editText);
        type_spinner = (Spinner) findViewById(R.id.money_type_spinner);
        time_line = (LinearLayout) findViewById(R.id.money_line_time);
        tv_time = (TextView) findViewById(R.id.money_tv_time);
        label_edittext = (EditText) findViewById(R.id.money_label_edit);
        done = (Button) findViewById(R.id.money_add_save);
        money_delete = (ImageView) findViewById(R.id.money_delete);
        //limited cash input
        InputFilter[] is = {new CashierInputFilter()};
        transaction_edittext.setFilters(is);
    }

    private void receiveData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Log.d("test", "...............failed");
        } else {
            tb_transaction = (Tb_transaction) bundle.getSerializable("tb_money");
        }
    }

    private void showData() {
        //show spending or income
        int ifInorout = 0;
        SpinnerAdapter apsAdapter = inorout_spinner.getAdapter();
        int k = apsAdapter.getCount();
        for (int i = 0; i < k; i++) {
            if (tb_transaction.getInorout().equals(apsAdapter.getItem(i).toString())) {
                inorout_spinner.setSelection(i, true);
                inorout = tb_transaction.getInorout();
                ifInorout = i;
                break;
            }
        }
        //show money
        transaction_edittext.setText(Double.toString(tb_transaction.getAmount()));
        //show category
        adapterArea = new ArrayAdapter<CharSequence>(Activity_transaction_edititem.this, android.R.layout.simple_spinner_dropdown_item, areaData[ifInorout]);
        type_spinner.setAdapter(adapterArea);// add adapterArea to type_spinner
        int p = areaData[ifInorout].length;
        for (int i = 0; i < p; i++) {
            if (tb_transaction.getType().equals(areaData[ifInorout][i])) {
                type_spinner.setSelection(i, true);
                type = tb_transaction.getType();
                break;
            }
        }
        //show time
        time = tb_transaction.getTime();
        tv_time.setText(time);
        //show label;
        label_edittext.setText(tb_transaction.getLabel());
    }

    private void inoroutOnclick() {
        inorout_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inorout = (String) inorout_spinner.getSelectedItem();
                adapterArea = new ArrayAdapter<CharSequence>(Activity_transaction_edititem.this, android.R.layout.simple_spinner_dropdown_item, areaData[position]);
                type_spinner.setAdapter(adapterArea);// add adapterArea to type_spinner
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void typeOnclick() {
        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = (String) type_spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void timeOnclick() {
        time_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(Activity_transaction_edititem.this).inflate(R.layout.data_picker, null);
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
                AlertDialog.Builder alert = new AlertDialog.Builder(Activity_transaction_edititem.this);
                alert.setView(view);
                alert.setTitle("Please choose date");
                alert.setCancelable(false);
                alert.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth() + 1;
                        int day = datePicker.getDayOfMonth();
                        time = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
                        tv_time.setText(time);
                    }
                });
                alert.show();
            }
        });
    }

    private void doneOnclick() {
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_money = transaction_edittext.getText().toString().trim();
                label = label_edittext.getText().toString().trim();
                //determine if information is complete
                if (str_money.equals("")) {
                    Toast.makeText(Activity_transaction_edititem.this, "Please input money", Toast.LENGTH_SHORT).show();
                } else if (time == null) {
                    Toast.makeText(Activity_transaction_edititem.this, "Please input date", Toast.LENGTH_SHORT).show();
                } else {
                    money = Double.parseDouble(str_money);
                    tb_transaction.setInorout(inorout);
                    tb_transaction.setAmount(money);
                    tb_transaction.setType(type);
                    tb_transaction.setTime(time);
                    tb_transaction.setLabel(label);
                    try {
                        db.update(tb_transaction);
                        Toast.makeText(Activity_transaction_edititem.this, "Edit success", Toast.LENGTH_SHORT).show();
                        //return the result,ask to refresh ListView
                        sendResult();
                        //close current Fragment，back to main Fragment
                        finish();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void backOnclick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void deleteOnclick() {
        money_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int delId = tb_transaction.getId();
                try {
                    db.deleteById(Tb_transaction.class, delId);
                    Toast.makeText(Activity_transaction_edititem.this, "Delete success", Toast.LENGTH_SHORT).show();
                    //return the result,ask to refresh ListView
                    sendResult();
                    finish();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendResult() {
        Intent intent = new Intent();
        intent.putExtra("isClear", "1");
        setResult(Activity.RESULT_OK, intent);
    }

}
