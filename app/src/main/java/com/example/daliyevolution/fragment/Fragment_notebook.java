package com.example.daliyevolution.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daliyevolution.R;
import com.example.daliyevolution.base.BaseFragment;
import com.example.daliyevolution.function.Adapter_notebook;
import com.example.daliyevolution.model.Tb_notebook;
import com.example.daliyevolution.ui.Activity_notebook_additem;
import com.example.daliyevolution.util.Db_config;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/***
 * fragment_notebook
 * notebook fragment
 * @author Guanjie Huang
 * @ID u6532079
 * @Check&Modify Chao Zhang
 * @ID u6545192
 *
 */
public class Fragment_notebook extends BaseFragment {
    public TextView tv_add;
    public TextView tv_clear;
    public ListView notebook_listview;

    public List<Tb_notebook> list_notebook;
    public Adapter_notebook adapter_notebook;

    public DbManager.DaoConfig daoConfig = Db_config.getDaoConfig();
    public DbManager db = x.getDb(daoConfig);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notebook, container, false);
        initView(view);
        initAdapter();
        itemSetListener();
        addOnclick();
        allClear();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        } else {
            if (data.getStringExtra("isClear") != null) {
                System.out.println("extra string got");
                refreshListView();
            }
        }
    }

    private void initView(View view) {
        tv_add = (TextView) view.findViewById(R.id.diary_add);
        tv_clear = (TextView) view.findViewById(R.id.diary_delete);
        notebook_listview = (ListView) view.findViewById(R.id.diary_lv);
    }

    private void initAdapter() {
        try {
            list_notebook = db.findAll(Tb_notebook.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (list_notebook == null) {
            list_notebook = new ArrayList<>();
        }
        adapter_notebook = new Adapter_notebook(context, R.layout.fragment_notebook_listview, list_notebook);
        notebook_listview.setAdapter(adapter_notebook);
        if (list_notebook.isEmpty()) {
            Toast.makeText(context, "no data", Toast.LENGTH_SHORT).show();
        }
    }

    private void itemSetListener() {
        notebook_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Tb_notebook tb_diary = adapter_notebook.getItem(position);
                bundle.putSerializable("tb_diary", tb_diary);
                Intent intent = new Intent(context, Activity_notebook_additem.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void addOnclick() {
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize add page Activity
                Intent intent = new Intent(context, Activity_notebook_additem.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    //refresh ListView
    private void refreshListView() {
        list_notebook.clear();
        adapter_notebook.notifyDataSetChanged();
        List<Tb_notebook> myList;
        try {
            myList = db.findAll(Tb_notebook.class);
            if (myList == null) {
                myList = new ArrayList<>();
            }
            for (Tb_notebook tb_diary : myList) {
                list_notebook.add(tb_diary);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        adapter_notebook.notifyDataSetChanged();
    }

    private void allClear() {
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.delete(Tb_notebook.class);
                    Toast.makeText(context, "cleared ", Toast.LENGTH_SHORT).show();
                    list_notebook.clear();
                    adapter_notebook.notifyDataSetChanged();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
