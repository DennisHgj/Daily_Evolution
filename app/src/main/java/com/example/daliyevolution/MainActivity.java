package com.example.daliyevolution;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

/***
 * MainActivity
 * main activity
 * @author Guanjie Huang, Chao Zhang
 * @ID u6532079
 * @ID u6545192
 */

public class MainActivity extends AppCompatActivity {
    ArrayList<String> entries=Datas.datas;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onPress(View v){
        EditText txt= findViewById(R.id.editText);
        txt.setText("");
    }
}
