package com.jianpei.jpeducation.activitys.mine;


import android.widget.ImageView;
import android.widget.TextView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MyClassActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_my_class;
    }

    @Override
    protected void initView() {
        tvTitle.setText("我的课程");

    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
