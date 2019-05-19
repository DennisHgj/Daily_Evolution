package com.example.daliyevolution.function;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.daliyevolution.R;
import com.example.daliyevolution.fragment.Fragment_transaction;
import com.example.daliyevolution.model.Tb_transaction;
import com.example.daliyevolution.ui.Activity_transaction_edititem;

import java.util.List;

/***
 * Adapter_transaction
 * use to set adapter for ListView
 * @author Guanjie Huang
 * @ID u6532079
 */

public class Adapter_transaction extends ArrayAdapter<List<Tb_transaction>> {
    private int resourceId;
    private TextView tv_time;
    private TextView tv_in;
    private TextView tv_out;
    private ListView listView_transaction;

    private Fragment_transaction my_fragment_transaction;
    private Context myContext;
    private Adapter_transaction_item adapter_transaction_item;


    public Adapter_transaction(@NonNull Context context, @LayoutRes int resource, @NonNull List<List<Tb_transaction>> objects,
                         Fragment_transaction fragment_transaction) {
        super(context, resource, objects);
        myContext = context;
        my_fragment_transaction = fragment_transaction;
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        List<Tb_transaction> list_transaction_item = getItem(position);
        View view;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        }else {
            view = convertView;
        }
        //initialize parts
        tv_time = (TextView)view.findViewById(R.id.money_item_time);
        tv_in = (TextView)view.findViewById(R.id.money_item_in);
        tv_out = (TextView)view.findViewById(R.id.money_item_out);
        listView_transaction = (ListView)view.findViewById(R.id.lv_money_itemlv);
        //Pending information
        String time = list_transaction_item.get(0).getTime();
        double in_total = 0;
        double out_total = 0;
        //Get the total information of item
        if(!list_transaction_item.isEmpty()){
            for(Tb_transaction tb_transaction:list_transaction_item){
                if(tb_transaction.getInorout().equals("Income")){
                    in_total = in_total + tb_transaction.getAmount();
                }else {
                    out_total = out_total + tb_transaction.getAmount();
                }
            }
        }
        //Add information to a component
        tv_time.setText(time);
        String dayInTotal = Double.toString(in_total);
        String dayOutTotal = Double.toString(out_total);
        tv_out.setText("Spend:"+dayOutTotal.substring(0,dayOutTotal.indexOf(".")+2)+"  ");
        tv_in.setText("Income:"+dayInTotal.substring(0,dayInTotal.indexOf(".")+2));
        //Initialize the child ListView
        if(!list_transaction_item.isEmpty()){
            adapter_transaction_item = new Adapter_transaction_item(myContext,R.layout.fragment_transaction_item_listview,list_transaction_item);
            listView_transaction.setAdapter(adapter_transaction_item);
            setListViewHeightBasedOnChildren(listView_transaction);
        }
        //Add a click event to the child ListView
        itemSetListener();
        return view;
    }

    //Solve the sub-listView display is not complete
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void itemSetListener(){
        listView_transaction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tb_transaction tb_transaction = (Tb_transaction) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("tb_money",tb_transaction);
                Intent intent = new Intent(my_fragment_transaction.getContext(), Activity_transaction_edititem.class);
                intent.putExtras(bundle);
                my_fragment_transaction.startActivityForResult(intent,0);
            }
        });
    }

}
