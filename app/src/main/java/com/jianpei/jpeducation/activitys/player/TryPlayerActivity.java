package com.jianpei.jpeducation.activitys.player;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.aliyun.player.VidPlayerConfigGen;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.PlayerConfig;
import com.aliyun.player.source.UrlSource;
import com.aliyun.player.source.VidAuth;
import com.aliyun.player.source.VidSts;
import com.aliyun.utils.VcPlayerLog;
import com.aliyun.vodplayerview.activity.AliyunPlayerSkinActivity;
import com.aliyun.vodplayerview.constants.PlayParameter;
import com.aliyun.vodplayerview.listener.OnChangeQualityListener;
import com.aliyun.vodplayerview.utils.FixedToastUtils;
import com.aliyun.vodplayerview.utils.ScreenUtils;

import com.aliyun.vodplayerview.utils.download.AliyunDownloadManager;
import com.aliyun.vodplayerview.view.choice.AlivcShowMoreDialog;
import com.aliyun.vodplayerview.view.control.ControlView;
import com.aliyun.vodplayerview.view.gesturedialog.BrightnessDialog;
import com.aliyun.vodplayerview.view.more.AliyunShowMoreValue;
import com.aliyun.vodplayerview.view.more.ShowMoreView;
import com.aliyun.vodplayerview.view.more.SpeedValue;
import com.aliyun.vodplayerview.widget.AliyunScreenMode;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.bean.classinfo.DirectorySectionBean;
import com.jianpei.jpeducation.bean.classinfo.VideoUrlBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.viewmodel.ClassInfoFModel;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.umeng.commonsdk.statistics.UMServerURL.DEFAULT_URL;

public class TryPlayerActivity extends AppCompatActivity {

    @BindView(R.id.aliyunPlayerView)
    AliyunVodPlayerView aliyunPlayerView;

//    private DirectorySectionBean directorySectionBean;

    private String voidId;

