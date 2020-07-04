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
import com.jianpei.jpeducation.fragment.mine.order.AllOrderFragment;
import com.jianpei.jpeducation.fragment.mine.order.CompleteOrderFragment;
import com.jianpei.jpeducation.fragment.mine.order.WaitPayOrderFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class UserOrderListActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager2 viewPage;

    private String[] tabTitles = {"全部", "未完成", "已完成"};

    private int type;


    private Fragment[] fragments = {new AllOrderFragment(), new WaitPayOrderFragment(), new CompleteOrderFragment()};

    private TabFragmentAdapter tabFragmentAdapter;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_user_order_list;
    }

    @Override
    protected void initView() {
        tvTitle.setText("我的订单");
        type = getIntent().getIntExtra("type", 0);


        tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments);

        viewPage.setAdapter(tabFragmentAdapter);


        new TabLayoutMediator(tabLayout, viewPage, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitles[position]);
            }
        }).attach();

        if(type==0){
            viewPage.setCurrentItem(1);
        }else{
            viewPage.setCurrentItem(2);
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
