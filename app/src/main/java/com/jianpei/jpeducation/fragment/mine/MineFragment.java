package com.jianpei.jpeducation.fragment.mine;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.mine.IntegralActivity;
import com.jianpei.jpeducation.activitys.mine.ShoppingCartActivity;
import com.jianpei.jpeducation.activitys.mine.UserCouponActivity;
import com.jianpei.jpeducation.activitys.mine.UserInfoActivity;
import com.jianpei.jpeducation.activitys.mine.UserOrderListActivity;
import com.jianpei.jpeducation.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MineFragment extends BaseFragment {
    @BindView(R.id.civ_head)
    CircleImageView civHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_jifen)
    TextView tvJifen;
    @BindView(R.id.tv_jinbi)
    TextView tvJinbi;
    @BindView(R.id.tv_wait_pay)
    TextView tvWaitPay;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.tv_shop)
    TextView tvShop;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.tv_my_class)
    TextView tvMyClass;
    @BindView(R.id.tv_my_tiku)
    TextView tvMyTiku;
    @BindView(R.id.tv_my_data)
    TextView tvMyData;
    @BindView(R.id.tv_my_moving)
    TextView tvMyMoving;
    @BindView(R.id.ll_share)
    LinearLayout llShare;
    @BindView(R.id.tv_suggest)
    TextView tvSuggest;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.tv_signin)
    TextView tvSignin;


//    @BindView(R.id.btn_info)
//    Button btnInfo;

    @Override
    protected int initLayout() {
        return R.layout.mine_fragment;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData(Context mContext) {

    }

    @OnClick({R.id.civ_head, R.id.tv_wait_pay, R.id.tv_pay, R.id.tv_shop, R.id.tv_coupon, R.id.tv_integral, R.id.tv_my_class, R.id.tv_my_tiku, R.id.tv_my_data, R.id.tv_my_moving, R.id.ll_share, R.id.tv_suggest, R.id.tv_service, R.id.tv_signin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.civ_head://个人信息
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
            case R.id.tv_wait_pay://未支付
                startActivity(new Intent(getActivity(), UserOrderListActivity.class).putExtra("type", 0));
                break;
            case R.id.tv_pay://已支付
                startActivity(new Intent(getActivity(), UserOrderListActivity.class).putExtra("type", 1));
                break;
            case R.id.tv_shop://购物车
                startActivity(new Intent(getActivity(), ShoppingCartActivity.class));
                break;
            case R.id.tv_coupon://优惠券
                startActivity(new Intent(getActivity(), UserCouponActivity.class));
                break;
            case R.id.tv_signin://签到
            case R.id.tv_integral://积分
                startActivity(new Intent(getActivity(), IntegralActivity.class));
                break;
            case R.id.tv_my_class://我的课程
                break;
            case R.id.tv_my_tiku://我的题库
                break;
            case R.id.tv_my_data://我的资料
                shortToast("我的资料");
                break;
            case R.id.tv_my_moving://我的动态
                break;
            case R.id.ll_share://邀请好友
                break;
            case R.id.tv_suggest://意见反馈
                break;
            case R.id.tv_service://客服中心
                break;

        }
    }


//    @OnClick(R.id.btn_info)
//    public void onViewClicked() {
//        startActivity(new Intent(getActivity(), UserInfoActivity.class));
//    }
}
