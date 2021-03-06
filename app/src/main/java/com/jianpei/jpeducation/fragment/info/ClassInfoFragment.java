package com.jianpei.jpeducation.fragment.info;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.PlayerConfig;
import com.aliyun.player.source.UrlSource;
import com.aliyun.player.source.VidAuth;
import com.aliyun.player.source.VidSts;
import com.aliyun.utils.VcPlayerLog;
import com.aliyun.vodplayerview.constants.PlayParameter;
import com.aliyun.vodplayerview.listener.OnAutoPlayListener;
import com.aliyun.vodplayerview.utils.FixedToastUtils;
import com.aliyun.vodplayerview.utils.NetWatchdog;
import com.aliyun.vodplayerview.utils.ScreenUtils;
import com.aliyun.vodplayerview.utils.download.AliyunDownloadManager;
import com.aliyun.vodplayerview.view.choice.AlivcShowMoreDialog;
import com.aliyun.vodplayerview.view.control.ControlView;
import com.aliyun.vodplayerview.view.more.AliyunShowMoreValue;
import com.aliyun.vodplayerview.view.more.ShowMoreView;
import com.aliyun.vodplayerview.view.more.SpeedValue;
import com.aliyun.vodplayerview.widget.AliyunScreenMode;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.login.LoginActivity;
import com.jianpei.jpeducation.activitys.player.TryPlayerActivity;
import com.jianpei.jpeducation.adapter.ItemOffsetDecoration;
import com.jianpei.jpeducation.adapter.classinfo.ExplanationAdapter;
import com.jianpei.jpeducation.adapter.classinfo.TeacherAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.base.BasePlayerFragment;
import com.jianpei.jpeducation.base.MyApplication;
import com.jianpei.jpeducation.bean.classinfo.ClassInfoBean;
import com.jianpei.jpeducation.bean.classinfo.GroupCouponBean;
import com.jianpei.jpeducation.bean.classinfo.TeacherBean;
import com.jianpei.jpeducation.bean.classinfo.VideoUrlBean;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.utils.pop.CouponPopup;
import com.jianpei.jpeducation.viewmodel.ClassInfoFModel;
import com.jianpei.jpeducation.viewmodel.ClassInfoModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassInfoFragment extends BasePlayerFragment {


    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.rv_explanation)
    RecyclerView rvExplanation;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_now_price)
    TextView tvNowPrice;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_handsel)
    TextView tvHandsel;
    @BindView(R.id.ll_handsel)
    LinearLayout llHandsel;
    @BindView(R.id.tv_teachers)
    TextView tvTeachers;
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.tv_aging)
    TextView tvAging;
    @BindView(R.id.tv_nums)
    TextView tvNums;
    @BindView(R.id.tv_server)
    TextView tvServer;
    @BindView(R.id.tv_guarantee)
    TextView tvGuarantee;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.ll_server)
    LinearLayout llServer;
    @BindView(R.id.ll_classInfo)
    LinearLayout llClassInfo;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.tv_tryListener)
    TextView tvTryListener;

    private TeacherAdapter teacherAdapter;
    private List<TeacherBean> teacherBeans;

    private ClassInfoModel classInfoModel;

    private ClassInfoFModel classInfoFModel;

    private CouponPopup couponPopup;

    private List<GroupCouponBean> mGroupCouponBeans;
    ///
    private String originalPrice;
    private String material;

    private String groupId;//
    private String catId;

    public ClassInfoFragment(String groupId, String catId) {
        this.groupId = groupId;
        this.catId = catId;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_class_info;
    }

    @Override
    protected void initView(View view) {
        L.e("=========initView========");

        if (NetWatchdog.is4GConnected(MyApplication.getInstance())) {
            tvTryListener.setVisibility(View.GONE);
        }

        classInfoModel = new ViewModelProvider(getActivity()).get(ClassInfoModel.class);
        classInfoFModel = new ViewModelProvider(getActivity()).get(ClassInfoFModel.class);
        //首页数据结果
        classInfoFModel.getClassInfoBean().observe(getActivity(), new Observer<ClassInfoBean>() {
            @Override
            public void onChanged(ClassInfoBean classInfoBean) {
                dismissLoading();
                classInfoModel.getClassInfoBeanLiveData().setValue(classInfoBean);//数据回传到Activity
                classInfoFModel.videoUrl(classInfoBean.getVideo_id(), "", classInfoBean.getId());//获取试看视频
                setDatatoView(classInfoBean);//设置页面数据
            }
        });
        classInfoFModel.getErrData().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                if (!TextUtils.isEmpty(o)) {
                    shortToast(o);

                }

            }
        });
        //获取视频播放结果
        classInfoFModel.getVideoUrlBeansLiveData().observe(getActivity(), new Observer<VideoUrlBean>() {
            @Override
            public void onChanged(VideoUrlBean videoUrlBean) {
                //试听课程结果
                aliyunPlayerView.setCoverUri(videoUrlBean.getImg());

                //初始化播放器并播放试听课程
                initAliyunPlayerView();

                playVideo(videoUrlBean);
            }
        });

        mGroupCouponBeans = new ArrayList<>();
        //获取优惠券结果
        classInfoFModel.getGroupCouponBeansLiveData().observe(getActivity(), new Observer<List<GroupCouponBean>>() {
            @Override
            public void onChanged(List<GroupCouponBean> groupCouponBeans) {
                mGroupCouponBeans.addAll(groupCouponBeans);
                if (couponPopup == null) {
                    couponPopup = new CouponPopup(getActivity(), mGroupCouponBeans);
                    couponPopup.setMyCouponReceiveListener(new CouponPopup.MyCouponReceiveListener() {
                        @Override
                        public void onClickCouponReceive(String couponId) {
                            if (TextUtils.isEmpty(SpUtils.getValue(SpUtils.ID))) {
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                return;
                            }
                            showLoading("");
                            classInfoFModel.couponReceive(couponId, "");
                        }
                    });
                }
                couponPopup.showPop();
            }
        });
        //滚动监听
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (nestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            classInfoModel.tabViewStatusChange(scrollY);

        });

        //领取优惠券结果
        classInfoFModel.getCouponReceiveLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                if (couponPopup != null)
                    couponPopup.dismiss();
                shortToast(s);
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        rvExplanation.setLayoutManager(linearLayoutManager);
        rvExplanation.setHasFixedSize(true);
        rvExplanation.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.addItemDecoration(new ItemOffsetDecoration(10));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData(Context mContext) {

        L.e("=========initData========");

        teacherBeans = new ArrayList<>();
        teacherAdapter = new TeacherAdapter(teacherBeans, getActivity());

        recyclerView.setAdapter(teacherAdapter);


        //
        showLoading("");
        classInfoFModel.groupInfo(groupId, "");
//        获取优惠券信息
//        classInfoFModel.groupCoupon(catId, groupId);


    }

    protected void setDatatoView(ClassInfoBean classInfoBean) {
        if (classInfoBean == null)
            return;
        //如果没有活动价格，隐藏原价
        if (classInfoBean.getHuod_price_info() == null) {
            tvPrice.setVisibility(View.INVISIBLE);
            tvNowPrice.setText(classInfoBean.getOriginal_price_info());
        } else {
            tvNowPrice.setText(classInfoBean.getHuod_price_info());
            originalPrice = classInfoBean.getOriginal_price_info();
            tvPrice.setText("原价：" + originalPrice);
            tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        //更新ClassInfoActivity价格
//        classInfoModel.setPrices(new String[]{classInfoBean.getHuod_price_info(), classInfoBean.getOriginal_price_info(), classInfoBean.getMaterial_des()});
        tvTitle.setText(classInfoBean.getTitle());
        //班级描述
        if (classInfoBean.getSub_title() == null) {
            tvDescription.setVisibility(View.GONE);
        } else {
            tvDescription.setText(classInfoBean.getSub_title());
        }
        //是否有赠送资料
        if (classInfoBean.getMaterial_des() == null) {
            llHandsel.setVisibility(View.GONE);
        } else {
            material = classInfoBean.getMaterial_des();
            tvHandsel.setText(material);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String teacherNum : classInfoBean.getTeacher_names()) {
            stringBuilder.append(teacherNum + " ");
        }
        String teachers = stringBuilder.toString();
        stringBuilder.replace(0, stringBuilder.length(), "");
        stringBuilder.reverse();
        stringBuilder = null;
        tvTeachers.setText(teachers);

//        tvClass.setText(classInfoBean.getVideo_time_str());
        tvAging.setText(classInfoBean.getYear_num() + "年");
        tvNums.setText(classInfoBean.getBuy_num() + "人已报名");
        //服务保障
        tvServer.setText(classInfoBean.getOur_service());
        tvGuarantee.setText(classInfoBean.getOur_guarantee());
        //班级说明
        if (classInfoBean.getContent() != null) {
            ExplanationAdapter explanationAdapter = new ExplanationAdapter(classInfoBean.getContent(), getActivity());
            rvExplanation.setAdapter(explanationAdapter);
        }
//        if (classInfoBean.getContent() != null) {
//            Spanned spanned = Html.fromHtml(classInfoBean.getContent());
//            tvExplanation.setText(Html.fromHtml(spanned.toString()));
//        }
        //是否有优惠券可领取1是，0否
        if (classInfoBean.getIs_coupon() == 0) {
            tvCoupon.setVisibility(View.GONE);
        } else {
            tvCoupon.setVisibility(View.VISIBLE);

        }
        teacherBeans.addAll(classInfoBean.getTeachers());
        if (teacherBeans.size() == 0) {
            recyclerView.setVisibility(View.GONE);
        } else {
            teacherAdapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.tv_coupon, R.id.tv_tryListener})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_coupon:
                if (mGroupCouponBeans.size() != 0) {
                    if (couponPopup == null) {
                        couponPopup = new CouponPopup(getActivity(), mGroupCouponBeans);
                        couponPopup.setMyCouponReceiveListener(new CouponPopup.MyCouponReceiveListener() {
                            @Override
                            public void onClickCouponReceive(String couponId) {
                                if (TextUtils.isEmpty(SpUtils.getValue(SpUtils.ID))) {
                                    startActivity(new Intent(getActivity(), LoginActivity.class));
                                    return;
                                }
                                showLoading("");
                                classInfoFModel.couponReceive(couponId, "");
                            }
                        });
                    }
                    couponPopup.showPop();
                } else {
                    //获取优惠券信息
                    classInfoFModel.groupCoupon(catId, groupId);
                }

                break;
            case R.id.tv_tryListener:
                aliyunPlayerView.start();
                tvTryListener.setVisibility(View.GONE);
                aliyunPlayerView.setControlBarCanShow(true);
                break;
        }


    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int type = this.getResources().getConfiguration().orientation;
        if (type == Configuration.ORIENTATION_LANDSCAPE) {
            llClassInfo.setVisibility(View.GONE);
            llContent.setVisibility(View.GONE);
            llServer.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            aliyunPlayerView.setTitleBarCanShow(true);

            //切换到了横屏
        } else if (type == Configuration.ORIENTATION_PORTRAIT) {
            //切换到了竖屏
            llClassInfo.setVisibility(View.VISIBLE);
            llContent.setVisibility(View.VISIBLE);
            llServer.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            aliyunPlayerView.setTitleBarCanShow(false);


        }
        updatePlayerViewMode();
    }


}
