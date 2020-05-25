package com.jianpei.jpeducation.fragment.login;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.login.ForgetPwdActivity;
import com.jianpei.jpeducation.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class PassLoginFragment extends BaseFragment {


    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwdR)
    EditText etPwdR;
    @BindView(R.id.tv_forgetPwd)
    TextView tvForgetPwd;
    @BindView(R.id.btn_next)
    Button btnNext;

    @Override
    protected int initLayout() {
        return R.layout.fragment_pass_login;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData(Context mContext) {

    }

    @OnClick({R.id.tv_forgetPwd, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forgetPwd:
                startActivity(new Intent(getActivity(), ForgetPwdActivity.class));
                break;
            case R.id.btn_next:
                break;
        }
    }
}
