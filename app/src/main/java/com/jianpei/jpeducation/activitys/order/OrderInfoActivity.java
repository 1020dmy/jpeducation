package com.jianpei.jpeducation.activitys.order;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class OrderInfoActivity extends BaseActivity {


    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_group_statue)
    TextView tvGroupStatue;
    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.tv_leader)
    TextView tvLeader;
    @BindView(R.id.civ_item)
    CircleImageView civItem;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.ll_groupInfo)
    LinearLayout llGroupInfo;
    @BindView(R.id.tv_orderNum)
    TextView tvOrderNum;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_class_title)
    TextView tvClassTitle;
    @BindView(R.id.tv_classNames)
    TextView tvClassNames;
    @BindView(R.id.tv_material)
    TextView tvMaterial;
    @BindView(R.id.tv_material_name)
    TextView tvMaterialName;
    @BindView(R.id.line)
    TextView line;
    @BindView(R.id.iv_quan)
    ImageView ivQuan;
    @BindView(R.id.tv_quan)
    TextView tvQuan;
    @BindView(R.id.ll_quan)
    LinearLayout llQuan;
    @BindView(R.id.tv_origin_price)
    TextView tvOriginPrice;
    @BindView(R.id.tv_discount_price)
    TextView tvDiscountPrice;
    @BindView(R.id.tv_real_price)
    TextView tvRealPrice;
    @BindView(R.id.iv_weixin)
    ImageView ivWeixin;
    @BindView(R.id.ll_weixin_pay)
    LinearLayout llWeixinPay;
    @BindView(R.id.iv_zhifubao)
    ImageView ivZhifubao;
    @BindView(R.id.ll_zhifubao_pay)
    LinearLayout llZhifubaoPay;
    @BindView(R.id.tv_pay_price)
    TextView tvPayPrice;
    @BindView(R.id.submit)
    TextView submit;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_order_info;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.iv_back, R.id.iv_right, R.id.button, R.id.ll_weixin_pay, R.id.ll_zhifubao_pay, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.iv_right:
                break;
            case R.id.button:
                break;
            case R.id.ll_weixin_pay:
                break;
            case R.id.ll_zhifubao_pay:
                break;
            case R.id.submit:
                break;
        }
    }
}
