package com.jianpei.jpeducation.activitys.mine.mclass;


import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.player.source.VidAuth;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.mclass.DownloadClassAdapter;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.bean.classinfo.VideoUrlBean;
import com.jianpei.jpeducation.bean.mclass.DirectoryBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.utils.myclassdown.DownloadClassListener;
import com.jianpei.jpeducation.utils.myclassdown.DownloadClassManager;
import com.jianpei.jpeducation.viewmodel.ClassPlayerModel;
import com.jianpei.jpeducation.viewmodel.OfflineClassRoomModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ClassDownloadActivity extends BaseNoStatusActivity implements MyItemOnClickListener {


    @BindView(R.id.iv_statue)
    ImageView ivStatue;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.imageButton)
    ImageButton imageButton;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private DownloadClassAdapter downloadClassAdapter;

    private OfflineClassRoomModel offlineClassRoomModel;


    private List<ViodBean> viodBeans;

    private MyDownloadClassListener myDownloadClassListener;

    private ClassPlayerModel classPlayerModel;
    //
    private String buyId, cid;
    //
    private ViodBean mViodBean;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_class_download;
    }

    @Override
    protected void initView() {
        tvTitle.setText("下载");
        imageButton.setVisibility(View.GONE);
        setTitleViewPadding(ivStatue);

        buyId = getIntent().getStringExtra("buyId");
        cid = getIntent().getStringExtra("cid");

        offlineClassRoomModel = new ViewModelProvider(this).get(OfflineClassRoomModel.class);
        classPlayerModel = new ViewModelProvider(this).get(ClassPlayerModel.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void initData() {
        downloadClassAdapter = new DownloadClassAdapter(this);
        recyclerView.setAdapter(downloadClassAdapter);
        //更新结果
        offlineClassRoomModel.getDirectoryBeansLiveData().observe(this, new Observer<List<DirectoryBean>>() {
            @Override
            public void onChanged(List<DirectoryBean> directoryBeans) {
                dismissLoading();
                downloadClassAdapter.setList(directoryBeans);
                for (DirectoryBean directoryBean : directoryBeans) {
                    if (directoryBean.getViods() != null) {
                        if (viodBeans == null) {
                            viodBeans = new ArrayList<>();
                        }
                        viodBeans.addAll(directoryBean.getViods());

                    }
                }
            }
        });

        offlineClassRoomModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });
        //删除结果
        offlineClassRoomModel.getDeleteResultLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                offlineClassRoomModel.getUndone();
                classPlayerModel.getStringMutableLiveData().setValue("");//更新下载数量
            }
        });
//        //重新获取vid
        classPlayerModel.getVideoUrlBeansLiveData().observe(this, new Observer<VideoUrlBean>() {
            @Override
            public void onChanged(VideoUrlBean videoUrlBean) {
                downloadVideo(videoUrlBean, mViodBean);
            }
        });


        showLoading("");
        offlineClassRoomModel.getUndone();


        //下载监听
        myDownloadClassListener = new MyDownloadClassListener();
        DownloadClassManager.getInstance().addDownloadInfoListener(myDownloadClassListener);
    }

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
        DownloadClassManager.getInstance().paperDownload(vidAuth, viodBean);
    }

