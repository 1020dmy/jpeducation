package com.jianpei.jpeducation.activitys.mine;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.login.LoginActivity;
import com.jianpei.jpeducation.activitys.web.GuiZeActivity;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.base.MyApplication;
import com.jianpei.jpeducation.utils.AppUtils;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.viewmodel.LoginModel;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_aboutus)
    TextView tvAboutus;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.ll_version)
    LinearLayout llVersion;
    @BindView(R.id.tv_xieyi)
    TextView tvXieyi;
    @BindView(R.id.btn_signout)
    Button btnSignout;

    private LoginModel loginModel;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        tvTitle.setText("设置");
        if (TextUtils.isEmpty(SpUtils.getValue(SpUtils.ID))) {
            btnSignout.setVisibility(View.GONE);
        } else {
            btnSignout.setVisibility(View.VISIBLE);
        }

        loginModel=new ViewModelProvider(this).get(LoginModel.class);
    }

    @Override
    protected void initData() {

        tvVersion.setText("V" + AppUtils.getVersionName(MyApplication.getInstance()));

        loginModel.getLoginOutLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                shortToast(s);
                SpUtils.remove(SpUtils.ID);
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                finish();
            }
        });
        loginModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                shortToast(o);
                btnSignout.setEnabled(true);
            }
        });

    }


    @OnClick({R.id.iv_back, R.id.tv_aboutus, R.id.ll_version, R.id.tv_xieyi, R.id.btn_signout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_aboutus:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.ll_version:
                startActivity(new Intent(this, UpVersionActivity.class));
                break;
            case R.id.tv_xieyi:
                startActivity(new Intent(this, GuiZeActivity.class).putExtra("webUrl", SpUtils.getValue(SpUtils.UserProtocol)).putExtra("title", R.string.xieyi_name));
                break;
            case R.id.btn_signout:
                 btnSignout.setEnabled(false);
                 loginModel.loginOut();
                break;
        }
    }
}
