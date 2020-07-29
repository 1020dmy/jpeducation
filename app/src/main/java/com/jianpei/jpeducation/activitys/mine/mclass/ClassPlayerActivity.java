package com.jianpei.jpeducation.activitys.mine.mclass;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.aliyun.player.bean.ErrorCode;
import com.aliyun.player.source.VidAuth;
import com.aliyun.private_service.PrivateService;
import com.aliyun.utils.VcPlayerLog;
import com.aliyun.vodplayerview.activity.AliyunPlayerSkinActivity;
import com.aliyun.vodplayerview.utils.Common;
import com.aliyun.vodplayerview.utils.ScreenUtils;
import com.aliyun.vodplayerview.utils.download.AliyunDownloadInfoListener;
import com.aliyun.vodplayerview.utils.download.AliyunDownloadManager;
import com.aliyun.vodplayerview.utils.download.AliyunDownloadMediaInfo;
import com.aliyun.vodplayerview.view.choice.AlivcShowMoreDialog;
import com.aliyun.vodplayerview.view.control.ControlView;
import com.aliyun.vodplayerview.view.download.DownloadDataProvider;
import com.aliyun.vodplayerview.view.more.AliyunShowMoreValue;
import com.aliyun.vodplayerview.view.more.ShowMoreView;
import com.aliyun.vodplayerview.view.more.SpeedValue;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.classinfo.ClassInfoActivity;
import com.jianpei.jpeducation.adapter.TabFragmentAdapter;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.bean.classinfo.VideoUrlBean;
import com.jianpei.jpeducation.bean.mclass.MyClassBean;
import com.jianpei.jpeducation.fragment.mine.mclass.PlayerCommentFragment;
import com.jianpei.jpeducation.fragment.mine.mclass.PlayerListFragment;
import com.jianpei.jpeducation.utils.pop.DownloadClassPopup;
import com.jianpei.jpeducation.viewmodel.ClassPlayerModel;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    AliyunVodPlayerView aliyunPlayerView;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager2 viewPage;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    private MyClassBean myClassBean;

    private String[] tabTitle = {"目录", "评价"};

    private ClassPlayerModel classPlayerModel;

    AlivcShowMoreDialog showMoreDialog;

//    private DownloadClassPopup downloadClassPopup;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_class_player;
    }

    @Override
    protected void initView() {
        setTitleViewPadding2(tvStatus);
        initShare();//初始化分享

        myClassBean = getIntent().getParcelableExtra("myClassBean");
        Fragment[] fragments = {new PlayerListFragment(myClassBean.getCid(), myClassBean.getId()), new PlayerCommentFragment(myClassBean.getCid())};
        viewPage.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(), getLifecycle(), fragments));
        new TabLayoutMediator(tabLayout, viewPage, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitle[position]);
            }
        }).attach();
        //初始化播放器并播放试听课程
        initAliyunPlayerView();
    }

    @Override
    protected void initData() {
        classPlayerModel = new ViewModelProvider(this).get(ClassPlayerModel.class);

        classPlayerModel.getPlayUrlBean().observe(this, new Observer<VideoUrlBean>() {
            @Override
            public void onChanged(VideoUrlBean videoUrlBean) {
                dismissLoading();
                if (videoUrlBean.getType() == 0) {
                    if (aliyunPlayerView != null)
                        aliyunPlayerView.onStop();
                    playVideo(videoUrlBean);
                }
            }
        });
    }

    //播放视频
    protected void playVideo(VideoUrlBean videoUrlBean) {
        if (videoUrlBean == null) {
            shortToast("视频加载失败");
            return;
        }
        aliyunPlayerView.setCoverUri(videoUrlBean.getImg());
        VidAuth vidAuth = new VidAuth();
        vidAuth.setPlayAuth(videoUrlBean.getAuth());
        vidAuth.setVid(videoUrlBean.getVid());
        vidAuth.setRegion("cn-shanghai");
        aliyunPlayerView.setAuthInfo(vidAuth);

    }

    @OnClick({R.id.iv_back, R.id.iv_download, R.id.iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_download:
                startActivity(new Intent(this, ClassDownloadActivity.class));
                break;
            case R.id.iv_share:
                if (mShareAction != null) {
                    mShareAction.open();
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (aliyunPlayerView != null) {
            aliyunPlayerView.setAutoPlay(false);
            aliyunPlayerView.onStop();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (aliyunPlayerView != null) {
            aliyunPlayerView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (aliyunPlayerView != null)
            aliyunPlayerView.onDestroy();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int type = this.getResources().getConfiguration().orientation;
        if (type == Configuration.ORIENTATION_LANDSCAPE) {
            //切换到了横屏
            aliyunPlayerView.setTitleBarCanShow(true);
            rlTitle.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            viewPage.setVisibility(View.GONE);
        } else if (type == Configuration.ORIENTATION_PORTRAIT) {
            //切换到了竖屏
            aliyunPlayerView.setTitleBarCanShow(false);
            rlTitle.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            viewPage.setVisibility(View.VISIBLE);
        }
        updatePlayerViewMode();

    }

    //初始化阿里云播放器
    protected void initAliyunPlayerView() {
        aliyunPlayerView.setTitleBarCanShow(false);
        aliyunPlayerView.setControlBarCanShow(true);
        //保持屏幕敞亮
        aliyunPlayerView.setKeepScreenOn(false);
        aliyunPlayerView.setTheme(AliyunVodPlayerView.Theme.Blue);
        aliyunPlayerView.setCirclePlay(false);//是否循环播放
        aliyunPlayerView.setAutoPlay(true);//是否自动播放

        aliyunPlayerView.setOnShowMoreClickListener(new ControlView.OnShowMoreClickListener() {
            @Override
            public void showMore() {
                showMorea();
            }
        });

    }

    private void showMorea() {
        showMoreDialog = new AlivcShowMoreDialog(this);
        AliyunShowMoreValue moreValue = new AliyunShowMoreValue();
        moreValue.setSpeed(aliyunPlayerView.getCurrentSpeed());
        moreValue.setVolume((int) aliyunPlayerView.getCurrentVolume());

        ShowMoreView showMoreView = new ShowMoreView(this, moreValue);
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
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = brightness / 255.0f;
        getWindow().setAttributes(lp);
    }

    protected void updatePlayerViewMode() {
        if (aliyunPlayerView != null) {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                //转为竖屏了。
                //显示状态栏
                //                if (!isStrangePhone()) {
                //                    getSupportActionBar().show();
                //                }

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                aliyunPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                //设置view的布局，宽高之类
                RelativeLayout.LayoutParams aliVcVideoViewLayoutParams = (RelativeLayout.LayoutParams) aliyunPlayerView
                        .getLayoutParams();
                aliVcVideoViewLayoutParams.height = (int) (ScreenUtils.getWidth(this) * 9.0f / 16);
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                //转到横屏了。
                //隐藏状态栏
                if (!isStrangePhone()) {

                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
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


    /////下载相关


}
