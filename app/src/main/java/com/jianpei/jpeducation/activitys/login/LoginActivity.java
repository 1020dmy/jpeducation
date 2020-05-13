package com.jianpei.jpeducation.activitys.login;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jianpei.jpeducation.Constants;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.MainActivity;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.presenter.LoginPresenter;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private LoginPresenter loginPresenter;
    private IWXAPI api;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);


    }

    @Override
    protected void initData() {
        loginPresenter = ViewModelProviders.of(this).get(LoginPresenter.class);

        loginPresenter.getScuucessData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissDialog();
                shortToast(s);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
        loginPresenter.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissDialog();
                shortToast(s);
            }
        });
    }


    @OnClick({R.id.btn_login, R.id.imageButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                showDialog("登陆中...");
                loginPresenter.login(etName.getText().toString(), etPwd.getText().toString());
                break;
            case R.id.imageButton:
                weixinLogin();
                break;
        }
    }

    void weixinLogin() {
        if (!api.isWXAppInstalled()) {
            shortToast("您的设备未安装微信客户端");
        } else {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            api.sendReq(req);
        }

    }
}
