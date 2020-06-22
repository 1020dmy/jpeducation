package com.jianpei.jpeducation.base;

import android.app.Application;

import com.jianpei.jpeducation.utils.L;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

public class MyApplication extends Application {

    private static MyApplication instance;
    //微信APPid

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        L.isDebug = true;//日志开关
        initUmeng();
    }

    public static MyApplication getInstance() {
        return instance;
    }


    protected void initUmeng() {

        UMConfigure.init(this, "59892f08310c9307b60023d0", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
                "669c30a9584623e70e8cd01b0381dcb4");
//        UMConfigure.init(this, "5eb4bf4d978eea078b7e9364"
//                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0

        PlatformConfig.setWeixin("wx760dbdd41ef089a5", "f48c09b4fd43c74e6df9c1e1aec76fc9");
        PlatformConfig.setQQZone("101875487", "79bbca3fb14f19baa9b6b8f182534870");
        PlatformConfig.setQQFileProvider("com.jianpei.jpeducation.fileprovider");

    }


}
