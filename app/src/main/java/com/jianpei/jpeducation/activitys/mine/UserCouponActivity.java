package com.jianpei.jpeducation.activitys.mine;


import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.TabFragmentAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.fragment.mine.coupon.CouponAvailableFragment;
import com.jianpei.jpeducation.fragment.mine.coupon.CouponExpiredFragment;
import com.jianpei.jpeducation.fragment.mine.coupon.CouponUsedFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class UserCouponActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager2 viewPage;

    private String[] tabsTitle = {"可用", "已用", "过期"};


    private TabFragmentAdapter tabFragmentAdapter;


    private int formActivity;

    private String cat_id;

    private String group_id;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_user_coupon;
    }

    @Override
    protected void initView() {
        tvTitle.setText("我的优惠券");
        formActivity = getIntent().getIntExtra("formActivity", -1);

        cat_id = getIntent().getStringExtra("cat_id");
        group_id = getIntent().getStringExtra("group_id");


        viewPage.setUserInputEnabled(false); //true:滑动，false：禁止滑动

        Fragment[] fragments = {new CouponAvailableFragment(formActivity,cat_id,group_id), new CouponUsedFragment(), new CouponExpiredFragment()};
        tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments);
        viewPage.setAdapter(tabFragmentAdapter);


        new TabLayoutMediator(tabLayout, viewPage, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabsTitle[position]);
            }
        }).attach();


    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {

        finish();
    }
}
