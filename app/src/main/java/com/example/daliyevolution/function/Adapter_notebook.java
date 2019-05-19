package com.example.daliyevolution.function;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.daliyevolution.R;
import com.example.daliyevolution.model.Tb_notebook;

import java.util.List;
/***
 * Adapter_notebook
 * use to set adapter for notebook ListView
 * @Author Guanjie Huang
 * @ID u6532079
 */

public class Adapter_notebook extends ArrayAdapter<Tb_notebook> {
    private int resourceId;
    private TextView tv_lable;
    private TextView tv_date;

    public Adapter_notebook(@NonNull Context context, @LayoutRes int resource, @NonNull List<Tb_notebook> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Tb_notebook tb_diary = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        } else {
            view = convertView;
        }
        //initialize elements
        tv_lable = (TextView) view.findViewById(R.id.diary_label);
        tv_date = (TextView) view.findViewById(R.id.diary_date);
        //add information to elements
        tv_lable.setText(tb_diary.getLabel());
        tv_date.setText(tb_diary.getDate());
        return view;

    }
}
