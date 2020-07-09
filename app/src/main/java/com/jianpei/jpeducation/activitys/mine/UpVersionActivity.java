package com.jianpei.jpeducation.activitys.mine;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseActivity;

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

    @Override
    protected int setLayoutView() {
        return R.layout.activity_up_version;
    }

    @Override
    protected void initView() {
        tvTitle.setText("版本更新");

    }

    @Override
    protected void initData() {

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
