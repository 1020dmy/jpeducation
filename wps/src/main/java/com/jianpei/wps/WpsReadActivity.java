package com.jianpei.wps;


import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.ValueCallback;

import java.util.HashMap;

public class WpsReadActivity extends AppCompatActivity {

    private String filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wps_read);
        initWps();
        filePath=getIntent().getStringExtra("filePath");
        openFIle();
    }


    protected void initWps(){
        // 在调用TBS初始化、创建WebView之前进行如下配置
        HashMap map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);
    }


    protected void openFIle(){
        if (TextUtils.isEmpty(filePath))
            return;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("style", "1");
        params.put("local", "true");
        params.put("memuData", null);
        QbSdk.openFileReader(this, filePath, params, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {

            }
        });
    }



}
