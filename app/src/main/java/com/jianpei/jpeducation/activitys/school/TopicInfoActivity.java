package com.jianpei.jpeducation.activitys.school;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.TabFragmentAdapter;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.bean.school.TopicBean;
import com.jianpei.jpeducation.fragment.school.TopicInfoHotFragment;
import com.jianpei.jpeducation.fragment.school.TopicInfoNewFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class TopicInfoActivity extends BaseNoStatusActivity {

    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_topic_title)
    TextView tvTopicTitle;
    @BindView(R.id.tv_read_num)
    TextView tvReadNum;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager2 viewPage;


    private TopicBean topicBean;

    private String[] tabTitle = {"热门", "最新"};


    @Override
    protected int setLayoutView() {
        return R.layout.activity_topic_info;
    }

    @Override
    protected void initView() {
        setTitleViewPadding(tvStatus);
        topicBean = getIntent().getParcelableExtra("topicBean");
        tvTopicTitle.setText("#话题：" + topicBean.getTitle() + "!");
        tvReadNum.setText("阅读量：" + topicBean.getView_num());
        Fragment[] fragments = {new TopicInfoHotFragment(topicBean.getId()), new TopicInfoNewFragment(topicBean.getId())};

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
