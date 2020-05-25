package com.jianpei.jpeducation.fragment.login;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.utils.CountDownTimerUtils;
import com.jianpei.jpeducation.viewmodel.CodeLoginModel;
import com.jianpei.jpeducation.viewmodel.LauncherModel;

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
    protected CountDownTimerUtils countDownTimerUtils;

    @Override
    protected int initLayout() {
        return R.layout.fragment_code;
    }

    @Override
    protected void initView(View view) {

        codeLoginModel = ViewModelProviders.of(this).get(CodeLoginModel.class);
        countDownTimerUtils = new CountDownTimerUtils(tvSendCode, 60 * 1000, 1000);


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
                shortToast(s);
                countDownTimerUtils.start();


            }
        });

    }

    @OnClick({R.id.tv_sendCode, R.id.btn_next})
    public void onViewClicked(View view) {
        tvTip.setText("");
        switch (view.getId()) {
            case R.id.tv_sendCode:
                showLoading("");
                codeLoginModel.sendCode(etPhone.getText().toString());
                break;
            case R.id.btn_next:
                showLoading("");
                codeLoginModel.codeLogin(etPhone.getText().toString(), etCode.getText().toString());
                break;
        }
    }
}
