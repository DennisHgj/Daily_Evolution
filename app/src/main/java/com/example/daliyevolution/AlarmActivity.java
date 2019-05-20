package com.example.daliyevolution;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.daliyevolution.model.Tb_alarm;
import com.example.daliyevolution.ui.Activity_main;
import com.example.daliyevolution.util.Db_config;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.IOException;
import java.util.Calendar;


public class AlarmActivity extends AppCompatActivity {

    private AlarmManager alarm = null;
    private Button set = null;
    private TimePicker time = null;
    private int hourOfDay = 0 ;
    private int minute = 0;
    private Calendar calendar = Calendar.getInstance();
    private DbManager.DaoConfig daoConfig = Db_config.getDaoConfig();
    private DbManager db = x.getDb(daoConfig);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_layout);

        this.set = (Button) super.findViewById(R.id.set);
        this.time = (TimePicker) super.findViewById(R.id.time);

        this.alarm = (AlarmManager) super.getSystemService(Context.ALARM_SERVICE) ;
        this.set.setOnClickListener(new SetOnClickListener()) ;
        this.time.setIs24HourView(true) ;
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

            try {
                //save the alarm in the database
                Tb_alarm ta = new Tb_alarm(hourOfDay, minute);
                db.save(ta);
                Intent intent = new Intent(AlarmActivity.this, AlarmReceiver.class);//jump to another activity
                intent.setAction("org.alarm.action.setalarm");
                PendingIntent sender = PendingIntent.getBroadcast(
                        AlarmActivity.this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmActivity.this.alarm.setRepeating(AlarmManager.RTC_WAKEUP,
                        AlarmActivity.this.calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sender);//set the alarm clock
                //jump to the fragment_time after adding a new alarm successfully
                Intent intent1 = new Intent(AlarmActivity.this, Activity_main.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);

            } catch (DbException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Toast.makeText(AlarmActivity.this, "new alarm added！",
                    Toast.LENGTH_LONG).show();
        }

    }

}
