package com.example.daliyevolution.function;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.daliyevolution.R;
import com.example.daliyevolution.util.Db_config;
import com.example.daliyevolution.util.MyAlarmManager;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;

/***
 * AlarmBaseAdapter
 * an adapter to show the alarm item
 * get data from the database
 * @author Lingyu Xia
 *
 * @ID u6483756
 */

public class AlarmBaseAdapter extends BaseAdapter {

    private ArrayList<Object[]> data; // store the info of alarm from the database
    private Context mContext;
    private MyAlarmManager myAlarmManager;
    private Calendar calendar = Calendar.getInstance();
    private DbManager.DaoConfig daoConfig = Db_config.getDaoConfig();
    private DbManager db = x.getDb(daoConfig);

    public AlarmBaseAdapter(Context mContext, ArrayList<Object[]> data) {
        super();
        this.mContext = mContext;
        this.data = data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View vi = inflater.inflate(R.layout.fragment_alarm_item,null);
        final TextView al_id = (TextView) vi.findViewById(R.id.alarm_id);
        TextView alarm_info = (TextView) vi.findViewById(R.id.alarm_info);
        al_id.setText(String.valueOf(data.get(position)[0])); // the id of a specific alarm, the textview is hidden

        if ((int)data.get(position)[2] < 10){
            // make the alarm in right format if the minute is less than 10
            alarm_info.setText("    " + data.get(position)[1] + " : 0" + data.get(position)[2]);
        }else {
            alarm_info.setText("    " + data.get(position)[1] + " : " + data.get(position)[2]);
        }

        myAlarmManager = MyAlarmManager.getmInstance();
        calendar.setTimeInMillis(System.currentTimeMillis()) ;
        calendar.set(Calendar.HOUR_OF_DAY, (int)data.get(position)[1]) ;
        calendar.set(Calendar.MINUTE, (int)data.get(position)[1]) ;
        calendar.set(Calendar.SECOND, 0) ;
        calendar.set(Calendar.MILLISECOND, 0) ;

        myAlarmManager.setNextSyncTime(mContext, (int) data.get(position)[0], calendar);

        TextView delete = (TextView) vi.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {

                    try {
                        // delete the alarm info from the database
                        String sql = "delete from Tb_alarm where id = " + data.get(position)[0];
                        db.executeUpdateDelete(sql);
                        System.out.println(data.get(position)[2]);

                        myAlarmManager.cancel(mContext, (int)data.get(position)[0]);

                        Intent intent = new Intent();
                        intent.setAction("deleteAlarm");
                        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                        mContext.sendBroadcast(intent);

                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                }

        });

        return vi;
    }
}
