package com.jianpei.jpeducation.activitys.login;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.MainActivity;
import com.jianpei.jpeducation.activitys.web.GuiZeActivity;
import com.jianpei.jpeducation.base.BaseModelActivity;
import com.jianpei.jpeducation.base.MyApplication;
import com.jianpei.jpeducation.fragment.login.CodeLoginFragment;
import com.jianpei.jpeducation.fragment.login.PassLoginFragment;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.viewmodel.WxLoginModel;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseModelActivity<WxLoginModel, String> {


    @BindView(R.id.tv_registered)
    TextView tvRegistered;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.tv_codelogin)
    TextView tvCodeLogin;
    @BindView(R.id.tv_pwdLogin)
    TextView tvPwdLogin;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_bottom_xieyi)
    TextView tvBottomXieyi;
//    @BindView(R.id.tv_accessToken)
//    TextView textView;


    private PassLoginFragment passLoginFragment;
    private CodeLoginFragment codeLoginFragment;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        setTitleViewPadding(tvStatus);
        passLoginFragment = new PassLoginFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, passLoginFragment).show(passLoginFragment).commit();
        initViewModel();//初始化
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void onError(String message) {
        shortToast(message);
    }

    @Override
    protected void onSuccess(String data) {
        initPush();
        if (TextUtils.isEmpty(data)) {
            startActivity(new Intent(LoginActivity.this, BindPhoneActivity.class));
        } else {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

        }
        finish();
    }

    protected void initPush(){
        //获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(MyApplication.getInstance());
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                L.i("LoginActivity","注册成功：deviceToken：-------->  " + deviceToken);
//                bindAlias(mPushAgent);
                SpUtils.putString(SpUtils.push_token,deviceToken);

            }

            @Override
            public void onFailure(String s, String s1) {
                L.e("LoginActivity","注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });
    }


    @OnClick({R.id.tv_registered, R.id.tv_pwdLogin, R.id.tv_codelogin, R.id.iv_back, R.id.tv_wxlogin, R.id.tv_bottom_xieyi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_wxlogin:
                aaa();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_registered:
                startActivity(new Intent(this, RegisteredActivity.class));
                break;
            case R.id.tv_pwdLogin:
                switchFragment(0);
                break;
            case R.id.tv_codelogin:
                switchFragment(1);
                break;
            case R.id.tv_bottom_xieyi:
                startActivity(new Intent(this, GuiZeActivity.class).putExtra("title",R.string.xieyi_name).putExtra("webUrl", SpUtils.getValue(SpUtils.UserProtocol)));
                break;
        }
    }


    /**
     * 切换fragmeng
     *
     * @param index
     */
    private void switchFragment(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏上个Fragment
        if (index == 0) {
            tvPwdLogin.setVisibility(View.GONE);
            tvCodeLogin.setVisibility(View.VISIBLE);
            if (passLoginFragment == null) {
                passLoginFragment = new PassLoginFragment();
                transaction.add(R.id.frameLayout, passLoginFragment);
            }
            if (codeLoginFragment != null) {
                transaction.hide(codeLoginFragment);
            }
            transaction.show(passLoginFragment).commitAllowingStateLoss();
        } else {
            tvPwdLogin.setVisibility(View.VISIBLE);
            tvCodeLogin.setVisibility(View.GONE);
            if (codeLoginFragment == null) {
                codeLoginFragment = new CodeLoginFragment();
                transaction.add(R.id.frameLayout, codeLoginFragment);
            }
            if (passLoginFragment != null) {
                transaction.hide(passLoginFragment);
            }
            transaction.show(codeLoginFragment).commitAllowingStateLoss();
        }
    }


    public void aaa() {
        UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, umAuthListener);
    }

    UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            if (map != null) {
                showLoading("");
//                Set<String> keys = map.keySet();
//                for (String key : keys) {
//                    System.out.println("=====key===" +key+",value:"+map.get(key));
//                }

//                String access_token = map.get("access_token");
//                System.out.println("=====access_token===" + access_token);
                mViewModel.wxLogin(
                        map.get("refreshToken"),
                        map.get("expiration"),
                        map.get("screen_name"),
                        map.get("access_token"),
                        map.get("city"),
                        map.get("gender"),
                        map.get("openid"),
                        map.get("province"),
                        map.get("iconurl"));
            }

        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            shortToast("授权失败");

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            shortToast("取消授权");

        }
    };


}
