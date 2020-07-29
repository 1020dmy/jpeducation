package com.jianpei.jpeducation.fragment.login;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.MainActivity;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.utils.CountDownTimerUtils;
import com.jianpei.jpeducation.utils.MyTextWatcher;
import com.jianpei.jpeducation.viewmodel.CodeLoginModel;
import com.jianpei.jpeducation.viewmodel.SendCodeModel;

import butterknife.BindView;
import butterknife.OnClick;

public class CodeLoginFragment extends BaseFragment {


    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_sendCode)
    TextView tvSendCode;
    @BindView(R.id.btn_next)
    Button btnNext;

    @BindView(R.id.tv_tip)
    TextView tvTip;

    protected CodeLoginModel codeLoginModel;
    private SendCodeModel sendCodeModel;
    protected CountDownTimerUtils countDownTimerUtils;
    @BindView(R.id.iv_phone_cancle)
    ImageView ivPhoneCancle;
    @BindView(R.id.iv_code_cancle)
    ImageView ivCodeCancle;


    @Override
    protected int initLayout() {
        return R.layout.fragment_code;
    }

    @Override
    protected void initView(View view) {

        codeLoginModel = new ViewModelProvider(this).get(CodeLoginModel.class);
        sendCodeModel = new ViewModelProvider(this).get(SendCodeModel.class);
        countDownTimerUtils = new CountDownTimerUtils(tvSendCode, 60 * 1000, 1000);

        etPhone.addTextChangedListener(new MyTextWatcher(ivPhoneCancle));
        etCode.addTextChangedListener(new MyTextWatcher(ivCodeCancle));


    }

    @Override
    protected void initData(Context mContext) {
        codeLoginModel.getErrData().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                tvTip.setText(s);
            }
        });
        codeLoginModel.getScuucessData().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                tvTip.setText("");
                shortToast(s);
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();

            }
        });
        sendCodeModel.getSuccessCodeLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                shortToast("验证码发送成功");
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

    }

    @OnClick({R.id.tv_sendCode, R.id.btn_next, R.id.iv_phone_cancle, R.id.iv_code_cancle})
    public void onViewClicked(View view) {
        tvTip.setText("");
        switch (view.getId()) {
            case R.id.tv_sendCode:
                showLoading("");
                sendCodeModel.sendCode(etPhone.getText().toString(), "login");
                break;
            case R.id.btn_next:
                showLoading("");
                codeLoginModel.codeLogin(etPhone.getText().toString(), etCode.getText().toString());
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
