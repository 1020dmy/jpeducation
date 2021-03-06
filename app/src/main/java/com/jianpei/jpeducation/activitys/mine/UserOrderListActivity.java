package com.jianpei.jpeducation.activitys.mine;


import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jianpei.jpeducation.Constants;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.TabFragmentAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.fragment.mine.order.AllOrderFragment;
import com.jianpei.jpeducation.fragment.mine.order.CompleteOrderFragment;
import com.jianpei.jpeducation.fragment.mine.order.WaitPayOrderFragment;
import com.jianpei.jpeducation.viewmodel.DataNoticeChangeModel;

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

    private DataNoticeChangeModel dataNoticeChangeModel;


    private Fragment[] fragments = {new AllOrderFragment(), new WaitPayOrderFragment(), new CompleteOrderFragment()};

//    private TabFragmentAdapter tabFragmentAdapter;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_user_order_list;
    }

    @Override
    protected void initView() {
        tvTitle.setText("我的订单");
        type = getIntent().getIntExtra("type", 0);

        dataNoticeChangeModel = new ViewModelProvider(this).get(DataNoticeChangeModel.class);

        viewPage.setUserInputEnabled(false); //true:滑动，false：禁止滑动

        viewPage.setOffscreenPageLimit(fragments.length);
        viewPage.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        new TabLayoutMediator(tabLayout, viewPage, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitles[position]);
            }
        }).attach();

        if (type == 0) {
            viewPage.setCurrentItem(1);
        } else {
            viewPage.setCurrentItem(2);
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Constants.orderIspay == 1) {
            Constants.orderIspay = 0;
            dataNoticeChangeModel.getNoticeChangeLiveData().setValue("更新数据");


        }
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

}
