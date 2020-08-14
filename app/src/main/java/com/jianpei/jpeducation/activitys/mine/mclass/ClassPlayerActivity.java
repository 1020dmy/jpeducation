package com.jianpei.jpeducation.activitys.mine.mclass;


import android.content.Intent;
import android.content.res.Configuration;
import android.text.TextUtils;
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
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.fragment.mine.mclass.PlayerCommentFragment;
import com.jianpei.jpeducation.fragment.mine.mclass.PlayerListFragment;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.classdownload.VideoDownloadManager;
import com.jianpei.jpeducation.viewmodel.ClassPlayerModel;
import com.jianpei.jpeducation.viewmodel.OfflineClassRoomModel;


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
    @BindView(R.id.tv_down_num)
    TextView tvDownNum;
    private MyClassBean myClassBean;

    private String[] tabTitle = {"目录", "评价"};

    private ClassPlayerModel classPlayerModel;

    AlivcShowMoreDialog showMoreDialog;

    private OfflineClassRoomModel classRoomModel;

    private int type = 0;//0播放，1下载

    private ViodBean dViodBean, pViodBean;//正在下载的，正在播放的

    private PlayerListFragment playerListFragment;
    private PlayerCommentFragment playerCommentFragment;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_class_player;
    }

    @Override
    protected void initView() {
        setTitleViewPadding2(tvStatus);
        initShare();//初始化分享
        myClassBean = getIntent().getParcelableExtra("myClassBean");
        //
        playerListFragment = new PlayerListFragment(myClassBean.getCid(), myClassBean.getId());
        playerCommentFragment = new PlayerCommentFragment(myClassBean.getCid());
        Fragment[] fragments = {playerListFragment, playerCommentFragment};
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

        //查询下载数量结果
        classRoomModel.getViodBeanLiveData().observe(this, new Observer<List<ViodBean>>() {
            @Override
            public void onChanged(List<ViodBean> viodBeans) {
                if (viodBeans != null) {
                    if (viodBeans.size() == 0) {
                        tvDownNum.setVisibility(View.GONE);
                    } else {
                        tvDownNum.setVisibility(View.VISIBLE);
                        tvDownNum.setText(viodBeans.size() + "");
                    }
                }

            }
        });
        //错误
        classRoomModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                if (!TextUtils.isEmpty(o)) {
                    shortToast(o);
                }

            }
        });
        //更新下载数量
        classPlayerModel.getStringMutableLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                classRoomModel.getRoomViodBean(3);

            }
        });
        //视频播放url
        classPlayerModel.getVideoUrlBeansLiveData().observe(this, new Observer<VideoUrlBean>() {
            @Override
            public void onChanged(VideoUrlBean videoUrlBean) {
                dismissLoading();
                if (type == 0) {
                    if (videoUrlBean.getType() == 0) {
                        if (aliyunPlayerView != null)
                            aliyunPlayerView.onStop();
                        playVideo(videoUrlBean);
                    }
                } else {
                    if (videoDownloadManager == null) {
                        initDownload();
                    }
                    downloadVideo(videoUrlBean, dViodBean);

                }
            }
        });

        //视频切换播放
        classPlayerModel.getViodBeanMutableLiveData().observe(this, new Observer<ViodBean>() {
            @Override
            public void onChanged(ViodBean viodBean) {
                if (viodBean == null)
                    return;
                type = 0;
                pViodBean = viodBean;
                L.e("====pViodBean:" + pViodBean.toString());
                if (TextUtils.isEmpty(viodBean.getSavePath())) {//点播播放
                    showLoading("");
                    classPlayerModel.videoUrl(viodBean.getId(), myClassBean.getId(), myClassBean.getCid());
                } else {
                    if (aliyunPlayerView != null)
                        aliyunPlayerView.onStop();
                    changePlayLocalSource(viodBean.getSavePath(), "");

                }
            }
        });
        //视频下载
        classPlayerModel.getDownloadViodLiveData().observe(this, new Observer<ViodBean>() {
            @Override
            public void onChanged(ViodBean viodBean) {
                type = 1;
                dViodBean = viodBean;
                classPlayerModel.videoUrl(viodBean.getId(), myClassBean.getId(), myClassBean.getCid());
            }
        });
        //
        classPlayerModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                if (!TextUtils.isEmpty(o))
                    shortToast(o);

            }
        });

        classPlayerModel.getUpdateScheduleLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                L.e("s:" + s);
            }
        });
        //查询下载数量
        classRoomModel.getRoomViodBean(3);

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
        if (!TextUtils.isEmpty(pViodBean.getDqtime())) {
            aliyunPlayerView.seekTo(Integer.parseInt(pViodBean.getDqtime()) * 1000);
        }

    }

    private VideoDownloadManager videoDownloadManager;

    //视频下载
    protected void downloadVideo(VideoUrlBean videoUrlBean, ViodBean viodBean) {
        if (videoUrlBean == null) {
            shortToast("视频加载失败");
            return;
        }
        VidAuth vidAuth = new VidAuth();
        vidAuth.setPlayAuth(videoUrlBean.getAuth());
        vidAuth.setVid(videoUrlBean.getVid());
        vidAuth.setRegion("cn-shanghai");
        videoDownloadManager.prepareDownload(vidAuth, viodBean);
    }

    protected void initDownload() {
        // 获取AliyunDownloadManager对象
        videoDownloadManager = VideoDownloadManager.getInstance();
        //设置同时下载个数
        videoDownloadManager.setMaxNum(3);

//        videoDownloadManager = DownloadDataProvider.getSingleton(getActivity().getApplicationContext());
        // 更新sts回调
//        videoDownloadManager.setRefreshStsCallback(new MyRefreshStsCallback());

        // 视频下载的回调
        videoDownloadManager.setDownloadInfoListener(playerListFragment);
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

        aliyunPlayerView.setScheduleListener(new AliyunVodPlayerView.ScheduleListener() {
            @Override
            public void onSchedule(long sourceDuration, long progress) {
                L.e("=====progress:" + progress);
                if (progress != 0 && progress % 20 == 0) {//没隔20秒更新记录
                    classPlayerModel.updateSchedule(sourceDuration + "", progress + "", myClassBean.getCid(), pViodBean.getChapter_id(), pViodBean.getId(), myClassBean.getId());
                }


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
