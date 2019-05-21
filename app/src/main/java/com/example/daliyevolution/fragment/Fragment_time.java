package com.example.daliyevolution.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.daliyevolution.AlarmActivity;
import com.example.daliyevolution.AlarmReceiver;
import com.example.daliyevolution.R;
import com.example.daliyevolution.base.AlarmBaseAdapter;
import com.example.daliyevolution.base.BaseFragment;
import com.example.daliyevolution.util.Db_config;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/***
 * Fragment_time
 * show all the alarm that is stored in the database
 * access to add a new alarm
 * @author Lingyu Xia
 *
 * @ID u6483756
 */
public class Fragment_time extends BaseFragment {
    private Calendar calendar = Calendar.getInstance();
    private TextView tx;
    private DbManager.DaoConfig daoConfig = Db_config.getDaoConfig();
    private DbManager db = x.getDb(daoConfig);
    private ListView alarm_list;
    private ArrayList<Object[]> al = new ArrayList<>();// store the info of alarm from the database
    int hour_of_day;
    int minute;
    int id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        alarm_list = (ListView) view.findViewById(R.id.alarm_list);
        AlarmManager alarm = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
        try {
            String sql = "select * from Tb_alarm";
            Cursor cursor = db.execQuery(sql);
            // find and store all alarm from the database
            if(cursor.moveToFirst()){
                while(cursor.moveToNext()){
                    // use the cursor to get the alarm info
                    id = cursor.getInt(0);
                    hour_of_day = cursor.getInt(1);
                    minute = cursor.getInt(2);

                    // generate the alarm when get the data from database -> can be passed as a parameter
                    calendar.setTimeInMillis(System.currentTimeMillis()) ;
                    calendar.set(Calendar.HOUR_OF_DAY, hour_of_day) ;
                    calendar.set(Calendar.MINUTE, minute) ;
                    calendar.set(Calendar.SECOND, 0) ;
                    calendar.set(Calendar.MILLISECOND, 0) ;

                    Intent intent = new Intent(getContext(), AlarmReceiver.class);
                    intent.setAction("setalarm");// set the action for brosadcast
                    intent.setData(Uri.parse("content://calendar/calendar_alerts/1" + id));

                    PendingIntent sender = PendingIntent.getBroadcast(
                            getContext(), 0, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    // wake the alarm when the time matches

                    alarm.setRepeating(AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sender);//activate the alarm clock when time is up

                    //give the alarm info to adapter and implement function of cancel alarm
                    Object[] s = new Object[]{id, hour_of_day, minute, sender};
                    al.add(s);
                }
                cursor.close();
                AlarmBaseAdapter aba = new AlarmBaseAdapter(getContext(), al);
                alarm_list.setAdapter(aba);
            }

        } catch (DbException e) {
            e.printStackTrace();
        }

        tx = (TextView) view.findViewById(R.id.add_alarm);
        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //jump to the activity which will set up the alarm
                Intent intent1=new Intent(context, AlarmActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
        });

        return view;
    }
}
