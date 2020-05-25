package com.jianpei.jpeducation.activitys.login;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class BindPhoneActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageButton ivBack;
    @BindView(R.id.tv_registered)
    TextView tvRegistered;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_sendCode)
    TextView tvSendCode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.btn_next)
    Button btnNext;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.iv_back, R.id.tv_registered, R.id.tv_sendCode, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_registered:
                startActivity(new Intent(this, RegisteredActivity.class));
                break;
            case R.id.tv_sendCode:
                break;
            case R.id.btn_next:
                break;
        }
    }
}
