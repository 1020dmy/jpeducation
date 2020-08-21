package com.jianpei.jpeducation.activitys.classinfo;


import android.content.Intent;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.mine.ShoppingCartActivity;
import com.jianpei.jpeducation.activitys.order.OrderConfirmActivity;
import com.jianpei.jpeducation.activitys.web.KeFuActivity;
import com.jianpei.jpeducation.adapter.NTabFragmentAdapter;
import com.jianpei.jpeducation.adapter.TabFragmentAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.classinfo.ClassInfoBean;
import com.jianpei.jpeducation.bean.classinfo.GroupClassBean;
import com.jianpei.jpeducation.bean.classinfo.ImputedPriceBean;
import com.jianpei.jpeducation.bean.homedata.RegimentInfoBean;
import com.jianpei.jpeducation.bean.order.MIneOrderInfoBean;
import com.jianpei.jpeducation.fragment.group.GclassInfoFragment;
import com.jianpei.jpeducation.fragment.info.CommentFragment;
import com.jianpei.jpeducation.fragment.info.DirectoryFragment;
import com.jianpei.jpeducation.utils.DisplayUtil;
import com.jianpei.jpeducation.utils.pop.GroupingPopup;
import com.jianpei.jpeducation.utils.pop.SubjectPopup;
import com.jianpei.jpeducation.viewmodel.ClassInfoModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GroupInfoActivity extends BaseActivity {


    @BindView(R.id.viewPage)
    ViewPager viewPage;
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
    //    @BindView(R.id.tv_shopping)
//    TextView tvShopping;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private String[] tabTitle = {"详情", "目录", "评价"};
    private GclassInfoFragment classInfoFragment;//详情
    private DirectoryFragment directoryFragment;
    private CommentFragment commentFragment;
    private Fragment[] fragments;

    //    private TabFragmentAdapter classInfoTabFragmentAdapter;
    private NTabFragmentAdapter nTabFragmentAdapter;
    private int height;

    private ClassInfoModel classInfoModel;


//    private RegimentInfoBean regimentInfoBean;

    private String groupId;

    ///科目列表
    private List<GroupClassBean> mGroupClassBeans;
    private SubjectPopup subjectPopup;
    private ClassInfoBean mClassInfoBean;
    //所参团的ID
    private String pointId, id;
    //类型
    private String goods_type = "2";

    @Override
    protected int setLayoutView() {
        return R.layout.activity_group_info;
    }

    @Override
    protected void initView() {

        height = DisplayUtil.dp2px(300);
//        regimentInfoBean = getIntent().getParcelableExtra("regimentInfoBean");
        pointId = getIntent().getStringExtra("pointId");
        id = getIntent().getStringExtra("id");


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

//        classInfoModel.setRegimentInfoBeanMutableLiveData(regimentInfoBean);//传递消息到frgament

        //获取科目信息
        mGroupClassBeans = new ArrayList<>();
        classInfoModel.getGroupClassBeansLiveData().observe(this, new Observer<List<GroupClassBean>>() {
            @Override
            public void onChanged(List<GroupClassBean> groupClassBeans) {
                dismissLoading();
                mGroupClassBeans.addAll(groupClassBeans);
                showPow();

            }
        });

        //获取 购买课程下单/计算价格结果
        classInfoModel.getClassGenerateOrderBeanLiveData().observe(this, new Observer<MIneOrderInfoBean>() {
            @Override
            public void onChanged(MIneOrderInfoBean classGenerateOrderBean) {
                dismissLoading();
                startActivityForResult(new Intent(GroupInfoActivity.this, OrderConfirmActivity.class)
                        .putExtra("classGenerateOrderBean", classGenerateOrderBean)
                        .putExtra("type", "GroupInfo"), 111);
            }
        });
        //获取计算的价格的结果
        classInfoModel.getImputedPriceBeanLiveData().observe(this, new Observer<ImputedPriceBean>() {
            @Override
            public void onChanged(ImputedPriceBean imputedPriceBean) {
                if (subjectPopup != null) {
                    subjectPopup.upDataPrice(imputedPriceBean.getTotal_price(), imputedPriceBean.getPrice(), imputedPriceBean.getIs_material());
                }
            }
        });
        classInfoModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                if (!TextUtils.isEmpty(o))
                    shortToast(o);
            }
        });
        //显示用户当前的拼团
        classInfoModel.getClassInfoBeanLiveData().observe(this, new Observer<ClassInfoBean>() {
            @Override
            public void onChanged(ClassInfoBean classInfoBean) {
                mClassInfoBean = classInfoBean;
                if (classInfoBean.getUser_regiment_info() != null) {
                    showGroupingPow();
                }
            }
        });
        //去参团数据回传，弹出科目选择框
        classInfoModel.getJoinGroupInfoLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                groupId = s;
                goods_type = "2";
                if (mGroupClassBeans.size() != 0) {
                    showPow();
                } else {
                    showLoading("");
                    classInfoModel.groupClass(pointId, id);
                }
            }
        });
