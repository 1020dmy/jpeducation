package com.jianpei.jpeducation.activitys.login;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.utils.CountDownTimerUtils;
import com.jianpei.jpeducation.viewmodel.ForgetPwdModel;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPwdActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_sendCode)
    TextView tvSendCode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_pwdR)
    EditText etPwdR;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    private ForgetPwdModel forgetPwdModel;
    protected CountDownTimerUtils countDownTimerUtils;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void initView() {
        setTitleViewPadding(tvStatus);

        tvTitle.setText(getResources().getString(R.string.forget_title));

    }

    @Override
    protected void initData() {
        countDownTimerUtils = new CountDownTimerUtils(tvSendCode, 60 * 1000, 1000);

        forgetPwdModel = ViewModelProviders.of(this).get(ForgetPwdModel.class);
        forgetPwdModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                dismissLoading();
                if ("sjl".equals(error)) {
                    shortToast("验证码发送成功");
                    countDownTimerUtils.start();
                    tvTip.setText("");
                } else {
                    tvTip.setText(error);
                }
            }
        });
        forgetPwdModel.getScuucessData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                dismissLoading();
                shortToast(error);
                tvTip.setText("");
                finish();

            }
        });

    }

    @OnClick({R.id.iv_back, R.id.tv_sendCode, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_sendCode:
                showLoading("");
                forgetPwdModel.sendCode(etPhone.getText().toString());
                break;
            case R.id.btn_next:
                showLoading("");
                forgetPwdModel.codeLogin(etPhone.getText().toString(), etCode.getText().toString(), etPwd.getText().toString(), etPwdR.getText().toString());
                break;
        }
    }
}
