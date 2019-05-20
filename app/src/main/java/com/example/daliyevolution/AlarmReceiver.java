package com.example.daliyevolution;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.daliyevolution.ui.Activity_main;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
            // jump to the activity which shows alert message when receiving the broadcast

        if (intent.getAction().equals("deleteAlarm")) {
            Intent intent1 = new Intent(context, Activity_main.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.putExtra("id", 1);
            context.startActivity(intent1);
        } else {
                Intent intent1 = new Intent(context, AlarmMessage.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
        }

}
