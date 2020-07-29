package com.jianpei.jpeducation.activitys.login;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.MainActivity;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.utils.CountDownTimerUtils;
import com.jianpei.jpeducation.utils.MyTextWatcher;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.viewmodel.BindPhoneModel;
import com.jianpei.jpeducation.viewmodel.SendCodeModel;

import butterknife.BindView;
import butterknife.OnClick;

public class BindPhoneActivity extends BaseNoStatusActivity {

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
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.iv_phone_cancle)
    ImageView ivPhoneCancle;
    @BindView(R.id.iv_code_cancle)
    ImageView ivCodeCancle;

    protected CountDownTimerUtils countDownTimerUtils;

    private String uid;

    private SendCodeModel sendCodeModel;

    private BindPhoneModel bindPhoneModel;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected void initView() {
        setTitleViewPadding(tvStatus);
        tvRegistered.setVisibility(View.GONE);
        etPhone.addTextChangedListener(new MyTextWatcher(ivPhoneCancle));
        etCode.addTextChangedListener(new MyTextWatcher(ivCodeCancle));

        sendCodeModel = new ViewModelProvider(this).get(SendCodeModel.class);
        bindPhoneModel = new ViewModelProvider(this).get(BindPhoneModel.class);


    }

    @Override
    protected void initData() {
        countDownTimerUtils = new CountDownTimerUtils(tvSendCode, 60 * 1000, 1000);
        uid = SpUtils.getValue(SpUtils.ID);

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

        bindPhoneModel.getScuucessData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvTip.setText("");
                shortToast(s);
                startActivity(new Intent(BindPhoneActivity.this, MainActivity.class));
                finish();
            }
        });
        bindPhoneModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                tvTip.setText(s);
            }
        });


    }


    @OnClick({R.id.iv_back, R.id.tv_registered, R.id.tv_sendCode, R.id.btn_next, R.id.iv_phone_cancle, R.id.iv_code_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.tv_registered:
                startActivity(new Intent(this, RegisteredActivity.class));
                break;
            case R.id.tv_sendCode:
                showLoading("");
                sendCodeModel.sendCode(etPhone.getText().toString(), "other");
                break;
            case R.id.btn_next:
                showLoading("");
                bindPhoneModel.bindPhone(uid, etPhone.getText().toString(), etCode.getText().toString());
                break;
            case R.id.iv_phone_cancle:
                etPhone.setText("");
                break;
            case R.id.iv_code_cancle:
                etCode.setText("");
                break;
        }
    }


}
