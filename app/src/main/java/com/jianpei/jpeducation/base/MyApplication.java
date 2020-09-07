package com.jianpei.jpeducation.base;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.jianpei.jpeducation.Constants;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.utils.DisplayUtil;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.utils.TestImageLoader;
import com.previewlibrary.ZoomMediaLoader;
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
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;

public class MyApplication extends Application {

    private final String TAG="MyApplication";

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

        DisplayUtil.getWindowSize();
        //螳螂
//        MIMManager.getInstance().init(getApplicationContext(),7011 , "5ea91b77dc4cec0b99d0b7da","");

        //查看大图
        ZoomMediaLoader.getInstance().init(new TestImageLoader());

    }

    public static MyApplication getInstance() {
        return instance;
    }


    protected void initUmeng() {
//        UMConfigure.init(this, "59892f08310c9307b60023d0", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
//                "669c30a9584623e70e8cd01b0381dcb4");
        UMConfigure.init(this, "5eb4bf4d978eea078b7e9364", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
                "456cbe6da94f6b9c6be2c6bb5d517b3b");

        PlatformConfig.setWeixin(Constants.WX_ID, "f48c09b4fd43c74e6df9c1e1aec76fc9");
        PlatformConfig.setQQZone("101875487", "79bbca3fb14f19baa9b6b8f182534870");
        PlatformConfig.setQQFileProvider("com.jianpei.jpeducation.fileprovider");
        //
        if (TextUtils.isEmpty(SpUtils.ID))
            return;
            //获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(this);
            //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                L.i(TAG,"注册成功：deviceToken：-------->  " + deviceToken);
//                bindAlias(mPushAgent);
                SpUtils.putString(SpUtils.push_token,deviceToken);

            }

            @Override
            public void onFailure(String s, String s1) {
                L.e(TAG,"注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });


    }


//    protected void bindAlias(PushAgent mPushAgent){
//        String userId= SpUtils.getValue(SpUtils.ID);
//        if (!TextUtils.isEmpty(userId)){
//            L.e("userId:"+userId);
//            //别名绑定，将某一类型的别名ID绑定至某设备，老的绑定设备信息被覆盖，别名ID和deviceToken是一对一的映射关系
//            mPushAgent.setAlias(userId,"userId" , new UTrack.ICallBack() {
//                @Override
//                public void onMessage(boolean isSuccess, String message) {
//                    L.e("别名绑定:"+isSuccess+","+message);
//
//                }
//            });
//        }
//    }



    protected void initWx() {

        IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
// 将该app注册到微信
        msgApi.registerApp(Constants.WX_ID);
    }


}
