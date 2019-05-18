package com.example.daliyevolution;

import android.app.Application;
import android.content.Context;

import org.xutils.x;
/***
 * MyApp
 * define APP
 * @author Guanjie Huang
 * @ID u6532079
 */
public class MyApp extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        x.Ext.init(this);
        x.Ext.setDebug(false); //Output debug log, opening will affect performance
    }

    public static Context getGlobalContext(){
        return mContext;
    }
}

