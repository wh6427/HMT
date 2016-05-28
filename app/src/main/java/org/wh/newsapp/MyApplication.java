package org.wh.newsapp;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;


/**
 * Created by kkk on 2016/5/20.
 */
public class MyApplication extends Application {
    @Override public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }

}
