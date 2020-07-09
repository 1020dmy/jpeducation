package com.jianpei.jpeducation.activitys.mine.mclass;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.TabFragmentAdapter;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.bean.mclass.MyClassBean;
import com.jianpei.jpeducation.fragment.mine.mclass.PlayerCommentFragment;
import com.jianpei.jpeducation.fragment.mine.mclass.PlayerListFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class ClassPlayerActivity extends BaseNoStatusActivity {


    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_download)
    ImageView ivDownload;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.aliyunVodPlayerView)
    AliyunVodPlayerView aliyunVodPlayerView;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager2 viewPage;
    private MyClassBean myClassBean;

    private String[] tabTitle = {"目录", "评价"};

    @Override
    protected int setLayoutView() {
        return R.layout.activity_class_player;
    }

    @Override
    protected void initView() {
        setTitleViewPadding2(tvStatus);
        myClassBean = getIntent().getParcelableExtra("myClassBean");
        Fragment[] fragments = {new PlayerListFragment(myClassBean.getCid()), new PlayerCommentFragment(myClassBean.getCid())};
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


    @OnClick({R.id.iv_back, R.id.iv_download, R.id.iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_download:
                break;
            case R.id.iv_share:
                break;
        }
    }
}