//    private class MyClassDownloadListener implements ClassDownloadListener {
//        private WeakReference<ClassDownloadActivity> weakReference;
//
//        public MyClassDownloadListener(ClassDownloadActivity playerListFragment) {
//            weakReference = new WeakReference<>(playerListFragment);
//        }
//
//        @Override
//        public void onPrepared(List<ViodBean> viodBeans) {
//        }
//
//        @Override
//        public void onAdd(ViodBean info) {
//
//        }
//
//        @Override
//        public void onStart(ViodBean info) {
//            ClassDownloadActivity playerListFragment = weakReference.get();
//            if (playerListFragment != null && viodBeans != null) {
//                for (ViodBean viodBean : viodBeans) {
//                    if (viodBean.getId().equals(info.getId())) {
//                        viodBean.setProgress(info.getProgress());
//                        viodBean.setStatus(info.getStatus());
//                    }
//                }
//                downloadClassAdapter.notifyDataSetChanged();
//            }
//
//        }
//
//        @Override
//        public void onProgress(ViodBean info, int percent) {
//            ClassDownloadActivity playerListFragment = weakReference.get();
//            if (playerListFragment != null && viodBeans != null) {
//                for (ViodBean viodBean : viodBeans) {
//                    if (viodBean.getId().equals(info.getId())) {
//                        viodBean.setProgress(info.getProgress());
//                        viodBean.setStatus(info.getStatus());
//                    }
//                }
//                downloadClassAdapter.notifyDataSetChanged();
//            }
//
//        }
//
//        @Override
//        public void onStop(ViodBean info) {
//
//        }
//
//        @Override
//        public void onCompletion(ViodBean info) {
//            ClassDownloadActivity playerListFragment = weakReference.get();
//            if (playerListFragment != null) {
//                offlineClassRoomModel.getUndone();
//
//            }
//        }
//
//        @Override
//        public void onError(ViodBean info, ErrorCode code, String msg, String requestId) {
//
//        }
//
//        @Override
//        public void onWait(ViodBean outMediaInfo) {
//
//        }
//
//        @Override
//        public void onDelete(ViodBean info) {
//
//            offlineClassRoomModel.deleteViodBean(info);
//
//        }
//
//        @Override
//        public void onDeleteAll() {
//
//        }
//
//        @Override
//        public void onFileProgress(ViodBean info) {
//
//        }
//    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }


    @Override
    public void onItemClick(int position, View view) {

    }

    @Override
    public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        switch (view.getId()) {
            case R.id.tv_status:
                ViodBean viodBean = (ViodBean) data;
                if (viodBean.getStatus() == DownloadClassManager.STOP || viodBean.getStatus() == DownloadClassManager.ERROR) {//继续下载
                    DownloadClassManager.getInstance().continueDownload(viodBean);

                } else if (viodBean.getStatus() == DownloadClassManager.START) {//停止下载
                    DownloadClassManager.getInstance().stopDownload(viodBean);
                }

                break;
            case R.id.tv_delete:
                showLoading("");
                DownloadClassManager.getInstance().deleteFile((ViodBean) data);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        DownloadClassManager.getInstance().clearListener(myDownloadClassListener);
        myDownloadClassListener = null;
        super.onDestroy();
    }

    /**
     * 下载更新列表
     *
     * @param viodBean
     */
    protected void upDataList(ViodBean viodBean) {
        for (ViodBean viodBean1 : viodBeans) {
            if (viodBean.getId().equals(viodBean1.getId())) {
                viodBean1.setProgress(viodBean.getProgress());
                viodBean1.setStatus(viodBean.getStatus());
                downloadClassAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    private class MyDownloadClassListener implements DownloadClassListener {
        @Override
        public void onPrepared(List<TrackInfo> trackInfos, ViodBean viodBean) {

        }

        @Override
        public void onStart(ViodBean viodBean) {
            upDataList(viodBean);

        }

        @Override
        public void onStop(ViodBean viodBean) {
            upDataList(viodBean);
        }

        @Override
        public void onProgress(ViodBean viodBean, int progress) {
            upDataList(viodBean);
        }

        @Override
        public void onFileProgress(ViodBean viodBean, int progress) {

        }

        @Override
        public void onError(ViodBean viodBean, ErrorInfo errorInfo) {
            upDataList(viodBean);

        }

        @Override
        public void onCompletion(ViodBean viodBean) {
            upDataList(viodBean);
            offlineClassRoomModel.getUndone();

        }

        @Override
        public void againStart(ViodBean viodBean) {
            if (viodBean.getStatus() == DownloadClassManager.STOP || viodBean.getStatus() == DownloadClassManager.ERROR) {
                mViodBean=viodBean;
                classPlayerModel.videoUrl(viodBean.getId(), buyId, cid);//获取vid
            }
        }

        @Override
        public void deleteFile(ViodBean viodBean) {
            offlineClassRoomModel.deleteViodBean(viodBean);
        }
    }
}
