package com.example.daliyevolution.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/***
 * AlarmMessage
 * show the alert dialog when time matches the alarm
 * @author Lingyu Xia
 *
 * @ID u6483756
 */
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
                        Intent intent1 = new Intent(getApplicationContext(), Activity_main.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                    }
                }).show();

    }
}
