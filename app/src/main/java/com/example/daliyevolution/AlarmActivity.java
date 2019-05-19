package com.example.daliyevolution;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class AlarmActivity extends AppCompatActivity {

    private AlarmManager alarm = null;
    private Button set = null;
    private Button delete = null;
    private TextView msg = null;
    private TimePicker time = null;
    private int hourOfDay = 0 ;
    private int minute = 0;
    private Calendar calendar = Calendar.getInstance() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_layout);

        this.set = (Button) super.findViewById(R.id.set);
        this.delete = (Button) super.findViewById(R.id.delete);
        this.msg = (TextView) super.findViewById(R.id.msg);
        this.time = (TimePicker) super.findViewById(R.id.time);

        this.alarm = (AlarmManager) super.getSystemService(Context.ALARM_SERVICE) ;
        this.set.setOnClickListener(new SetOnClickListener()) ;
        this.delete.setOnClickListener(new DeleteOnClickListener()) ;
        this.time.setIs24HourView(false) ;
        this.time.setOnTimeChangedListener(new OnTimeChangedListenerImpl()) ;

    }

    private class OnTimeChangedListenerImpl implements TimePicker.OnTimeChangedListener {

        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            AlarmActivity.this.calendar.setTimeInMillis(System.currentTimeMillis()) ;//get current time
            AlarmActivity.this.calendar.set(Calendar.HOUR_OF_DAY, hourOfDay) ;
            AlarmActivity.this.calendar.set(Calendar.MINUTE, minute) ;
            AlarmActivity.this.calendar.set(Calendar.SECOND, 0) ;
            AlarmActivity.this.calendar.set(Calendar.MILLISECOND, 0) ;
            AlarmActivity.this.hourOfDay = hourOfDay ;
            AlarmActivity.this.minute = minute ;
        }

    }

    private class SetOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AlarmActivity.this,
                    AlarmReceiver.class);//jump to another activity
            intent.setAction("org.alarm.action.setalarm") ;//define a broadcast
            PendingIntent sender = PendingIntent.getBroadcast(
                    AlarmActivity.this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmActivity.this.alarm.set(AlarmManager.RTC_WAKEUP,
                    AlarmActivity.this.calendar.getTimeInMillis(), sender);//set the alarm clock

            Toast.makeText(AlarmActivity.this, "new alarm added！",
                    Toast.LENGTH_LONG).show();
        }

    }
    private class DeleteOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (AlarmActivity.this.alarm != null) {
                Intent intent = new Intent(AlarmActivity.this,
                        AlarmReceiver.class);
                intent.setAction("org.alarm.action.setalarm") ;
                PendingIntent sender = PendingIntent.getBroadcast(
                        AlarmActivity.this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);//指定PendingIntent
                AlarmActivity.this.alarm.cancel(sender) ;	// 取消闹钟
                AlarmActivity.this.msg.setText("There is no alarm clock currently") ;
                Toast.makeText(AlarmActivity.this, "Delete successfully！",
                        Toast.LENGTH_LONG).show();
            }
        }

    }
}
