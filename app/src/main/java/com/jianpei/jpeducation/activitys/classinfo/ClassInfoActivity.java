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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.login.LoginActivity;
import com.jianpei.jpeducation.activitys.mine.ShoppingCartActivity;
import com.jianpei.jpeducation.activitys.order.OrderConfirmActivity;
import com.jianpei.jpeducation.adapter.NTabFragmentAdapter;
import com.jianpei.jpeducation.adapter.TabFragmentAdapter;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.bean.classinfo.ClassInfoBean;
import com.jianpei.jpeducation.bean.classinfo.GroupClassBean;
import com.jianpei.jpeducation.bean.classinfo.ImputedPriceBean;
import com.jianpei.jpeducation.bean.order.MIneOrderInfoBean;
import com.jianpei.jpeducation.fragment.info.ClassInfoFragment;
import com.jianpei.jpeducation.fragment.info.CommentFragment;
import com.jianpei.jpeducation.fragment.info.DirectoryFragment;
import com.jianpei.jpeducation.utils.DisplayUtil;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.utils.pop.SubjectPopup;
import com.jianpei.jpeducation.viewmodel.ClassInfoModel;
import com.mantis.imview.ui.activity.ChatActivity;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class ClassInfoActivity extends BaseNoStatusActivity {


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

    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    private String[] tabTitle = {"详情", "目录", "评价"};

    private ClassInfoFragment classInfoFragment;//详情
    private DirectoryFragment directoryFragment;//目录
    private CommentFragment commentFragment;//评价
    private Fragment[] fragments;


    private NTabFragmentAdapter nTabFragmentAdapter;

    private ClassInfoModel classInfoModel;

    private int height;

//    private GroupInfoBean groupInfoBean;

    private String groupId, catId;//

    private SubjectPopup subjectPopup;

    private List<GroupClassBean> mGroupClassBeans;


    private ClassInfoBean mClassInfoBean;

    //是否添加购物车
    private boolean isShop;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_class_info;
    }

    @Override
    protected void initView() {
        height = DisplayUtil.dp2px(300);
        //初始化分享

        groupId = getIntent().getStringExtra("groupId");
        catId = getIntent().getStringExtra("catId");

        classInfoFragment = new ClassInfoFragment(groupId, catId);
        directoryFragment = new DirectoryFragment(groupId);
        commentFragment = new CommentFragment(groupId);
        fragments = new Fragment[]{classInfoFragment, directoryFragment, commentFragment};


        classInfoModel = new ViewModelProvider(this).get(ClassInfoModel.class);//初始化model
        /**
         * 课程详情
         */
        classInfoModel.getClassInfoBeanLiveData().observe(this, new Observer<ClassInfoBean>() {
            @Override
            public void onChanged(ClassInfoBean classInfoBean) {
                mClassInfoBean = classInfoBean;

            }
        });

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

        //获取科目信息
        classInfoModel.getGroupClassBeansLiveData().observe(this, new Observer<List<GroupClassBean>>() {
            @Override
            public void onChanged(List<GroupClassBean> groupClassBeans) {
                dismissLoading();
                if (mGroupClassBeans == null)
                    mGroupClassBeans = new ArrayList<>();
                mGroupClassBeans.addAll(groupClassBeans);
                showPow();

            }
        });
        //获取 购买课程下单/计算价格结果
        classInfoModel.getClassGenerateOrderBeanLiveData().observe(this, new Observer<MIneOrderInfoBean>() {
            @Override
            public void onChanged(MIneOrderInfoBean classGenerateOrderBean) {
                dismissLoading();
                startActivity(new Intent(ClassInfoActivity.this, OrderConfirmActivity.class).putExtra("classGenerateOrderBean", classGenerateOrderBean).putExtra("type", "ClassInfo"));
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


    }

    @Override
    protected void initData() {
        viewPager.setUserInputEnabled(false);
        viewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(), this.getLifecycle(), fragments));
        viewPager.setOffscreenPageLimit(fragments.length);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                tab.setText(tabTitle[position]);

            }
        }).attach();
    }


    @OnClick({R.id.iv_back, R.id.iv_shopping, R.id.iv_share, R.id.iv_black_back, R.id.iv_black_shopping, R.id.iv_black_share, R.id.submit, R.id.tv_kefu, R.id.tv_shopping})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.iv_black_back:
                finish();
                break;
            case R.id.iv_shopping:
            case R.id.iv_black_shopping:
                if (TextUtils.isEmpty(SpUtils.getValue(SpUtils.ID))) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    startActivity(new Intent(this, ShoppingCartActivity.class));
                }
                break;
            case R.id.iv_share:
            case R.id.iv_black_share:
                if (mShareAction == null) {
                    initShare();
                }
                mShareAction.open();

                break;
            case R.id.submit:
                isShop = false;
                if (mGroupClassBeans != null && mGroupClassBeans.size() != 0) {
                    showPow();
                } else {
                    showLoading("");
                    classInfoModel.groupClass(groupId, "");
                }
                break;
            case R.id.tv_kefu:
//                startActivity(new Intent(this, KeFuActivity.class));
                startActivity(new Intent(this, ChatActivity.class));
                break;
            case R.id.tv_shopping:
                isShop = true;
                if (mGroupClassBeans != null && mGroupClassBeans.size() != 0) {
                    showPow();
                } else {
                    showLoading("");
                    classInfoModel.groupClass(groupId, "");
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
                    subjectPopup.dismiss();
                    if (TextUtils.isEmpty(SpUtils.getValue(SpUtils.ID))) {
                        startActivity(new Intent(ClassInfoActivity.this, LoginActivity.class));
                        return;
                    }
                    if (!isShop) {
                        showLoading("");
                        classInfoModel.classGenerateOrder("1", groupId, "0", "0", subjectPopup.getClassIds(), subjectPopup.getSuitesIds(), "", "");
                    } else {
                        classInfoModel.insertCar(groupId, subjectPopup.getClassIds(), subjectPopup.getSuitesIds());
                    }
                }
            });

            subjectPopup.setMyItemOnClickListener(new SubjectPopup.MyItemClickListener() {
                @Override
                public void onClicker() {
                    classInfoModel.imputedPrice(groupId, subjectPopup.getClassIds(), subjectPopup.getSuitesIds(), "");
                }
            });
        }
        subjectPopup.showPop();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        int type = this.getResources().getConfiguration().orientation;
        if (type == Configuration.ORIENTATION_LANDSCAPE) {
            L.e("==========", "切换到了横屏");
            llBottom.setVisibility(View.GONE);
            rlTitle.setVisibility(View.GONE);
            //切换到了横屏
        } else if (type == Configuration.ORIENTATION_PORTRAIT) {
            L.e("==========", "切换到了竖屏");
            //切换到了竖屏
            llBottom.setVisibility(View.VISIBLE);
            rlTitle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();

    }


}
