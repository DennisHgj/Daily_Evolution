package com.example.daliyevolution.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;

import com.example.daliyevolution.R;
import com.example.daliyevolution.base.BaseFragActivity;
import com.example.daliyevolution.base.BaseFragment;
import com.example.daliyevolution.fragment.Fragment_home;
import com.example.daliyevolution.fragment.Fragment_notebook;
import com.example.daliyevolution.fragment.Fragment_time;
import com.example.daliyevolution.fragment.Fragment_transaction;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/***
 * Activity_main
 * main activity, used to implement fragment transfer
 * @author Guanjie Huang
 * @ID u6532079
 */
@ContentView(R.layout.activity_main)
public class Activity_main extends BaseFragActivity {
    @ViewInject(R.id.rg_main)
    RadioGroup myradiogroup;

    private int position;
    private List<BaseFragment> fragment_list;
    private Fragment mContent;//record current Fragment
    //private List<BaseFragment> rgList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
        setListener();

    }


    private void initFragment() {
        fragment_list = new ArrayList<>();
        fragment_list.add(new Fragment_home());
        fragment_list.add(new Fragment_time());
        fragment_list.add(new Fragment_notebook());
        fragment_list.add(new Fragment_transaction());
    }

    private void setListener() {
        myradiogroup.setOnCheckedChangeListener(new myOnCheckedChangeListener());
        myradiogroup.check(R.id.bt_home);
    }

    public void onAppinfoClick(View view) {
        new AlertDialog.Builder(this)//show the dialog
                .setTitle("AppInfo")
                .setMessage(" Home Page :\n" +
                        "\n" +
                        "* Display on-time climate information\n" +
                        "* Refresh automatically when starting the application\n" +
                        "* Get real data from internet\n" +
                        "* Could change the city and refresh\n" +
                        "\n" +
                        "Alarm Page :\n" +
                        "* Display added alarms list\n" +
                        "* Edit an alarm\n" +
                        "* Add an alarm\n" +
                        "* Delete an alarm\n" +
                        "* Throw out alert information when time is up\n" +
                        "\n" +
                        "Notebook Page :\n" +
                        "* Display added notes list\n" +
                        "* Edit a note, when label and content are null, delete it\n" +
                        "* Clear all notes\n" +
                        "* Add a note \n" +
                        "\n" +
                        "Transaction Page :\n" +
                        "* Transactions are divided into two part: Income and Spending. Every part has many types of transaction.\n" +
                        "* Display transactions in different months\n" +
                        "* Use Pie chart to display transactions category analysis in one month\n" +
                        "* Edit a transaction\n" +
                        "* Add a transaction\n" +
                        "* Delete a transaction")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initFragment();
                        setListener();
                    }
                }).show();
    }

    class myOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId) {
                case R.id.bt_home:
                    position = 0;
                    break;
                case R.id.bt_time:
                    position = 1;
                    break;
                case R.id.bt_read:
                    position = 2;
                    break;
                case R.id.bt_money:
                    position = 3;
                    break;
                default:
                    break;
            }
            BaseFragment to = getFragment();

            switchFragment(mContent, to);
        }
    }

    private BaseFragment getFragment() {
        BaseFragment fragment = fragment_list.get(position);
        return fragment;
    }

    private void switchFragment(Fragment from, Fragment to) {
        if (from != to) {
            mContent = to;

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //Determine if it has been added
            if (!mContent.isAdded()) {
                //To is not added: hide from, add to
                if (from != null) {
                    transaction.hide(from);
                }
                if (to != null) {
                    transaction.add(R.id.fl_content, to).commit();
                }
            } else {
                //To has been added: hide from, display to
                if (from != null) {
                    transaction.hide(from);
                }
                if (to != null) {
                    transaction.show(to).commit();
                }
            }
        }
    }

}


