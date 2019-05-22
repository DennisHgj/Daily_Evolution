package com.example.daliyevolution;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class noteactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noteactivity);
    }
    // cancel button
    public void onPress(View v){
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    // clear button
    public void onClick(View v){
        EditText txt= findViewById(R.id.editText);
        txt.setText("");
    }
    //add button
    public void add(View v){
        EditText txt= findViewById(R.id.editText);
        Intent intent= new Intent(this,MainActivity.class);
        intent.putExtra("String",txt.getText().toString());
        startActivity(intent);
    }
}
