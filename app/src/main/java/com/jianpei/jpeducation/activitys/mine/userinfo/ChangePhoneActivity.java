package com.jianpei.jpeducation.activitys.mine.userinfo;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.utils.CountDownTimerUtils;
import com.jianpei.jpeducation.viewmodel.SendCodeModel;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePhoneActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.iv_code_cancle)
    ImageView ivCodeCancle;
    @BindView(R.id.tv_sendCode)
    TextView tvSendCode;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.btn_next)
    Button btnNext;

    private SendCodeModel sendCodeModel;

    protected CountDownTimerUtils countDownTimerUtils;



    private String phone;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_change_phone;
    }

    @Override
    protected void initView() {
        tvTitle.setText("更换手机");
        phone = getIntent().getStringExtra("phone");


        sendCodeModel = new ViewModelProvider(this).get(SendCodeModel.class);

    }

    @Override
    protected void initData() {
        tvPhone.setText(phone);
        countDownTimerUtils = new CountDownTimerUtils(tvSendCode, 60 * 1000, 1000);

        sendCodeModel.getSuccessCodeLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                countDownTimerUtils.start();

            }
        });
        sendCodeModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });

    }


    @OnClick({R.id.iv_back, R.id.iv_code_cancle, R.id.tv_sendCode, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_code_cancle:
                etCode.setText("");
                break;
            case R.id.tv_sendCode:
                showLoading("");
                sendCodeModel.sendCode(phone, "other");
                break;
            case R.id.btn_next:
                break;
        }
    }
}
