package com.example.daliyevolution.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.example.daliyevolution.R;
import com.example.daliyevolution.base.BaseFragActivity;
import com.example.daliyevolution.base.BaseFragment;
import com.example.daliyevolution.fragment.Fragment_time;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

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

    private void initFragment(){
        fragment_list=new ArrayList<>();
        fragment_list.add(new Fragment_time());
    }

    private void setListener() {
        myradiogroup.setOnCheckedChangeListener(new myOnCheckedChangeListener());
        myradiogroup.check(R.id.navigation_home);
    }

    class myOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId) {
                case R.id.navigation_home:
                    position = 0;
                    break;
                case R.id.navigation_dashboard:
                    position=1;
                    break;
                case R.id.navigation_notifications:
                    position=2;
                    break;
            }
            BaseFragment to = getFragment();

            switchFragment(mContent,to);
        }
    }

    private BaseFragment getFragment(){
        BaseFragment fragment = fragment_list.get(position);
        return fragment;
    }

    private void switchFragment(Fragment from, Fragment to){
        if(from!=to){
            mContent = to;

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //Determine if it has been added
            if(!mContent.isAdded()){
                //To is not added: hide from, add to
                if(from!=null){
                    transaction.hide(from);
                }
                if(to!=null){
                    transaction.add(R.id.fl_content,to).commit();
                }
            }else{
                //To has been added: hide from, display to
                if(from!=null){
                    transaction.hide(from);
                }
                if(to!=null){
                    transaction.show(to).commit();
                }
            }
        }
    }

}
