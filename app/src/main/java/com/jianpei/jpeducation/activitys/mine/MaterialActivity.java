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
import com.jianpei.jpeducation.fragment.mine.material.MaterialDownloadFragment;
import com.jianpei.jpeducation.fragment.mine.material.MineMaterialFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MaterialActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager2 viewPage;


    private String[] tabTitle = {"资料下载", "我的资料"};

    private Fragment[] fragments={new MaterialDownloadFragment(),new MineMaterialFragment()};


    @Override
    protected int setLayoutView() {
        return R.layout.activity_material;
    }

    @Override
    protected void initView() {
        tvTitle.setText("我的资料");

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

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
