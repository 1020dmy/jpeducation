package com.jianpei.jpeducation.activitys.mine.mclass;


import android.content.Intent;
import android.content.res.Configuration;
import android.view.View;
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

import com.aliyun.player.source.UrlSource;
import com.aliyun.player.source.VidAuth;

import com.aliyun.vodplayerview.utils.download.AliyunDownloadManager;
import com.aliyun.vodplayerview.view.choice.AlivcShowMoreDialog;
import com.aliyun.vodplayerview.view.control.ControlView;
import com.aliyun.vodplayerview.view.more.AliyunShowMoreValue;
import com.aliyun.vodplayerview.view.more.ShowMoreView;
import com.aliyun.vodplayerview.view.more.SpeedValue;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.TabFragmentAdapter;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.bean.classinfo.VideoUrlBean;
import com.jianpei.jpeducation.bean.mclass.MyClassBean;
import com.jianpei.jpeducation.fragment.mine.mclass.PlayerCommentFragment;
import com.jianpei.jpeducation.fragment.mine.mclass.PlayerListFragment;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.classdownload.DownloadMediaInfo;
import com.jianpei.jpeducation.viewmodel.ClassPlayerModel;
import com.jianpei.jpeducation.viewmodel.OfflineClassRoomModel;


import java.util.concurrent.ConcurrentLinkedQueue;

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
    @BindView(R.id.tv_down_num)
    TextView tvDownNum;
    private MyClassBean myClassBean;

    private String[] tabTitle = {"目录", "评价"};

    private ClassPlayerModel classPlayerModel;

    AlivcShowMoreDialog showMoreDialog;

    private OfflineClassRoomModel classRoomModel;


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
        classRoomModel = new ViewModelProvider(this).get(OfflineClassRoomModel.class);
        //网络点播
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
        //本地播放
        classPlayerModel.getPlayLocationUrl().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                L.e("播放本地的资源文件");
                dismissLoading();
                if (aliyunPlayerView != null)
                    aliyunPlayerView.onStop();
                changePlayLocalSource(s, "");
            }
        });
        //查询数量结果
        classRoomModel.getDownloadMedialInfosLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer downloadMediaInfos) {
                if (downloadMediaInfos != null) {
                    if (downloadMediaInfos == 0) {
                        tvDownNum.setVisibility(View.GONE);
                    } else {
                        tvDownNum.setVisibility(View.VISIBLE);
                        tvDownNum.setText(downloadMediaInfos + "");
                    }
                }

            }
        });
        //错误
        classRoomModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {

                shortToast(o);

            }
        });
        //更新下载数量
        classPlayerModel.getStringMutableLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                classRoomModel.getDownloadMedialInfos(3);

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

    /**
     * 播放本地资源
     */
    private void changePlayLocalSource(String url, String title) {
        UrlSource urlSource = new UrlSource();
        urlSource.setUri(url);
        urlSource.setTitle(title);
        aliyunPlayerView.setLocalSource(urlSource);
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
        updatePlayerViewMode(aliyunPlayerView);

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


}
