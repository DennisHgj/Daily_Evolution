package com.example.daliyevolution.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.example.daliyevolution.R;
import com.example.daliyevolution.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/***
 * Activity_Launch
 * build welcome page
 * @author Guanjie Huang
 * @ID u6532079
 */
@ContentView(R.layout.activity_launch)
public class Activity_Launch extends BaseActivity {
    @ViewInject(R.id.iv_icon)
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView.setImageResource(R.drawable.icon);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //run in main thread
                startMainActivity();
            }
        },2000);//delay for 2 seconds
    }

    public void startMainActivity(){
        Intent intent = new Intent(this, Activity_main.class);
        startActivity(intent);
        //close current page
        finish();
    }
}
