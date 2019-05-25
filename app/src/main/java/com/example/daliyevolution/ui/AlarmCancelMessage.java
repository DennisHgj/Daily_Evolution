package com.example.daliyevolution.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.daliyevolution.base.BaseActivity;
import com.example.daliyevolution.fragment.Fragment_time;

public class AlarmCancelMessage extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AlertDialog.Builder(this)//show the dialog
                .setTitle("Cancel alarm！")
                .setMessage("Alarm canceled !!!")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(AlarmCancelMessage.this, Fragment_time.class);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                }).show();

    }
}
