package com.jianpei.jpeducation.activitys.login;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.MainActivity;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.fragment.login.CodeLoginFragment;
import com.jianpei.jpeducation.fragment.login.PassLoginFragment;
import com.jianpei.jpeducation.viewmodel.LoginModel;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Collection;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {


    @BindView(R.id.btn_registered)
    Button btnRegistered;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.btn_pwdLogin)
    Button btnPwdLogin;
    @BindView(R.id.btn_codeLogin)
    Button btnCodeLogin;
    private LoginModel loginModel;
    @BindView(R.id.tv_values)
    TextView tvValues;


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
        btnPwdLogin.setVisibility(View.GONE);

    }

    @Override
    protected void initData() {
        loginModel = ViewModelProviders.of(this).get(LoginModel.class);

        loginModel.getScuucessData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                shortToast(s);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
        loginModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                shortToast(s);
            }
        });
    }


    @OnClick({R.id.btn_registered, R.id.btn_pwdLogin, R.id.btn_codeLogin, R.id.btn_back, R.id.btn_wxLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_wxLogin:
                aaa();
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_registered:
                startActivity(new Intent(this, RegisteredActivity.class));
                break;
            case R.id.btn_pwdLogin:
                switchFragment(0);
                break;
            case R.id.btn_codeLogin:
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
            btnPwdLogin.setVisibility(View.GONE);
            btnCodeLogin.setVisibility(View.VISIBLE);
            if (passLoginFragment == null) {
                passLoginFragment = new PassLoginFragment();
                transaction.add(R.id.frameLayout, passLoginFragment);
            }
            if (codeLoginFragment != null) {
                transaction.hide(codeLoginFragment);
            }
            transaction.show(passLoginFragment).commitAllowingStateLoss();
        } else {
            btnPwdLogin.setVisibility(View.VISIBLE);
            btnCodeLogin.setVisibility(View.GONE);
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
          Collection<String> valus= map.values();
          StringBuilder stringBuilder=new StringBuilder();
          for(String value : valus){
              stringBuilder.append(value);
              stringBuilder.append(",");
          }
          tvValues.setText(stringBuilder.toString());
          stringBuilder.reverse();

        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            shortToast("失败了");

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            shortToast("取消了");

        }
    };

}
