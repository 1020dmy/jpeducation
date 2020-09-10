package com.jianpei.wps;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;

public class WpsReadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wps_read);
        initWps();
    }


    protected void initWps(){
        // 在调用TBS初始化、创建WebView之前进行如下配置
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);
    }


    protected void openFIle(){
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("style", "1");
//        params.put("local", "true");
//        params.put("memuData", jsondata);
//        QbSdk.openFileReader(ctx,”/sdcard/xxx.doc”, params,callback);
    }



}
