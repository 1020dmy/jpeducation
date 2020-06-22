package com.jianpei.jpeducation.fragment.info;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.os.Build;
import android.os.Environment;
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
import com.aliyun.vodplayerview.utils.FixedToastUtils;
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
import com.jianpei.jpeducation.activitys.player.TryPlayerActivity;
import com.jianpei.jpeducation.adapter.ItemOffsetDecoration;
import com.jianpei.jpeducation.adapter.classinfo.ExplanationAdapter;
import com.jianpei.jpeducation.adapter.classinfo.TeacherAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.classinfo.ClassInfoBean;
import com.jianpei.jpeducation.bean.classinfo.GroupCouponBean;
import com.jianpei.jpeducation.bean.classinfo.TeacherBean;
import com.jianpei.jpeducation.bean.classinfo.VideoUrlBean;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;
import com.jianpei.jpeducation.utils.L;
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
public class ClassInfoFragment extends BaseFragment {


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
    @BindView(R.id.aliyunPlayerView)
    AliyunVodPlayerView aliyunPlayerView;
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


    @Override
    protected int initLayout() {
        return R.layout.fragment_class_info;
    }

    @Override
    protected void initView(View view) {
        classInfoModel = new ViewModelProvider(getActivity()).get(ClassInfoModel.class);
        classInfoFModel = new ViewModelProvider(getActivity()).get(ClassInfoFModel.class);
        classInfoFModel.getClassInfoBean().observe(getActivity(), new Observer<ClassInfoBean>() {
            @Override
            public void onChanged(ClassInfoBean classInfoBean) {
                dismissLoading();
                classInfoFModel.videoUrl(classInfoBean.getVideo_id(), "");//获取试看视频
                setDatatoView(classInfoBean);//设置页面数据
            }
        });
        classInfoFModel.getErrData().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);

            }
        });
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
        classInfoFModel.getGroupCouponBeansLiveData().observe(getActivity(), new Observer<List<GroupCouponBean>>() {
            @Override
            public void onChanged(List<GroupCouponBean> groupCouponBeans) {
                mGroupCouponBeans.addAll(groupCouponBeans);
            }
        });

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (nestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            classInfoModel.tabViewStatusChange(scrollY);

        });
        classInfoModel.getGroupInfoBeanMutableLiveData().observe(getActivity(), new Observer<GroupInfoBean>() {
            @Override
            public void onChanged(GroupInfoBean groupInfoBean) {
                showLoading("");
                classInfoFModel.groupInfo(groupInfoBean.getId(), "");
                classInfoFModel.groupCoupon(groupInfoBean.getCat_id());
            }
        });
        classInfoFModel.getCouponReceiveLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                shortToast(s);
            }
        });

        rvExplanation.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.addItemDecoration(new ItemOffsetDecoration(10));
    }

    @Override
    protected void initData(Context mContext) {

        teacherBeans = new ArrayList<>();
        teacherAdapter = new TeacherAdapter(teacherBeans, getActivity());

        recyclerView.setAdapter(teacherAdapter);


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
        classInfoModel.setPrices(new String[]{classInfoBean.getHuod_price_info(), classInfoBean.getOriginal_price_info(),classInfoBean.getMaterial_des()});
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

        tvClass.setText(classInfoBean.getVideo_time_str());
        tvAging.setText(classInfoBean.getEnd_time_str());
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
        if ("0".equals(classInfoBean.getIs_coupon())) {
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
                if (couponPopup == null) {
                    couponPopup = new CouponPopup(getActivity(), mGroupCouponBeans);
                    couponPopup.setMyCouponReceiveListener(new CouponPopup.MyCouponReceiveListener() {
                        @Override
                        public void onClickCouponReceive(String couponId) {
                            showLoading("");
                            classInfoFModel.couponReceive(couponId, "");
                        }
                    });
                }
                couponPopup.showPop();
                break;
            case R.id.tv_tryListener:
                aliyunPlayerView.start();
                tvTryListener.setVisibility(View.GONE);
                aliyunPlayerView.setControlBarCanShow(true);
                break;
        }


    }


    //初始化阿里云播放器
    protected void initAliyunPlayerView() {
//        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_save_cache";
//        File file = new File(sdDir);
//        if (!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        aliyunPlayerView.setTitleBarCanShow(false);
        aliyunPlayerView.setControlBarCanShow(false);
        //保持屏幕敞亮
        aliyunPlayerView.setKeepScreenOn(false);
//        PlayParameter.PLAY_PARAM_URL = palyUrl;
//        aliyunPlayerView.setPlayingCache(true, sdDir, 60 * 60 /*时长, s */, 300 /*大小，MB*/);
        aliyunPlayerView.setTheme(AliyunVodPlayerView.Theme.Blue);
        aliyunPlayerView.setCirclePlay(false);//是否循环播放
        aliyunPlayerView.setAutoPlay(false);//是否自动播放
        //设置画面缩放模式：宽高比填充，宽高比适应，拉伸填充
        //aliyunPlayerView.setVideoScalingMode(IPlayer.ScaleMode.SCALE_ASPECT_FILL);

        aliyunPlayerView.setOnShowMoreClickListener(new ControlView.OnShowMoreClickListener() {
            @Override
            public void showMore() {
                showMorea();
            }
        });

    }

    //播放视频
    protected void playVideo(VideoUrlBean videoUrlBean) {
        String videoPath = PlayParameter.PLAY_PARAM_URL;
        // String videoPath = "http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4";


        //下面的这 2 种视频路径，到4.7.0版本，不支持播放
        //String videoPath1 = "android.resource://" + getPackageName() + "/" + R.raw.video_1;
        // String videoPath2 = "file:///android_asset/video_1.mp4";
//        UrlSource urlSource = new UrlSource();
//        urlSource.setUri(videoPath);


//        PlayerConfig playerConfig = aliyunPlayerView.getPlayerConfig();
//        //默认是5000
//        int maxDelayTime = 5000;
//        if (PlayParameter.PLAY_PARAM_URL.startsWith("artp")) {
//            //如果url的开头是artp，将直播延迟设置成100，
//            maxDelayTime = 100;
//        }
//        playerConfig.mMaxDelayTime = maxDelayTime;

        VidAuth vidAuth = new VidAuth();
        vidAuth.setPlayAuth(videoUrlBean.getAuth());
        vidAuth.setVid(videoUrlBean.getVid());
//        vidAuth.setRegion("https://vod.cn-shanghai.aliyuncs.com");
        vidAuth.setRegion("cn-shanghai");


//        aliyunPlayerView.setPlayerConfig(playerConfig);
        aliyunPlayerView.setAuthInfo(vidAuth);
//        aliyunPlayerView.setLocalSource(urlSource);

    }

    @Override
    public void onResume() {
        super.onResume();
//        if (aliyunPlayerView != null) {
//            aliyunPlayerView.setAutoPlay(false);
//            aliyunPlayerView.onResume();
//        }
        updatePlayerViewMode();
    }

    @Override
    public void onStop() {
        super.onStop();
        L.e("=====ClassInfoFragment:onStop");

        if (aliyunPlayerView != null) {
            aliyunPlayerView.setAutoPlay(false);
            aliyunPlayerView.onStop();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int type = this.getResources().getConfiguration().orientation;
        if (type == Configuration.ORIENTATION_LANDSCAPE) {
            L.e("==========", "切换到了横屏");
            llClassInfo.setVisibility(View.GONE);
            llContent.setVisibility(View.GONE);
            llServer.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            aliyunPlayerView.setTitleBarCanShow(true);

            //切换到了横屏
        } else if (type == Configuration.ORIENTATION_PORTRAIT) {
            L.e("==========", "切换到了竖屏");
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

    private void updatePlayerViewMode() {
        if (aliyunPlayerView != null) {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                //转为竖屏了。
                //显示状态栏
                //                if (!isStrangePhone()) {
                //                    getSupportActionBar().show();
                //                }

                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                aliyunPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                //设置view的布局，宽高之类
                RelativeLayout.LayoutParams aliVcVideoViewLayoutParams = (RelativeLayout.LayoutParams) aliyunPlayerView
                        .getLayoutParams();
                aliVcVideoViewLayoutParams.height = (int) (ScreenUtils.getWidth(getActivity()) * 9.0f / 16);
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                //转到横屏了。
                //隐藏状态栏
                if (!isStrangePhone()) {

                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    aliyunPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
                //设置view的布局，宽高
                RelativeLayout.LayoutParams aliVcVideoViewLayoutParams = (RelativeLayout.LayoutParams) aliyunPlayerView
                        .getLayoutParams();
                aliVcVideoViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            }
        }
    }

    protected boolean isStrangePhone() {
        boolean strangePhone = "mx5".equalsIgnoreCase(Build.DEVICE)
                || "Redmi Note2".equalsIgnoreCase(Build.DEVICE)
                || "Z00A_1".equalsIgnoreCase(Build.DEVICE)
                || "hwH60-L02".equalsIgnoreCase(Build.DEVICE)
                || "hermes".equalsIgnoreCase(Build.DEVICE)
                || ("V4".equalsIgnoreCase(Build.DEVICE) && "Meitu".equalsIgnoreCase(Build.MANUFACTURER))
                || ("m1metal".equalsIgnoreCase(Build.DEVICE) && "Meizu".equalsIgnoreCase(Build.MANUFACTURER));

        VcPlayerLog.e("lfj1115 ", " Build.Device = " + Build.DEVICE + " , isStrange = " + strangePhone);
        return strangePhone;
    }

    @Override
    public void onPause() {
        super.onPause();
        L.e("=====ClassInfoFragment:onPause");
        if (aliyunPlayerView != null) {
            aliyunPlayerView.pause();
        }
    }

    AlivcShowMoreDialog showMoreDialog;

    private void showMorea() {
        showMoreDialog = new AlivcShowMoreDialog(getActivity());
        AliyunShowMoreValue moreValue = new AliyunShowMoreValue();
        moreValue.setSpeed(aliyunPlayerView.getCurrentSpeed());
        moreValue.setVolume((int) aliyunPlayerView.getCurrentVolume());

        ShowMoreView showMoreView = new ShowMoreView(getActivity(), moreValue);
        showMoreDialog.setContentView(showMoreView);
        showMoreDialog.show();

        showMoreView.setOnSpeedCheckedChangedListener(new ShowMoreView.OnSpeedCheckedChangedListener() {
            @Override
            public void onSpeedChanged(RadioGroup group, int checkedId) {
                // 点击速度切换
                if (checkedId == com.aliyun.vodplayer.R.id.rb_speed_normal) {
                    aliyunPlayerView.changeSpeed(SpeedValue.One);
                } else if (checkedId == com.aliyun.vodplayer.R.id.rb_speed_onequartern) {
                    aliyunPlayerView.changeSpeed(SpeedValue.OneQuartern);
                } else if (checkedId == com.aliyun.vodplayer.R.id.rb_speed_onehalf) {
                    aliyunPlayerView.changeSpeed(SpeedValue.OneHalf);
                } else if (checkedId == com.aliyun.vodplayer.R.id.rb_speed_twice) {
                    aliyunPlayerView.changeSpeed(SpeedValue.Twice);
                }

            }
        });

        /**
         * 初始化亮度
         */
        if (aliyunPlayerView != null) {
            showMoreView.setBrightness(aliyunPlayerView.getScreenBrightness());
        }
        // 亮度seek
        showMoreView.setOnLightSeekChangeListener(new ShowMoreView.OnLightSeekChangeListener() {
            @Override
            public void onStart(SeekBar seekBar) {

            }

            @Override
            public void onProgress(SeekBar seekBar, int progress, boolean fromUser) {
                setWindowBrightness(progress);
                if (aliyunPlayerView != null) {
                    aliyunPlayerView.setScreenBrightness(progress);
                }
            }

            @Override
            public void onStop(SeekBar seekBar) {

            }
        });

        /**
         * 初始化音量
         */
        if (aliyunPlayerView != null) {
            showMoreView.setVoiceVolume(aliyunPlayerView.getCurrentVolume());
        }
        showMoreView.setOnVoiceSeekChangeListener(new ShowMoreView.OnVoiceSeekChangeListener() {
            @Override
            public void onStart(SeekBar seekBar) {

            }

            @Override
            public void onProgress(SeekBar seekBar, int progress, boolean fromUser) {
                aliyunPlayerView.setCurrentVolume(progress / 100.00f);
            }

            @Override
            public void onStop(SeekBar seekBar) {

            }
        });

    }

    private void setWindowBrightness(int brightness) {
        Window window = getActivity().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness / 255.0f;
        window.setAttributes(lp);
    }
}
