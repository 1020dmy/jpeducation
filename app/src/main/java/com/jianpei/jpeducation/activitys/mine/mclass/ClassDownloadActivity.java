package com.jianpei.jpeducation.activitys.mine.mclass;


import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliyun.player.bean.ErrorCode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.mclass.DownloadClassAdapter;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.bean.mclass.DirectoryBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.classdownload.ClassDownloadListener;
import com.jianpei.jpeducation.utils.classdownload.VideoDownloadManager;
import com.jianpei.jpeducation.viewmodel.OfflineClassRoomModel;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
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
//    private DownloadClassJieProvider downloadClassJieProvider;

    private OfflineClassRoomModel offlineClassRoomModel;


//    public LinkedHashMap<String, BaseViewHolder> downloadingInfos = new LinkedHashMap<>();


    private List<ViodBean> viodBeans;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_class_download;
    }

    @Override
    protected void initView() {
        tvTitle.setText("下载");
        imageButton.setVisibility(View.GONE);
        setTitleViewPadding(ivStatue);

        offlineClassRoomModel = new ViewModelProvider(this).get(OfflineClassRoomModel.class);

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
                L.e("==========s:" + s);
                offlineClassRoomModel.getUndone();
            }
        });


        showLoading("");
        offlineClassRoomModel.getUndone();


        VideoDownloadManager.getInstance().setDownloadInfoListener(new MyClassDownloadListener(this));

    }

    private class MyClassDownloadListener implements ClassDownloadListener {
        private WeakReference<ClassDownloadActivity> weakReference;

        public MyClassDownloadListener(ClassDownloadActivity playerListFragment) {
            weakReference = new WeakReference<>(playerListFragment);
        }

        @Override
        public void onPrepared(List<ViodBean> viodBeans) {
        }

        @Override
        public void onAdd(ViodBean info) {

        }

        @Override
        public void onStart(ViodBean info) {
            ClassDownloadActivity playerListFragment = weakReference.get();
            if (playerListFragment != null && viodBeans != null) {
                for (ViodBean viodBean : viodBeans) {
                    if (viodBean.getId().equals(info.getId())) {
                        viodBean.setProgress(info.getProgress());
                        viodBean.setStatus(info.getStatus());
                    }
                }
                downloadClassAdapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onProgress(ViodBean info, int percent) {
            ClassDownloadActivity playerListFragment = weakReference.get();
            if (playerListFragment != null && viodBeans != null) {
                for (ViodBean viodBean : viodBeans) {
                    if (viodBean.getId().equals(info.getId())) {
                        viodBean.setProgress(info.getProgress());
                        viodBean.setStatus(info.getStatus());
                    }
                }
                downloadClassAdapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onStop(ViodBean info) {

        }

        @Override
        public void onCompletion(ViodBean info) {
            ClassDownloadActivity playerListFragment = weakReference.get();
            if (playerListFragment != null) {
                offlineClassRoomModel.getUndone();

            }
        }

        @Override
        public void onError(ViodBean info, ErrorCode code, String msg, String requestId) {

        }

        @Override
        public void onWait(ViodBean outMediaInfo) {

        }

        @Override
        public void onDelete(ViodBean info) {

            offlineClassRoomModel.deleteViodBean(info);

        }

        @Override
        public void onDeleteAll() {

        }

        @Override
        public void onFileProgress(ViodBean info) {

        }
    }

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
                break;
            case R.id.tv_delete:
                L.e("=====delete======");
                showLoading("");
                ViodBean viodBean = (ViodBean) data;
                VideoDownloadManager.getInstance().deleteFile(viodBean);
                break;
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
