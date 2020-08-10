package com.jianpei.jpeducation.activitys.mine;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.base.MyApplication;
import com.jianpei.jpeducation.bean.VersionDetectBean;
import com.jianpei.jpeducation.utils.AppUtils;
import com.jianpei.jpeducation.viewmodel.VersionDetectModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpVersionActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_current)
    TextView tvCurrent;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_up_version)
    TextView tvUpVersion;
    @BindView(R.id.btn_up)
    Button btnUp;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_up_tip)
    TextView tvUpTip;

    private VersionDetectModel versionDetectModel;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_up_version;
    }

    @Override
    protected void initView() {
        tvTitle.setText("版本更新");

        versionDetectModel = new ViewModelProvider(this).get(VersionDetectModel.class);

    }

    @Override
    protected void initData() {

        versionDetectModel.getVersionDetectLiveData().observe(this, new Observer<VersionDetectBean>() {
            @Override
            public void onChanged(VersionDetectBean versionDetectBean) {
                dismissLoading();
                tvCurrent.setVisibility(View.GONE);
                btnUp.setVisibility(View.VISIBLE);
                tvUpTip.setVisibility(View.VISIBLE);
                tvVersion.setText("当前版本V" + AppUtils.getVersionName(MyApplication.getInstance()));
                tvUpVersion.setText("更新V" + versionDetectBean.getApp_version() + "版本");
                tvUpTip.setText(versionDetectBean.getHint());

            }
        });
        versionDetectModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast("");
                tvVersion.setText("V" + AppUtils.getVersionName(MyApplication.getInstance()));
            }
        });

        showLoading("");
        versionDetectModel.versionDetect();

    }


    @OnClick({R.id.iv_back, R.id.btn_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_up:
                break;
        }
    }
}
