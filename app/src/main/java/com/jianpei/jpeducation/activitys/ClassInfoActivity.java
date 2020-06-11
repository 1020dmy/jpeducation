package com.jianpei.jpeducation.activitys;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.ClassInfoTabFragmentAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.fragment.info.ClassInfoFragment;
import com.jianpei.jpeducation.fragment.info.CommentFragment;
import com.jianpei.jpeducation.fragment.info.DirectoryFragment;
import com.jianpei.jpeducation.utils.DisplayUtil;
import com.jianpei.jpeducation.viewmodel.ClassInfoModel;

import butterknife.BindView;
import butterknife.OnClick;


public class ClassInfoActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_shopping)
    ImageView ivShopping;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.tv_kefu)
    TextView tvKefu;
    @BindView(R.id.tv_shopping)
    TextView tvShopping;
    @BindView(R.id.submit)
    TextView submit;

    @BindView(R.id.viewPage)
    ViewPager2 viewPager;
    @BindView(R.id.iv_black_back)
    ImageView ivBlackBack;
    @BindView(R.id.iv_black_shopping)
    ImageView ivBlackShopping;
    @BindView(R.id.iv_black_share)
    ImageView ivBlackShare;
    @BindView(R.id.ll_status)
    LinearLayout llStatus;
    private String[] tabTitle = {"详情", "目录", "评价"};

    private ClassInfoFragment classInfoFragment;//详情
    private DirectoryFragment directoryFragment;//目录
    private CommentFragment commentFragment;//评价
    private Fragment[] fragments;

    private ClassInfoTabFragmentAdapter classInfoTabFragmentAdapter;

    private ClassInfoModel classInfoModel;

    private int height;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_class_info;
    }

    @Override
    protected void initView() {
        height = DisplayUtil.dp2px(300);

        classInfoModel = new ViewModelProvider(this).get(ClassInfoModel.class);//初始化model
        classInfoModel.getTabViewStatus().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer scrollY) {
                if (scrollY <= 0) {
                    llStatus.setAlpha(0);
                    llStatus.setVisibility(View.GONE);
                } else if (scrollY > 0 && scrollY < height) {
                    float scale = (float) scrollY / height;
                    float alpha = (1.0f * scale);
                    llStatus.setVisibility(View.VISIBLE);
                    llStatus.setAlpha(alpha);
                } else {
                    llStatus.setAlpha(1f);
                }


            }
        });

        viewPager.setUserInputEnabled(false); //true:滑动，false：禁止滑动
        classInfoFragment = new ClassInfoFragment();
        directoryFragment = new DirectoryFragment();
        commentFragment = new CommentFragment();
        fragments = new Fragment[]{classInfoFragment, directoryFragment, commentFragment};


    }

    @Override
    protected void initData() {

        classInfoTabFragmentAdapter = new ClassInfoTabFragmentAdapter(getSupportFragmentManager(), this.getLifecycle(), fragments);
        viewPager.setAdapter(classInfoTabFragmentAdapter);


        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitle[position]);
            }
        }).attach();


    }


    @OnClick({R.id.iv_back, R.id.iv_shopping, R.id.iv_share, R.id.iv_black_back, R.id.iv_black_shopping, R.id.iv_black_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.iv_black_back:
                finish();
                break;
            case R.id.iv_shopping:
            case R.id.iv_black_shopping:
                break;
            case R.id.iv_share:
            case R.id.iv_black_share:
                break;
        }
    }
}
