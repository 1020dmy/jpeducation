package com.jianpei.jpeducation.activitys.login;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseModelActivity;
import com.jianpei.jpeducation.utils.CountDownTimerUtils;
import com.jianpei.jpeducation.utils.MyTextWatcher;
import com.jianpei.jpeducation.viewmodel.ForgetPwdModel;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPwdActivity extends BaseModelActivity<ForgetPwdModel, String> {


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
    protected CountDownTimerUtils countDownTimerUtils;
    @BindView(R.id.iv_phone_cancle)
    ImageView ivPhoneCancle;
    @BindView(R.id.iv_code_cancle)
    ImageView ivCodeCancle;
    @BindView(R.id.iv_pwd_cancle)
    ImageView ivPwdCancle;
    @BindView(R.id.iv_pwdr_cancle)
    ImageView ivPwdrCancle;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void initView() {
        setTitleViewPadding(tvStatus);

        tvTitle.setText(getResources().getString(R.string.forget_title));
        etPhone.addTextChangedListener(new MyTextWatcher(ivPhoneCancle));
        etCode.addTextChangedListener(new MyTextWatcher(ivCodeCancle));
        etPwdR.addTextChangedListener(new MyTextWatcher(ivPwdrCancle));
        etPwd.addTextChangedListener(new MyTextWatcher(ivPwdCancle));

        initViewModel();//初始化data

    }

    @Override
    protected void initData() {
        countDownTimerUtils = new CountDownTimerUtils(tvSendCode, 60 * 1000, 1000);


    }

    @Override
    protected void onError(String message) {
        if ("sjl".equals(message)) {
            shortToast("验证码发送成功");
            countDownTimerUtils.start();
            tvTip.setText("");
        } else {
            tvTip.setText(message);
        }
    }

    @Override
    protected void onSuccess(String data) {
        shortToast(data);
        tvTip.setText("");
        finish();
    }

    @OnClick({R.id.iv_back, R.id.tv_sendCode, R.id.btn_next, R.id.iv_phone_cancle, R.id.iv_code_cancle, R.id.iv_pwd_cancle, R.id.iv_pwdr_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_sendCode:
                showLoading("");
                mViewModel.sendCode(etPhone.getText().toString());
                break;
            case R.id.btn_next:
                showLoading("");
                mViewModel.codeLogin(etPhone.getText().toString(), etCode.getText().toString(), etPwd.getText().toString(), etPwdR.getText().toString());
                break;
            case R.id.iv_phone_cancle:
                etPhone.setText("");
                break;
            case R.id.iv_code_cancle:
                etCode.setText("");
                break;
            case R.id.iv_pwd_cancle:
                etPwd.setText("");
                break;
            case R.id.iv_pwdr_cancle:
                etPwdR.setText("");
                break;
        }
    }
}
