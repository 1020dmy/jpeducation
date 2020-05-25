package com.jianpei.jpeducation.activitys.login;


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
import com.jianpei.jpeducation.viewmodel.RegisteredModel;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisteredActivity extends BaseActivity {


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
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.tv_bottom_tip)
    TextView tvBottomTip;
    @BindView(R.id.tv_bottom_xieyi)
    TextView tvBottomXieyi;

    @BindView(R.id.tv_tip)
    TextView tvTip;
    protected RegisteredModel registeredModel;

    protected CountDownTimerUtils countDownTimerUtils;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_registered;
    }

    @Override
    protected void initView() {
        tvTitle.setText(getResources().getString(R.string.reg_title));

    }

    @Override
    protected void initData() {
        countDownTimerUtils = new CountDownTimerUtils(tvSendCode, 60 * 1000, 1000);

        registeredModel = ViewModelProviders.of(this).get(RegisteredModel.class);

        registeredModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                tvTip.setText(error);
            }
        });
        registeredModel.getScuucessData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                tvTip.setText("");
                countDownTimerUtils.start();
            }
        });

    }

    @OnClick({R.id.iv_back, R.id.tv_sendCode, R.id.btn_next, R.id.tv_bottom_xieyi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_sendCode:
                registeredModel.sendCode(etPhone.getText().toString());
                break;
            case R.id.btn_next:
                registeredModel.register(etPhone.getText().toString(), etCode.getText().toString(), etPwd.getText().toString(), etPwdR.getText().toString());
                break;
            case R.id.tv_bottom_xieyi:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimerUtils.onDestroy();
        countDownTimerUtils = null;
    }
}
