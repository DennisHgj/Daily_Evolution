package com.example.daliyevolution.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.daliyevolution.AlarmActivity;
import com.example.daliyevolution.R;
import com.example.daliyevolution.base.AlarmBaseAdapter;
import com.example.daliyevolution.base.BaseFragment;
import com.example.daliyevolution.util.Db_config;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;


public class Fragment_time extends BaseFragment {
    private TextView tx;
    private DbManager.DaoConfig daoConfig = Db_config.getDaoConfig();
    private DbManager db = x.getDb(daoConfig);
    private ListView alarm_list;
    private ArrayList<int[]> al = new ArrayList<>();
    int hour_of_day;
    int minute;
    int id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        alarm_list = (ListView) view.findViewById(R.id.alarm_list);
        try {
            String sql = "select * from Tb_alarm";
            Cursor cursor = db.execQuery(sql);
            if(cursor.moveToFirst()){
                while(cursor.moveToNext()){
                    id=cursor.getInt(0);
                    hour_of_day = cursor.getInt(1);
                    minute = cursor.getInt(2);
                    int[] s = new int[]{id, hour_of_day, minute};
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
