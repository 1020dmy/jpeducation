package com.jianpei.jpeducation.base;

import android.app.Application;
import android.content.Context;

import com.jianpei.jpeducation.Constants;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.utils.L;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

public class MyApplication extends Application {

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new MaterialHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    private static MyApplication instance;
    //微信APPid

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        L.isDebug = true;//日志开关
        initUmeng();
        initWx();
    }

    public static MyApplication getInstance() {
        return instance;
    }


    protected void initUmeng() {
        UMConfigure.init(this, "59892f08310c9307b60023d0", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
                "669c30a9584623e70e8cd01b0381dcb4");
//        UMConfigure.init(this, "5eb4bf4d978eea078b7e9364"
//                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0

        PlatformConfig.setWeixin(Constants.WX_ID, "f48c09b4fd43c74e6df9c1e1aec76fc9");
        PlatformConfig.setQQZone("101875487", "79bbca3fb14f19baa9b6b8f182534870");
        PlatformConfig.setQQFileProvider("com.jianpei.jpeducation.fileprovider");

    }


    protected void initWx() {

        IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
// 将该app注册到微信
        msgApi.registerApp(Constants.WX_ID);
    }


}
