package com.example.daliyevolution.function;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.daliyevolution.R;
import com.example.daliyevolution.model.Tb_alarm;
import com.example.daliyevolution.ui.AlarmCancelMessage;
import com.example.daliyevolution.util.Db_config;
import com.example.daliyevolution.util.MyAlarmManager;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.Calendar;
import java.util.List;

/***
 * AlarmBaseAdapter
 * an adapter to show the alarm item
 * get data from the database
 * @author Lingyu Xia
 *
 * @ID u6483756
 */

public class AlarmBaseAdapter extends ArrayAdapter<Tb_alarm> {

    private int resourceId;
    private Context mContext;
    private MyAlarmManager myAlarmManager;
    private Calendar calendar = Calendar.getInstance();
    private DbManager.DaoConfig daoConfig = Db_config.getDaoConfig();
    private DbManager db = x.getDb(daoConfig);

    public AlarmBaseAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Tb_alarm> objects) {
        super(context, resource, objects);
        mContext = context;
        resourceId = resource;
    }


    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final Tb_alarm tb_alarm = getItem(position);
        View vi;
        if (view == null) {
            vi = LayoutInflater.from(getContext()).inflate(resourceId, null);
        } else {
            vi = view;
        }
        final TextView al_id = (TextView) vi.findViewById(R.id.alarm_id);
        TextView alarm_info = (TextView) vi.findViewById(R.id.alarm_info);
        al_id.setText(String.valueOf(tb_alarm.getId())); // the id of a specific alarm, the textview is hidden

        if (tb_alarm.getMinute() < 10){
            // make the alarm in right format if the minute is less than 10
            alarm_info.setText("    " + tb_alarm.getHour_of_day() + " : 0" + tb_alarm.getMinute());
        }else {
            alarm_info.setText("    " + tb_alarm.getHour_of_day() + " : " + tb_alarm.getMinute());
        }

        myAlarmManager = MyAlarmManager.getmInstance();
        calendar.setTimeInMillis(System.currentTimeMillis()) ;
        calendar.set(Calendar.HOUR_OF_DAY, tb_alarm.getHour_of_day()) ;
        calendar.set(Calendar.MINUTE, tb_alarm.getMinute()) ;
        calendar.set(Calendar.SECOND, 0) ;
        calendar.set(Calendar.MILLISECOND, 0) ;

        myAlarmManager.setNextSyncTime(mContext, tb_alarm.getId(), calendar);

        TextView delete = (TextView) vi.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {

                    try {
                        // delete the alarm info from the database
                        String sql = "delete from Tb_alarm where id = " + tb_alarm.getId();
                        db.executeUpdateDelete(sql);

                        myAlarmManager.cancel(mContext, tb_alarm.getId());

                        Intent intent = new Intent(mContext, AlarmCancelMessage.class);
                        mContext.startActivity(intent);

                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                }

        });

        return vi;
    }

}
