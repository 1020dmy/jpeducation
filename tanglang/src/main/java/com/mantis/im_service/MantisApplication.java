package com.mantis.im_service;

import android.app.Application;

import com.example.mantis_im_sdk.MIMManager;

public class MantisApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MIMManager.getInstance().init(getApplicationContext(),7011 , "5ea91b77dc4cec0b99d0b7da","");
    }
}

