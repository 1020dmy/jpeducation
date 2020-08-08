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
import com.jianpei.jpeducation.fragment.mine.integralandgold.AllFragment;
import com.jianpei.jpeducation.fragment.mine.integralandgold.ExpenditureFragment;
import com.jianpei.jpeducation.fragment.mine.integralandgold.IncomeFragment;
import com.jianpei.jpeducation.utils.SpUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class IntegralDetailsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
//    @BindView(R.id.tv_name)
//    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager2 viewPage;


    private Fragment[] fragments = {new AllFragment(),new IncomeFragment(), new ExpenditureFragment()};


    private String[] tabTitle = {"全部", "收入", "支出"};

    @Override
    protected int setLayoutView() {
        return R.layout.activity_integral_details;
    }

    @Override
    protected void initView() {
        tvTitle.setText("积分明细");

        viewPage.setUserInputEnabled(false); //true:滑动，false：禁止滑动

        viewPage.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));

        new TabLayoutMediator(tabLayout, viewPage, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitle[position]);
            }
        }).attach();


    }

    @Override
    protected void initData() {
        String price = getIntent().getStringExtra("integral");
        tvPrice.setText(price);
//        String name = SpUtils.getValue(SpUtils.USERNAME);
//        tvName.setText(name);
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }


}
