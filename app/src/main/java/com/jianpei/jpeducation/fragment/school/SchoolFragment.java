package com.jianpei.jpeducation.fragment.school;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.school.PostNewsActivity;
import com.jianpei.jpeducation.adapter.TabFragmentAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.utils.L;

import butterknife.BindView;
import butterknife.OnClick;

public class SchoolFragment extends BaseFragment {


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager2 viewPage;
    @BindView(R.id.iv_post_new)
    ImageView ivPostNew;

    private String[] tabTitles = {"广场", "关注"};


    private Fragment[] fragments = {new SquareFragment(), new AttentionFragment()};

    private TabFragmentAdapter tabFragmentAdapter;


    @Override
    protected int initLayout() {
        return R.layout.fragment_dashboard;
    }

    @Override
    protected void initView(View view) {

        tabFragmentAdapter = new TabFragmentAdapter(getActivity().getSupportFragmentManager(), getLifecycle(), fragments);

        viewPage.setAdapter(tabFragmentAdapter);


        new TabLayoutMediator(tabLayout, viewPage, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitles[position]);
            }
        }).attach();

    }


    @Override
    protected void initData(Context mContext) {

    }

    @OnClick(R.id.iv_post_new)
    public void onViewClicked() {
        startActivityForResult(new Intent(getActivity(), PostNewsActivity.class), 111);
    }
}
