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

                // time matches the latest alarm and jump to the AlarmMessage class to give a dialog
                Intent intent1 = new Intent(context, AlarmMessage.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);

        }

}
