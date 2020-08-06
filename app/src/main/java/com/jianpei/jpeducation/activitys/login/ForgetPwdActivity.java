package com.jianpei.jpeducation.activitys.login;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseModelActivity;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.utils.CountDownTimerUtils;
import com.jianpei.jpeducation.utils.MyTextWatcher;
import com.jianpei.jpeducation.viewmodel.ForgetPwdModel;
import com.jianpei.jpeducation.viewmodel.SendCodeModel;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPwdActivity extends BaseNoStatusActivity {


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
    private ForgetPwdModel forgetPwdModel;
    private SendCodeModel sendCodeModel;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void initView() {
        setTitleViewPadding(tvStatus);
        tvStatus.setVisibility(View.VISIBLE);

        tvTitle.setText(getResources().getString(R.string.forget_title));
        etPhone.addTextChangedListener(new MyTextWatcher(ivPhoneCancle));
        etCode.addTextChangedListener(new MyTextWatcher(ivCodeCancle));
        etPwdR.addTextChangedListener(new MyTextWatcher(ivPwdrCancle));
        etPwd.addTextChangedListener(new MyTextWatcher(ivPwdCancle));

        sendCodeModel = new ViewModelProvider(this).get(SendCodeModel.class);
        forgetPwdModel = new ViewModelProvider(this).get(ForgetPwdModel.class);


    }

    @Override
    protected void initData() {
        countDownTimerUtils = new CountDownTimerUtils(tvSendCode, 60 * 1000, 1000);
        sendCodeModel.getSuccessCodeLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                shortToast(s);
                countDownTimerUtils.start();
                tvTip.setText("");
            }
        });
        sendCodeModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                tvTip.setText(o);
            }
        });
        forgetPwdModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                tvTip.setText(o);

            }
        });
        forgetPwdModel.getScuucessData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                shortToast(o);
                tvTip.setText("");
                finish();

            }
        });


    }



    @OnClick({R.id.iv_back, R.id.tv_sendCode, R.id.btn_next, R.id.iv_phone_cancle, R.id.iv_code_cancle, R.id.iv_pwd_cancle, R.id.iv_pwdr_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_sendCode:
                showLoading("");
                sendCodeModel.sendCode(etPhone.getText().toString(),"reset_pwd");
                break;
            case R.id.btn_next:
                showLoading("");
                forgetPwdModel.codeLogin(etPhone.getText().toString(), etCode.getText().toString(), etPwd.getText().toString(), etPwdR.getText().toString());
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
