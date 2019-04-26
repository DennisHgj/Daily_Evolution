package com.example.daliyevolution.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import org.xutils.x;



public class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        x.view().inject(this);

    }
}
