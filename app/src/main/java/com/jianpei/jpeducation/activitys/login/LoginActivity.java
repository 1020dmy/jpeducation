package com.jianpei.jpeducation.activitys.login;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.MainActivity;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.fragment.login.CodeLoginFragment;
import com.jianpei.jpeducation.fragment.login.PassLoginFragment;
import com.jianpei.jpeducation.viewmodel.WxLoginModel;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {


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
//    @BindView(R.id.tv_accessToken)
//    TextView textView;

    private WxLoginModel wxLoginModel;


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

    }

    @Override
    protected void initData() {

        wxLoginModel = new ViewModelProvider(this).get(WxLoginModel.class);

        wxLoginModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);

            }
        });

        wxLoginModel.getScuucessData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                if (TextUtils.isEmpty(s)) {
                    startActivity(new Intent(LoginActivity.this, BindPhoneActivity.class));
                } else {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                }
                finish();
            }
        });


    }


    @OnClick({R.id.tv_registered, R.id.tv_pwdLogin, R.id.tv_codelogin, R.id.iv_back, R.id.tv_wxlogin})
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
                wxLoginModel.wxLogin(
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
