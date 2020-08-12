package com.jianpei.jpeducation.activitys.order;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.MainActivity;
import com.jianpei.jpeducation.activitys.mine.mclass.MyClassActivity;
import com.jianpei.jpeducation.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderResultActivity extends BaseActivity {


    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_tip)
    ImageView ivTip;
    @BindView(R.id.btn_one)
    Button btnOne;
    @BindView(R.id.btn_back)
    Button btnBack;

    private String state;
    private String orderId;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_order_result;
    }

    @Override
    protected void initView() {
        state = getIntent().getStringExtra("state");
        orderId=getIntent().getStringExtra("orderId");
        ivBack.setVisibility(View.GONE);

        if ("1".equals(state)) {
            tvTitle.setText("订单完成");
            btnOne.setText("去学习");
            ivTip.setImageResource(R.drawable.ic_order_result_pay_success);
        } else {
            tvTitle.setText("订单失败");
            btnOne.setText("查看订单");
            ivTip.setImageResource(R.drawable.ic_order_result_pay_fail);
        }

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.btn_one, R.id.btn_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_one:
                if ("1".equals(state)) {
                    startActivity(new Intent(this, MyClassActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                } else {
                    startActivity(new Intent(this, OrderInfoActivity.class).putExtra("orderId",orderId).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                    finish();
                }
                finish();
                break;
            case R.id.btn_back:
//                startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
        }
    }
}
