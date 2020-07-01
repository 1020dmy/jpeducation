package com.jianpei.jpeducation.activitys.mine;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserOrderListActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private int type;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_user_order_list;
    }

    @Override
    protected void initView() {
        tvTitle.setText("我的订单");
        type = getIntent().getIntExtra("type", 0);
        if(type==0){//待支付

        }else{//显示已经支付

        }

    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
