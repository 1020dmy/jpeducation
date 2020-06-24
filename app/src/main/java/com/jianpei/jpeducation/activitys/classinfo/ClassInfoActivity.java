package com.jianpei.jpeducation.activitys.classinfo;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.order.OrderConfirmActivity;
import com.jianpei.jpeducation.activitys.web.KeFuActivity;
import com.jianpei.jpeducation.adapter.ClassInfoTabFragmentAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.classinfo.ClassInfoBean;
import com.jianpei.jpeducation.bean.classinfo.GroupClassBean;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.fragment.info.ClassInfoFragment;
import com.jianpei.jpeducation.fragment.info.CommentFragment;
import com.jianpei.jpeducation.fragment.info.DirectoryFragment;
import com.jianpei.jpeducation.utils.DisplayUtil;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.pop.SubjectPopup;
import com.jianpei.jpeducation.viewmodel.ClassInfoFModel;
import com.jianpei.jpeducation.viewmodel.ClassInfoModel;
import com.jianpei.umeng.ShareActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class ClassInfoActivity extends BaseActivity implements ShareBoardlistener {


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

    @BindView(R.id.tv_now_price)
    TextView tvNowPrice;
    @BindView(R.id.tv_price)
    TextView tvPrice;

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

    private ClassInfoTabFragmentAdapter classInfoTabFragmentAdapter;

    private ClassInfoModel classInfoModel;

    private int height;

    private GroupInfoBean groupInfoBean;

    private SubjectPopup subjectPopup;

    private List<GroupClassBean> mGroupClassBeans;

    //分享相关
    private ShareAction mShareAction;
    private UMShareListener mShareListener;

    //
//    private String originPrice;
//    private String material;
    //
//    private List<String> classIds;
//    private List<String> suitesIds;


    private ClassInfoBean mClassInfoBean;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_class_info;
    }

    @Override
    protected void initView() {
        height = DisplayUtil.dp2px(300);

        groupInfoBean = getIntent().getParcelableExtra("groupInfoBean");
        viewPager.setUserInputEnabled(false); //true:滑动，false：禁止滑动
        classInfoFragment = new ClassInfoFragment();
        directoryFragment = new DirectoryFragment();
        commentFragment = new CommentFragment();
        fragments = new Fragment[]{classInfoFragment, directoryFragment, commentFragment};


        classInfoModel = new ViewModelProvider(this).get(ClassInfoModel.class);//初始化model

        classInfoModel.getClassInfoBeanLiveData().observe(this, new Observer<ClassInfoBean>() {
            @Override
            public void onChanged(ClassInfoBean classInfoBean) {
                mClassInfoBean = classInfoBean;
                //更新界面数据
                if (classInfoBean.getHuod_price_info() == null) {
                    tvPrice.setVisibility(View.GONE);
                    tvNowPrice.setText(classInfoBean.getOriginal_price_info());
                } else {
                    tvNowPrice.setText(classInfoBean.getHuod_price_info());
                    tvPrice.setText("原价：" + classInfoBean.getOriginal_price_info());
                    tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                }

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
        //接收来自与ClassInfoFragment的消息
//        classInfoModel.getPrices().observe(this, new Observer<String[]>() {
//            @Override
//            public void onChanged(String[] strings) {
//                //如果没有活动价格，隐藏原价
//                if (strings[0] == null) {
//                    tvPrice.setVisibility(View.GONE);
//                    tvNowPrice.setText(strings[1]);
//                } else {
//                    tvNowPrice.setText(strings[0]);
//                    originPrice = strings[1];
//                    tvPrice.setText("原价：" + originPrice);
//                    tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//                }
//                material = strings[2];
//
//            }
//        });
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
        classInfoModel.getClassGenerateOrderBeanLiveData().observe(this, new Observer<ClassGenerateOrderBean>() {
            @Override
            public void onChanged(ClassGenerateOrderBean classGenerateOrderBean) {
                dismissLoading();
                startActivity(new Intent(ClassInfoActivity.this, OrderConfirmActivity.class).putExtra("classGenerateOrderBean", classGenerateOrderBean));
            }
        });

        classInfoModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);

            }
        });

        ////传递信息到fagemnt
        classInfoModel.setGroupInfo(groupInfoBean);

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

        classInfoTabFragmentAdapter = new ClassInfoTabFragmentAdapter(getSupportFragmentManager(), this.getLifecycle(), fragments);
        viewPager.setAdapter(classInfoTabFragmentAdapter);


        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitle[position]);
            }
        }).attach();

        mShareListener = new CustomShareListener(this);


        mShareAction = new ShareAction(this).setDisplayList(
                SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE,
                SHARE_MEDIA.QQ,
                SHARE_MEDIA.QZONE
        ).setShareboardclickCallback(this);
    }


    @OnClick({R.id.iv_back, R.id.iv_shopping, R.id.iv_share, R.id.iv_black_back, R.id.iv_black_shopping, R.id.iv_black_share, R.id.submit, R.id.tv_kefu})
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
                if (mShareAction != null) {
                    mShareAction.open();
                }
                break;
            case R.id.submit:
                if (mGroupClassBeans.size() != 0) {
                    showPow();
                } else {
                    showLoading("");
                    classInfoModel.groupClass(groupInfoBean.getId(), "");
                }
                break;
            case R.id.tv_kefu:
                startActivity(new Intent(this, KeFuActivity.class));

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
                    classInfoModel.classGenerateOrder("1", groupInfoBean.getId(), "0", "0", subjectPopup.getClassIds(), subjectPopup.getSuitesIds(), "", "");

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


    @Override
    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
        UMWeb web = new UMWeb("http://mobile.umeng.com/social");
        web.setTitle("来自分享面板标题");
        web.setDescription("来自分享面板内容");
        web.setThumb(new UMImage(this, com.jianpei.umeng.R.drawable.ic_launcher));
        new ShareAction(this).withMedia(web)
                .setPlatform(share_media)
                .setCallback(mShareListener)
                .share();
    }

    private class CustomShareListener implements UMShareListener {

        private WeakReference<ShareActivity> mActivity;

        private CustomShareListener(ClassInfoActivity activity) {
            mActivity = new WeakReference(activity);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }

        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {


            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                    && platform != SHARE_MEDIA.EMAIL
                    && platform != SHARE_MEDIA.FLICKR
                    && platform != SHARE_MEDIA.FOURSQUARE
                    && platform != SHARE_MEDIA.TUMBLR
                    && platform != SHARE_MEDIA.POCKET
                    && platform != SHARE_MEDIA.PINTEREST

                    && platform != SHARE_MEDIA.INSTAGRAM
                    && platform != SHARE_MEDIA.GOOGLEPLUS
                    && platform != SHARE_MEDIA.YNOTE
                    && platform != SHARE_MEDIA.EVERNOTE) {
                Toast.makeText(mActivity.get(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
