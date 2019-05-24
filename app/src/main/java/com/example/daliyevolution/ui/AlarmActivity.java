package com.example.daliyevolution.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.daliyevolution.R;
import com.example.daliyevolution.base.BaseActivity;
import com.example.daliyevolution.model.Tb_alarm;
import com.example.daliyevolution.util.Db_config;

import org.xutils.DbManager;
import org.xutils.x;

import java.util.Calendar;

/***
 * AlarmActivity
 * alarm activity, used to add alarm to database
 * @author Lingyu Xia
 *
 * @ID u6483756
 */
public class AlarmActivity extends BaseActivity {
    public Button set = null;
    public TimePicker time = null;
    public int hourOfDay = 0; // store the hour of alarm we choose
    public int minute = 0; // store the minute of alarm we choose
    public Calendar calendar = Calendar.getInstance();
    public DbManager.DaoConfig daoConfig = Db_config.getDaoConfig();
    public DbManager db = x.getDb(daoConfig);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_layout);

        this.set = (Button) super.findViewById(R.id.set);
        this.time = (TimePicker) super.findViewById(R.id.time);

        this.set.setOnClickListener(new SetOnClickListener());
        this.time.setIs24HourView(true);
        this.time.setOnTimeChangedListener(new OnTimeChangedListenerImpl());

    }

    private class OnTimeChangedListenerImpl implements TimePicker.OnTimeChangedListener {

        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            AlarmActivity.this.calendar.setTimeInMillis(System.currentTimeMillis());//get current time
            AlarmActivity.this.hourOfDay = hourOfDay;
            AlarmActivity.this.minute = minute;
        }

    }

    private class SetOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            try {
                //save the alarm in the database
                Tb_alarm ta = new Tb_alarm(hourOfDay, minute);
                db.save(ta);

                //jump to the fragment_time after adding a new alarm, adding an id to specify the fragment


            } catch (Exception e) {
                e.printStackTrace();
            }

            Toast.makeText(AlarmActivity.this, "new alarm added！",
                    Toast.LENGTH_LONG).show();

            Intent intent = new Intent();
            intent.putExtra("isClear", "1");
            setResult(Activity.RESULT_OK, intent);
            finish();
        }

    }

}
