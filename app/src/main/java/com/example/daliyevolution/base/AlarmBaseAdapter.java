package com.example.daliyevolution.base;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.daliyevolution.R;
import com.example.daliyevolution.ui.Activity_main;
import com.example.daliyevolution.util.Db_config;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;
import java.util.ArrayList;

public class AlarmBaseAdapter extends BaseAdapter {

    private ArrayList<int[]> data; // store the info of alarm from the database
    private Context mContext;
    private DbManager.DaoConfig daoConfig = Db_config.getDaoConfig();
    private DbManager db = x.getDb(daoConfig);

    public AlarmBaseAdapter(Context mContext, ArrayList<int[]> data) {
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
        TextView al_id = (TextView) vi.findViewById(R.id.alarm_id);
        TextView alarm_info = (TextView) vi.findViewById(R.id.alarm_info);
        al_id.setText(String.valueOf(data.get(position)[0])); // the id of a specific alarm, the textview is hidden
        if (data.get(position)[2] < 10){
            // make the alarm in right format if the minute is less than 10
            alarm_info.setText("    " + data.get(position)[1] + " : 0" + data.get(position)[2]);
        }else {
            alarm_info.setText("    " + data.get(position)[1] + " : " + data.get(position)[2]);
        }
        TextView delete = (TextView) vi.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {

                    try {
                        String sql = "delete from Tb_alarm where id = " + data.get(position)[0];
                        db.executeUpdateDelete(sql);
                        Intent intent1=new Intent(mContext, Activity_main.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent1);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                }

        });

        return vi;
    }
}
