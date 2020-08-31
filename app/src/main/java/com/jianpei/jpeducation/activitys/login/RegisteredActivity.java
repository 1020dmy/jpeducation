package com.jianpei.jpeducation.activitys.login;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.MainActivity;
import com.jianpei.jpeducation.activitys.web.GuiZeActivity;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.base.BaseModelActivity;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.utils.CountDownTimerUtils;
import com.jianpei.jpeducation.utils.MyTextWatcher;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.viewmodel.RegisteredModel;
import com.jianpei.jpeducation.viewmodel.SendCodeModel;

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

    private RegisteredModel registeredModel;
    private SendCodeModel sendCodeModel;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_registered;
    }

    @Override
    protected void initView() {
//        setTitleViewPadding(tvStatus);
        tvTitle.setText(getResources().getString(R.string.reg_title));
        //
        sendCodeModel = new ViewModelProvider(this).get(SendCodeModel.class);
        registeredModel = new ViewModelProvider(this).get(RegisteredModel.class);

    }

    @Override
    protected void initData() {
        countDownTimerUtils = new CountDownTimerUtils(tvSendCode, 60 * 1000, 1000);


        etPhone.addTextChangedListener(new MyTextWatcher(ivPhoneCancle));
        etCode.addTextChangedListener(new MyTextWatcher(ivCodeCancle));
        etPwdR.addTextChangedListener(new MyTextWatcher(ivPwdrCancle));
        etPwd.addTextChangedListener(new MyTextWatcher(ivPwdCancle));

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

        registeredModel.getScuucessData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                shortToast(s);
                tvTip.setText("");
                startActivity(new Intent(RegisteredActivity.this, MainActivity.class));
                finish();
            }
        });

        registeredModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                tvTip.setText(s);

            }
        });


    }


    @OnClick({R.id.iv_back, R.id.tv_sendCode, R.id.btn_next, R.id.tv_bottom_xieyi, R.id.iv_phone_cancle, R.id.iv_code_cancle, R.id.iv_pwd_cancle, R.id.iv_pwdr_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_sendCode:
                showLoading("");
                sendCodeModel.sendCode(etPhone.getText().toString(), "regist");
                break;
            case R.id.btn_next:
                showLoading("");
                registeredModel.register(etPhone.getText().toString(), etCode.getText().toString(), etPwd.getText().toString(), etPwdR.getText().toString());
                break;
            case R.id.tv_bottom_xieyi:
                startActivity(new Intent(this, GuiZeActivity.class).putExtra("title", R.string.xieyi_name).putExtra("webUrl", SpUtils.getValue(SpUtils.UserProtocol)));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimerUtils.onDestroy();
        countDownTimerUtils = null;
    }


}
