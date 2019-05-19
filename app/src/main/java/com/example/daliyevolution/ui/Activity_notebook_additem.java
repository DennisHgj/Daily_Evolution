package com.example.daliyevolution.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daliyevolution.R;
import com.example.daliyevolution.base.BaseActivity;
import com.example.daliyevolution.model.Tb_notebook;
import com.example.daliyevolution.util.Db_config;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * Activity_notebook_additem
 * add notebook activity
 * @author Guanjie Huang
 * @ID u6532079
 */
@ContentView(R.layout.fragment_notebook_add)
public class Activity_notebook_additem extends BaseActivity {
    private ImageView back;
    private TextView save;
    private EditText label_editText;
    private EditText content_editText;

    private String label;
    private String date;
    private String content;
    private Tb_notebook tb_diary;

    private DbManager.DaoConfig daoConfig = Db_config.getDaoConfig();
    private DbManager db = x.getDb(daoConfig);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        receiveData();
        saveOnclick();
        backOnclick();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.diary_add_back);
        save = (TextView) findViewById(R.id.diary_save);
        label_editText = (EditText) findViewById(R.id.diary_label_edit);
        content_editText = (EditText) findViewById(R.id.diary_content_edit);
    }

    private void receiveData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            Log.d("test", "...............receive failed");
        } else {
            //show data
            tb_diary = (Tb_notebook) bundle.getSerializable("tb_diary");
            label_editText.setText(tb_diary.getLabel());
            content_editText.setText(tb_diary.getContent());
        }
    }

    private void saveOnclick() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get label and content
                label = label_editText.getText().toString().trim();
                content = content_editText.getText().toString().trim();
                //new notebook
                if (tb_diary == null) {
                    if (label.equals("")) {
                        Toast.makeText(Activity_notebook_additem.this, "Please add a label", Toast.LENGTH_SHORT).show();
                    } else if (content.equals("")) {
                        Toast.makeText(Activity_notebook_additem.this, "Please add content", Toast.LENGTH_SHORT).show();
                    } else {
                        //get current date
                        Date nowDate = new Date();
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        date = format.format(nowDate);
                        //add to database
                        Tb_notebook my_tb_diary = new Tb_notebook(label, date, content);
                        try {
                            db.save(my_tb_diary);
                            Toast.makeText(Activity_notebook_additem.this, "save success", Toast.LENGTH_SHORT).show();
                            //back data, ask refresh Listview
                            sendResult();
                            finish();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                }//edit a not
                else {
                    //if label and content all null, delete the note
                    if (label.equals("") && content.equals("")) {
                        try {
                            db.delete(tb_diary);
                            Toast.makeText(Activity_notebook_additem.this, "delete success", Toast.LENGTH_SHORT).show();
                            sendResult();
                            finish();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            tb_diary.setLabel(label);
                            tb_diary.setContent(content);
                            db.update(tb_diary);
                            Toast.makeText(Activity_notebook_additem.this, "save success", Toast.LENGTH_SHORT).show();
                            sendResult();
                            finish();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void sendResult() {
        Intent intent = new Intent();
        intent.putExtra("isClear", "1");
        setResult(Activity.RESULT_OK, intent);
    }

    private void backOnclick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
