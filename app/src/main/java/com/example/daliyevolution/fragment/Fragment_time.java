package com.example.daliyevolution.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daliyevolution.AlarmActivity;
import com.example.daliyevolution.R;
import com.example.daliyevolution.base.BaseFragment;

public class Fragment_time extends BaseFragment {
    private TextView tx;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

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
