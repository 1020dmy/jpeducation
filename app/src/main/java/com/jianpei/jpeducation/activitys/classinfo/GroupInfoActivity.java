package com.jianpei.jpeducation.activitys.classinfo;


import android.content.Intent;
import android.content.res.Configuration;
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
import com.jianpei.jpeducation.activitys.web.KeFuActivity;
import com.jianpei.jpeducation.adapter.ClassInfoTabFragmentAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.classinfo.GroupClassBean;
import com.jianpei.jpeducation.bean.homedata.RegimentInfoBean;
import com.jianpei.jpeducation.fragment.group.GclassInfoFragment;
import com.jianpei.jpeducation.fragment.group.GcommentFragment;
import com.jianpei.jpeducation.fragment.group.GdirectoryFragment;
import com.jianpei.jpeducation.fragment.info.CommentFragment;
import com.jianpei.jpeducation.fragment.info.DirectoryFragment;
import com.jianpei.jpeducation.utils.DisplayUtil;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.pop.SubjectPopup;
import com.jianpei.jpeducation.viewmodel.ClassInfoModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GroupInfoActivity extends BaseActivity {


    @BindView(R.id.viewPage)
    ViewPager2 viewPage;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_shopping)
    ImageView ivShopping;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.iv_black_back)
    ImageView ivBlackBack;
    @BindView(R.id.iv_black_shopping)
    ImageView ivBlackShopping;
    @BindView(R.id.iv_black_share)
    ImageView ivBlackShare;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.ll_status)
    LinearLayout llStatus;
    @BindView(R.id.tv_kefu)
    TextView tvKefu;
    @BindView(R.id.tv_shopping)
    TextView tvShopping;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private String[] tabTitle = {"详情", "目录", "评价"};
    private GclassInfoFragment classInfoFragment;//详情
    //    private GdirectoryFragment directoryFragment;//目录
    private DirectoryFragment directoryFragment;
    //    private GcommentFragment commentFragment;//评价
    private CommentFragment commentFragment;
    private Fragment[] fragments;

    private ClassInfoTabFragmentAdapter classInfoTabFragmentAdapter;
    private int height;

    private ClassInfoModel classInfoModel;


    private RegimentInfoBean regimentInfoBean;

    ///科目列表
    private List<GroupClassBean> mGroupClassBeans;
    private SubjectPopup subjectPopup;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_group_info;
    }

    @Override
    protected void initView() {
        height = DisplayUtil.dp2px(300);
        regimentInfoBean = getIntent().getParcelableExtra("regimentInfoBean");
        classInfoModel = new ViewModelProvider(this).get(ClassInfoModel.class);//初始化model
        //接收来自与ClassInfoFragment的消息
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

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                llStatus.setAlpha(1f);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        classInfoModel.setRegimentInfoBeanMutableLiveData(regimentInfoBean);//传递消息到frgament

        //获取科目信息
        mGroupClassBeans = new ArrayList<>();
        classInfoModel.getGroupClassBeansLiveData().observe(this, new Observer<List<GroupClassBean>>() {
            @Override
            public void onChanged(List<GroupClassBean> groupClassBeans) {
                mGroupClassBeans.addAll(groupClassBeans);

            }
        });
        classInfoModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                shortToast(o);
            }
        });
        viewPage.setUserInputEnabled(false); //true:滑动，false：禁止滑动
        classInfoFragment = new GclassInfoFragment();
        directoryFragment = new DirectoryFragment();
        commentFragment = new CommentFragment();
        fragments = new Fragment[]{classInfoFragment, directoryFragment, commentFragment};

    }

    @Override
    protected void initData() {
        classInfoTabFragmentAdapter = new ClassInfoTabFragmentAdapter(getSupportFragmentManager(), this.getLifecycle(), fragments);
        viewPage.setAdapter(classInfoTabFragmentAdapter);


        new TabLayoutMediator(tabLayout, viewPage, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitle[position]);
            }
        }).attach();


        classInfoModel.groupClass(regimentInfoBean.getPoint_id(), regimentInfoBean.getId());

    }


    @OnClick({R.id.iv_back, R.id.iv_shopping, R.id.iv_share, R.id.iv_black_back, R.id.iv_black_shopping, R.id.iv_black_share, R.id.tv_kefu, R.id.tv_shopping, R.id.submit, R.id.rl_buy})
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
            case R.id.tv_kefu:
                startActivity(new Intent(this, KeFuActivity.class));
                break;
            case R.id.tv_shopping:
                break;
            case R.id.submit:
                if (subjectPopup == null) {
                    subjectPopup = new SubjectPopup(this, mGroupClassBeans,null);
                }
                subjectPopup.showPop();
                break;
            case R.id.rl_buy:
//                if (subjectPopup == null) {
//                    subjectPopup = new SubjectPopup(this, mGroupClassBeans, "", "");
//                }
//                subjectPopup.showPop();
                break;
        }
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        int type = this.getResources().getConfiguration().orientation;
        if (type == Configuration.ORIENTATION_LANDSCAPE) {
            llBottom.setVisibility(View.GONE);
            rlTitle.setVisibility(View.GONE);
            //切换到了横屏
        } else if (type == Configuration.ORIENTATION_PORTRAIT) {
            //切换到了竖屏
            llBottom.setVisibility(View.VISIBLE);
            rlTitle.setVisibility(View.VISIBLE);
        }
    }
}