    private ClassInfoFModel classInfoFModel;
    private String localUrl;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_player);
        ButterKnife.bind(this);
        classInfoFModel = new ViewModelProvider(this).get(ClassInfoFModel.class);
        classInfoFModel.getVideoUrlBeansLiveData().observe(this, new Observer<VideoUrlBean>() {
            @Override
            public void onChanged(VideoUrlBean videoUrlBean) {

                initAliyunPlayerView();
                playVideo(videoUrlBean);


            }
        });
        localUrl = getIntent().getStringExtra("localUrl");
        title = getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(localUrl)) {
            initAliyunPlayerView();
            changePlayLocalSource(localUrl, title);
        } else {
            voidId = getIntent().getStringExtra("viodBeanId");
            classInfoFModel.videoUrl(voidId, "", "0");
        }


    }

    private void initAliyunPlayerView() {

        aliyunPlayerView.changeScreenMode(AliyunScreenMode.Full, false);
        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_save_cache";
        File file = new File(sdDir);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //保持屏幕敞亮
        aliyunPlayerView.setKeepScreenOn(true);
        PlayParameter.PLAY_PARAM_URL = DEFAULT_URL;
        aliyunPlayerView.setPlayingCache(false, sdDir, 60 * 60 /*时长, s */, 300 /*大小，MB*/);
        aliyunPlayerView.setTheme(AliyunVodPlayerView.Theme.Blue);
        aliyunPlayerView.setAutoPlay(true);
        aliyunPlayerView.setOnShowMoreClickListener(new ControlView.OnShowMoreClickListener() {
            @Override
            public void showMore() {
                showMorea();
            }
        });

        //TODO
        aliyunPlayerView.enableNativeLog();
    }

    /**
     * 播放本地资源
     */
    private void changePlayLocalSource(String url, String title) {
        UrlSource urlSource = new UrlSource();
        urlSource.setUri(url);
        urlSource.setTitle(title);
        aliyunPlayerView.setLocalSource(urlSource);
    }


    //播放视频
    protected void playVideo(VideoUrlBean videoUrlBean) {


        PlayerConfig playerConfig = aliyunPlayerView.getPlayerConfig();
        //默认是5000
        int maxDelayTime = 5000;
        if (PlayParameter.PLAY_PARAM_URL.startsWith("artp")) {
            //如果url的开头是artp，将直播延迟设置成100，
            maxDelayTime = 100;
        }
        playerConfig.mMaxDelayTime = maxDelayTime;

        VidAuth vidAuth = new VidAuth();
        vidAuth.setPlayAuth(videoUrlBean.getAuth());
        vidAuth.setVid(videoUrlBean.getVid());
        vidAuth.setRegion("cn-shanghai");

        aliyunPlayerView.setPlayerConfig(playerConfig);
        aliyunPlayerView.setAuthInfo(vidAuth);
//        aliyunPlayerView.setLocalSource(urlSource);

    }

    @Override
    public void onResume() {
        super.onResume();
        updatePlayerViewMode();

        if (aliyunPlayerView != null) {
            aliyunPlayerView.setAutoPlay(true);
            aliyunPlayerView.onResume();
        }
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
    protected void onDestroy() {
        super.onDestroy();
        if (aliyunPlayerView!=null)
            aliyunPlayerView.onDestroy();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        int type = this.getResources().getConfiguration().orientation;
//        if (type == Configuration.ORIENTATION_LANDSCAPE) {
//            Log.e("==========","切换到了横屏");
//
//            //切换到了横屏
//        } else if (type == Configuration.ORIENTATION_PORTRAIT) {
//            Log.e("==========","切换到了竖屏");
//            //切换到了竖屏
//        }
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

                this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                aliyunPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                //设置view的布局，宽高之类
                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams) aliyunPlayerView
                        .getLayoutParams();
                aliVcVideoViewLayoutParams.height = (int) (ScreenUtils.getWidth(this) * 9.0f / 16);
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                //转到横屏了。
                //隐藏状态栏
                if (!isStrangePhone()) {

                    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    aliyunPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
                //设置view的布局，宽高
                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams) aliyunPlayerView
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

    private long oldTime;

//    private static class MyShowMoreClickLisener implements ControlView.OnShowMoreClickListener {
//        WeakReference<TryPlayerActivity> weakReference;
//
//        MyShowMoreClickLisener(TryPlayerActivity activity) {
//            weakReference = new WeakReference<>(activity);
//        }
//
//        @Override
//        public void showMore() {
//            TryPlayerActivity activity = weakReference.get();
//            if (activity != null) {
//                long currentClickTime = System.currentTimeMillis();
//                // 防止快速点击
//                if (currentClickTime - activity.oldTime <= 1000) {
//                    return;
//                }
//                activity.oldTime = currentClickTime;
//                activity.showMore(activity);
//                activity.requestVidSts();
//            }
//
//        }
//    }

    AlivcShowMoreDialog showMoreDialog;
    private long downloadOldTime;
    private AliyunScreenMode mCurrentDownloadScreenMode;

    private boolean showAddDownloadView;
    private AliyunDownloadManager downloadManager;


    private void showMorea() {
        showMoreDialog = new AlivcShowMoreDialog(this);
        AliyunShowMoreValue moreValue = new AliyunShowMoreValue();
        moreValue.setSpeed(aliyunPlayerView.getCurrentSpeed());
        moreValue.setVolume((int) aliyunPlayerView.getCurrentVolume());

        ShowMoreView showMoreView = new ShowMoreView(this, moreValue);
        showMoreDialog.setContentView(showMoreView);
        showMoreDialog.show();
        showMoreView.setOnDownloadButtonClickListener(new ShowMoreView.OnDownloadButtonClickListener() {
            @Override
            public void onDownloadClick() {
                long currentClickTime = System.currentTimeMillis();
                // 防止快速点击
                if (currentClickTime - downloadOldTime <= 1000) {
                    return;
                }
                downloadOldTime = currentClickTime;
                // 点击下载
                showMoreDialog.dismiss();
                if ("url".equals(PlayParameter.PLAY_PARAM_TYPE) || "localSource".equals(PlayParameter.PLAY_PARAM_TYPE)) {
                    FixedToastUtils.show(TryPlayerActivity.this, getResources().getString(com.aliyun.vodplayer.R.string.alivc_video_not_support_download));
                    return;
                }
                mCurrentDownloadScreenMode = AliyunScreenMode.Full;
                showAddDownloadView = true;
                if (aliyunPlayerView != null) {
                    MediaInfo currentMediaInfo = aliyunPlayerView.getCurrentMediaInfo();
                    if (currentMediaInfo != null && currentMediaInfo.getVideoId().equals(PlayParameter.PLAY_PARAM_VID)) {
                        VidSts vidSts = new VidSts();
                        vidSts.setVid(PlayParameter.PLAY_PARAM_VID);
                        vidSts.setRegion(PlayParameter.PLAY_PARAM_REGION);
                        vidSts.setAccessKeyId(PlayParameter.PLAY_PARAM_AK_ID);
                        vidSts.setAccessKeySecret(PlayParameter.PLAY_PARAM_AK_SECRE);
                        vidSts.setSecurityToken(PlayParameter.PLAY_PARAM_SCU_TOKEN);
                        downloadManager.prepareDownload(vidSts);
                    }
                }
            }
        });

        showMoreView.setOnScreenCastButtonClickListener(new ShowMoreView.OnScreenCastButtonClickListener() {
            @Override
            public void onScreenCastClick() {
                Toast.makeText(TryPlayerActivity.this, "功能正在开发中......", Toast.LENGTH_SHORT).show();
//                TODO 2019年04月18日16:43:29  先屏蔽投屏功能
//                showMoreDialog.dismiss();
//                showScreenCastView();
            }
        });

        showMoreView.setOnBarrageButtonClickListener(new ShowMoreView.OnBarrageButtonClickListener() {
            @Override
            public void onBarrageClick() {
                Toast.makeText(TryPlayerActivity.this, "功能正在开发中......", Toast.LENGTH_SHORT).show();
//                if (showMoreDialog != null && showMoreDialog.isShowing()) {
//                    showMoreDialog.dismiss();
//                }
            }
        });

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
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness / 255.0f;
        window.setAttributes(lp);
    }
}