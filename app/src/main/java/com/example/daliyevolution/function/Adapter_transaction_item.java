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
import com.example.daliyevolution.model.Tb_transaction;

import java.util.List;

/***
 * Adapter_transaction_item
 * set items adapter for ListView
 * @author Guanjie Huang
 * @ID u6532079
 */
public class Adapter_transaction_item extends ArrayAdapter<Tb_transaction> {
    private int resourceId;
    private TextView tv_transaction_label;
    private TextView tv_transaction_account;

    public Adapter_transaction_item(@NonNull Context context, @LayoutRes int resource, @NonNull List<Tb_transaction> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Tb_transaction tb_transaction = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        } else {
            view = convertView;
        }
        //Initialize elements
        tv_transaction_label = (TextView) view.findViewById(R.id.money_item_label);
        tv_transaction_account = (TextView) view.findViewById(R.id.money_item_account);
        //add information
        tv_transaction_label.setText(tb_transaction.getLabel());
        if (tb_transaction.getInorout().equals("Income")) {
            tv_transaction_account.setText("+" + Double.toString(tb_transaction.getAmount()));
        } else {
            tv_transaction_account.setText("-" + Double.toString(tb_transaction.getAmount()));
        }

        return view;
    }
}
