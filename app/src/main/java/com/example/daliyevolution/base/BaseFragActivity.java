package com.example.daliyevolution.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import org.xutils.x;

/***
 * BaseFragActivity
 * define the basic activity for Fragments
 * @author Guanjie Huang
 * @ID u6532079
 */

public class BaseFragActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }
}