//        viewPage.setUserInputEnabled(false); //true:滑动，false：禁止滑动
        classInfoFragment = new GclassInfoFragment(pointId, id);
        directoryFragment = new DirectoryFragment(pointId);
        commentFragment = new CommentFragment(pointId);
        fragments = new Fragment[]{classInfoFragment, directoryFragment, commentFragment};

    }

    @Override
    protected void initData() {

        nTabFragmentAdapter = new NTabFragmentAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragments, tabTitle);

        viewPage.setOffscreenPageLimit(3);
        viewPage.setAdapter(nTabFragmentAdapter);
        tabLayout.setupWithViewPager(viewPage);
        //        classInfoTabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), this.getLifecycle(), fragments);
//        viewPage.setAdapter(classInfoTabFragmentAdapter);
//
//
//        new TabLayoutMediator(tabLayout, viewPage, new TabLayoutMediator.TabConfigurationStrategy() {
//            @Override
//            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//                tab.setText(tabTitle[position]);
//            }
//        }).attach();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

//        classInfoModel.setRegimentInfoBeanMutableLiveData(regimentInfoBean);//传递消息到frgament
        classInfoModel.getUpDataLiveData().setValue("");
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.iv_back, R.id.iv_shopping, R.id.iv_share, R.id.iv_black_back, R.id.iv_black_shopping, R.id.iv_black_share, R.id.tv_kefu, R.id.submit, R.id.tv_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.iv_black_back:
                finish();
                break;
            case R.id.iv_shopping:
            case R.id.iv_black_shopping:
                startActivity(new Intent(this, ShoppingCartActivity.class));
                break;
            case R.id.iv_share:
            case R.id.iv_black_share:
                if (mShareAction == null) {
                    initShare();
                }
                mShareAction.open();
                break;
            case R.id.tv_kefu:
                startActivity(new Intent(this, KeFuActivity.class));
                break;
            case R.id.submit:
                groupId = "";
                goods_type = "2";
                if (mClassInfoBean.getUser_regiment_info() != null) {
                    showGroupingPow();
                } else {
                    if (mGroupClassBeans.size() != 0) {
                        showPow();
                    } else {
                        showLoading("");
                        classInfoModel.groupClass(pointId, id);
                    }
                }
                break;
            case R.id.tv_buy:
                goods_type = "1";
                groupId = "";
                if (mGroupClassBeans.size() != 0) {
                    showPow();
                } else {
                    showLoading("");
                    classInfoModel.groupClass(pointId, id);
                }
                break;
        }
    }

    public void showPow() {
        if (subjectPopup == null) {
            subjectPopup = new SubjectPopup(this, mGroupClassBeans, mClassInfoBean);

            subjectPopup.setClassIds(new ArrayList<String>());
            subjectPopup.setSuitesIds(new ArrayList<String>());
            subjectPopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoading("");
                    classInfoModel.classGenerateOrder(goods_type, pointId, "0", "0", subjectPopup.getClassIds(), subjectPopup.getSuitesIds(), id, groupId);

                }
            });

            subjectPopup.setMyItemOnClickListener(new SubjectPopup.MyItemClickListener() {
                @Override
                public void onClicker() {
                    classInfoModel.imputedPrice(pointId, subjectPopup.getClassIds(), subjectPopup.getSuitesIds(), id);
                }
            });
        }
        subjectPopup.showPop();
    }

    private GroupingPopup groupingPopup;

    public void showGroupingPow() {
        if (groupingPopup == null) {
            groupingPopup = new GroupingPopup(this, mClassInfoBean.getUser_regiment_info());
        }
        groupingPopup.showPop();
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