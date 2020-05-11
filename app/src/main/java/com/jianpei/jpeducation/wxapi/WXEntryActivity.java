package com.jianpei.jpeducation.wxapi;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.AccessTokenBean;
import com.jianpei.jpeducation.bean.WxUserInfoBean;
import com.jianpei.jpeducation.utils.L;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    IWXAPI iwxapi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //接收到分享以及登录的intent传递handleIntent方法，处理结果
        iwxapi = WXAPIFactory.createWXAPI(this, "wx45ccf8958a0a24c7", false);
        iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    /**
     * 结果回调
     *
     * @param baseResp
     */
    @Override
    public void onResp(BaseResp baseResp) {
//        L.e("======WXEntryActivity:"+baseResp.errStr);
        //登录回调
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                String code = ((SendAuth.Resp) baseResp).code;
                //获取用户信息
                getWXUserInfo(code);
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                finish();
                break;
            default:
                finish();
                break;
        }
    }

    public <T> ObservableTransformer<T, T> setThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    void getWXUserInfo(String code) {
        RetrofitFactory.getInstance().API().weixinAccessToken("", "", code).flatMap(new Function<AccessTokenBean, ObservableSource<WxUserInfoBean>>() {
            @Override
            public ObservableSource<WxUserInfoBean> apply(AccessTokenBean accessTokenBean) throws Throwable {

                return RetrofitFactory.getInstance().API().weixinUserInfo(accessTokenBean.getAccess_token(), accessTokenBean.getOpenid());
            }
        }).flatMap(new Function<WxUserInfoBean, ObservableSource<BaseEntity<String>>>() {
            @Override
            public ObservableSource<BaseEntity<String>> apply(WxUserInfoBean wxUserInfoBean) throws Throwable {
                return RetrofitFactory.getInstance().API().wxLogin();
            }
        }).subscribe(new Observer<BaseEntity<String>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull BaseEntity<String> userInfo) {
                //调用登陆接口进行登陆
                finish();

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

}