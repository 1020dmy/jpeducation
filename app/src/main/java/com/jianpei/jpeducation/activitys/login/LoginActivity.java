package com.jianpei.jpeducation.activitys.login;


import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.fragment.login.CodeLoginFragment;
import com.jianpei.jpeducation.fragment.login.PassLoginFragment;
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


    private PassLoginFragment passLoginFragment;
    private CodeLoginFragment codeLoginFragment;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        passLoginFragment = new PassLoginFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, passLoginFragment).show(passLoginFragment).commit();

    }

    @Override
    protected void initData() {


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

//    @OnClick({R.id.btn_login})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.btn_login:
//                showLoading("登陆中...");
//                loginModel.login(etName.getText().toString(), etPwd.getText().toString());
//                break;
//        }
//    }

    public void aaa() {
        UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, umAuthListener);


    }

    UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            shortToast("成功了");
            startActivity(new Intent(LoginActivity.this,BindPhoneActivity.class));

        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            shortToast("失败了");
            startActivity(new Intent(LoginActivity.this,BindPhoneActivity.class));

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            shortToast("取消了");

        }
    };

}
