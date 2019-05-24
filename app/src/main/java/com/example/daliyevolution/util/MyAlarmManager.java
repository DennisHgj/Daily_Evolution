package com.example.daliyevolution.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.daliyevolution.ui.AlarmReceiver;

import java.util.Calendar;
/***
 * MyAlarmManager
 * use to create and cancel an alarm
 * @author Lingyu Xia
 *
 * @ID u6483756
 */
public class MyAlarmManager {

    private static MyAlarmManager mInstance = null;

    protected MyAlarmManager(){

    }

    public static MyAlarmManager getmInstance() {
        if (mInstance == null) {

            mInstance = new MyAlarmManager();
        }

        return mInstance;
    }

    public void setNextSyncTime(Context context, int id, Calendar calendar) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction("setalarm");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void cancel(Context context, int id) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction("setalarm");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);

        alarmManager.cancel(pendingIntent);
    }

}
