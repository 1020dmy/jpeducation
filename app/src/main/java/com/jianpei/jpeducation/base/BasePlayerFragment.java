package com.jianpei.jpeducation.base;

import android.content.res.Configuration;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.aliyun.player.source.VidAuth;
import com.aliyun.utils.VcPlayerLog;
import com.aliyun.vodplayerview.listener.OnAutoPlayListener;
import com.aliyun.vodplayerview.utils.ScreenUtils;
import com.aliyun.vodplayerview.view.choice.AlivcShowMoreDialog;
import com.aliyun.vodplayerview.view.control.ControlView;
import com.aliyun.vodplayerview.view.more.AliyunShowMoreValue;
import com.aliyun.vodplayerview.view.more.ShowMoreView;
import com.aliyun.vodplayerview.view.more.SpeedValue;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.classinfo.VideoUrlBean;
import com.jianpei.jpeducation.utils.DisplayUtil;
import com.jianpei.jpeducation.utils.L;

import butterknife.BindView;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/24
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public abstract class BasePlayerFragment extends BaseFragment {
    @BindView(R.id.aliyunPlayerView)
    protected AliyunVodPlayerView aliyunPlayerView;

    AlivcShowMoreDialog showMoreDialog;


    //初始化阿里云播放器
    protected void initAliyunPlayerView() {

//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.height = DisplayUtil.screenWidthPx / 16 * 9;
//        L.e("width:"+DisplayUtil.screenWidthPx +",height:"+DisplayUtil.screenWidthPx / 16 * 9);
//        aliyunPlayerView.setLayoutParams(layoutParams);

        aliyunPlayerView.setTitleBarCanShow(false);
        aliyunPlayerView.setControlBarCanShow(false);
        //保持屏幕敞亮
        aliyunPlayerView.setKeepScreenOn(false);
        aliyunPlayerView.setTheme(AliyunVodPlayerView.Theme.Blue);
        aliyunPlayerView.setCirclePlay(false);//是否循环播放
        aliyunPlayerView.setAutoPlay(false);//是否自动播放


        aliyunPlayerView.setOnShowMoreClickListener(new ControlView.OnShowMoreClickListener() {
            @Override
            public void showMore() {
                showMorea();
            }
        });

    }

    //播放视频
    protected void playVideo(VideoUrlBean videoUrlBean) {
        VidAuth vidAuth = new VidAuth();
        vidAuth.setPlayAuth(videoUrlBean.getAuth());
        vidAuth.setVid(videoUrlBean.getVid());
        vidAuth.setRegion("cn-shanghai");
        aliyunPlayerView.setAuthInfo(vidAuth);
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePlayerViewMode();
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
    public void onDestroy() {
        super.onDestroy();
        if (aliyunPlayerView != null) {
            aliyunPlayerView.onDestroy();
        }
    }

    public void updatePlayerViewMode() {
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
