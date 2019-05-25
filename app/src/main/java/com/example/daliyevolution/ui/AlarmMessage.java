package com.example.daliyevolution.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.daliyevolution.fragment.Fragment_home;
import com.example.daliyevolution.fragment.Fragment_time;

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
                .setMessage(" Do it now!")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(AlarmMessage.this, Fragment_home.class);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                }).show();

    }
}
