package com.example.daliyevolution.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/***
 * AlarmReceiver
 * receive the broadcast and do different operations based on
 * different actions
 * @author Lingyu Xia
 *
 * @ID u6483756
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println(intent.getAction());
        if (intent.getAction().equals("deleteAlarm")) {
            // the action is to delete an alarm from database
            // go back to the fragment_alarm.xml after deleting
            Intent intent1 = new Intent(context, Activity_main.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.putExtra("id", 1); // pass an id to specify the fragment we are going to
            context.startActivity(intent1);
        } else {
                // time matches the latest alarm and jump to the AlarmMessage class to give a dialog
                Intent intent1 = new Intent(context, AlarmMessage.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
        }

}
