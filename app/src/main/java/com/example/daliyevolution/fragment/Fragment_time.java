package com.example.daliyevolution.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daliyevolution.R;
import com.example.daliyevolution.base.BaseFragment;
import com.example.daliyevolution.function.AlarmBaseAdapter;
import com.example.daliyevolution.model.Tb_alarm;
import com.example.daliyevolution.ui.AlarmActivity;
import com.example.daliyevolution.util.Db_config;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/***
 * Fragment_time
 * show all the alarm that is stored in the database
 * access to add a new alarm
 * @author Lingyu Xia, Chao Zhang
 * @ID u6545192
 * @ID u6483756
 */
public class Fragment_time extends BaseFragment {
    private AlarmBaseAdapter aba;
    private TextView tx;
    private DbManager.DaoConfig daoConfig = Db_config.getDaoConfig();
    private DbManager db = x.getDb(daoConfig);
    private ListView alarm_list;
    public List<Tb_alarm> list_alarm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        alarm_list = (ListView) view.findViewById(R.id.alarm_list);

        initAdapter();

        tx = (TextView) view.findViewById(R.id.add_alarm);
        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //jump to the activity which will set up the alarm
                Intent intent1=new Intent(getContext(), AlarmActivity.class);
                startActivityForResult(intent1, 0);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        } else {
            if (data.getStringExtra("isClear") != null) {
                refreshListView();
            }
        }
    }

    private void initAdapter() {
        try {
            list_alarm = db.findAll(Tb_alarm.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (list_alarm == null) {
            list_alarm = new ArrayList<>();
        }
        aba = new AlarmBaseAdapter(getContext(), R.layout.fragment_alarm_item, list_alarm);
        alarm_list.setAdapter(aba);
        if (list_alarm.isEmpty()) {
            Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
        }
    }

    public void refreshListView(){
        list_alarm.clear();
        aba.notifyDataSetChanged();
        List<Tb_alarm> myList;
        try {
            myList = db.findAll(Tb_alarm.class);
            if (myList == null) {
                myList = new ArrayList<>();
            }
            for (Tb_alarm tb_diary : myList) {
                list_alarm.add(tb_diary);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        aba.notifyDataSetChanged();
    }
}
