package com.jianpei.jpeducation.base;

import android.app.Application;

import com.jianpei.jpeducation.Constants;
import com.jianpei.jpeducation.utils.L;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class MyApplication extends Application {

    private static MyApplication instance;
    //微信APPid

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        L.isDebug = true;//日志开关
//        regToWx();
    }

    public static MyApplication getInstance() {
        return instance;
    }




    /**
     * 注册应用到微信
     */
    protected void regToWx() {
        // IWXAPI 是第三方app和微信通信的openApi接口
        IWXAPI api;
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
        // 将应用的appId注册到微信
        api.registerApp(Constants.APP_ID);
        //建议动态监听微信启动广播进行注册到微信
//        registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                // 将该app注册到微信
//                api.registerApp(APP_ID);
//            }
//        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }


}
