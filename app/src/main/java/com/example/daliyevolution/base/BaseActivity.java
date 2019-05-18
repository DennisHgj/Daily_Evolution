package com.example.daliyevolution.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.xutils.x;

/***
 * BaseActivity
 * use for design baseActivities in the app
 * @author Guanjie Huang
 * @ID u6532079
 */

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize activity to database
        x.view().inject(this);
    }
}
