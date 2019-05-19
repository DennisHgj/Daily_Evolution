package com.example.daliyevolution;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class AlarmMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AlertDialog.Builder(this)//show the dialog
                .setTitle("Time is up！")
                .setMessage("Ring Ring Ring !!!：")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlarmMessage.this.finish();//kill the activity after a random click
                    }
                }).show();
    }
}
