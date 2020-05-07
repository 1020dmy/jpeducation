package com.jianpei.jpeducation.base;

import android.app.Application;

import com.jianpei.jpeducation.utils.L;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        L.isDebug = true;//日志开关
    }

}
