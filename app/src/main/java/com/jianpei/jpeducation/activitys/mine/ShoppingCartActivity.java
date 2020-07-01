package com.jianpei.jpeducation.activitys.mine;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.web.KeFuActivity;
import com.jianpei.jpeducation.adapter.ShoppingCatAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.CouponDataBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ShoppingCartActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_orderNum)
    TextView tvOrderNum;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
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

    int payType = 1;


    private ShoppingCatAdapter shoppingCatAdapter;

    private ArrayList<CouponDataBean.CouponData> mCouponDatas;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_shopping_cart;
    }

    @Override
    protected void initView() {

        tvTitle.setText("购物车");
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.orderconfirm_kefu);
        llWeixinPay.setEnabled(false);
        ivWeixin.setEnabled(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void initData() {
        shoppingCatAdapter = new ShoppingCatAdapter();

        shoppingCatAdapter.setMyItemDeleteListener(new ShoppingCatAdapter.MyItemDeleteListener() {
            @Override
            public void onDeleteClick(int position, String aaa) {
                shortToast("删除" + position);

            }
        });

        recyclerView.setAdapter(shoppingCatAdapter);


    }


    @OnClick({R.id.iv_back, R.id.iv_right, R.id.ll_quan, R.id.ll_weixin_pay, R.id.ll_zhifubao_pay, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_right:
                startActivity(new Intent(this, KeFuActivity.class));
                break;
            case R.id.ll_quan:
//                if (mCouponDatas == null || mCouponDatas.size() == 0) {
//                    orderConfirmModel.couponData("1", "10", "1");
//                } else {
//                    showPop();
//                }
                break;
            case R.id.ll_weixin_pay:
                changeStatus(1);
                break;
            case R.id.ll_zhifubao_pay:
                changeStatus(2);
                break;
            case R.id.submit:
                break;
        }
    }

    public void changeStatus(int type) {
        payType = type;
        if (type == 1) {
            llWeixinPay.setEnabled(false);
            ivWeixin.setEnabled(false);
            llZhifubaoPay.setEnabled(true);
            ivZhifubao.setEnabled(true);
        } else {
            llWeixinPay.setEnabled(true);
            ivWeixin.setEnabled(true);
            llZhifubaoPay.setEnabled(false);
            ivZhifubao.setEnabled(false);
        }

    }
}
