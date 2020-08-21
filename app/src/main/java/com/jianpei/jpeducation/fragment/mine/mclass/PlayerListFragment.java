package com.jianpei.jpeducation.fragment.mine.mclass;

import android.content.Context;

import android.view.View;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.nativeclass.TrackInfo;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.mclass.PlayListAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.mclass.DirectoryBean;
import com.jianpei.jpeducation.bean.mclass.MClassInfoBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.myclassdown.DownloadClassListener;
import com.jianpei.jpeducation.utils.myclassdown.DownloadClassManager;
import com.jianpei.jpeducation.utils.myclassdown.RefreshVidCallback;
import com.jianpei.jpeducation.utils.pop.DownloadClassPopup;
import com.jianpei.jpeducation.viewmodel.ClassPlayerModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PlayerListFragment extends BaseFragment implements MyItemOnClickListener {


    @BindView(R.id.tv_teachers)
    TextView tvTeachers;
    @BindView(R.id.tv_profession)
    TextView tvProfession;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    private ClassPlayerModel classPlayerModel;

    private String classId;

    private PlayListAdapter playListAdapter;
    private LinearLayoutManager linearLayoutManager;


    private DownloadClassPopup downloadClassPopup;

    private List<ViodBean> mViodBeans;


    int positoin = 0;

    private MyDownloadClassListener myDownloadClassListener;
    private MyRefreshVidCallback myRefreshVidCallback;


    public PlayerListFragment(String classId) {
        this.classId = classId;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_player_list;
    }

    @Override
    protected void initView(View view) {
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        playListAdapter = new PlayListAdapter(this);
        recyclerView.setAdapter(playListAdapter);

        nestedScrollView.fullScroll(View.FOCUS_DOWN);


        if (myDownloadClassListener == null)
            myDownloadClassListener = new MyDownloadClassListener();
        DownloadClassManager.getInstance().addDownloadInfoListener(myDownloadClassListener);
    }

    @Override
    protected void initData(Context mContext) {
        classPlayerModel = new ViewModelProvider(getActivity()).get(ClassPlayerModel.class);

        //获取课程信息
        classPlayerModel.getmClassInfoBeanLiveData().observe(this, new Observer<MClassInfoBean>() {
            @Override
            public void onChanged(MClassInfoBean mClassInfoBean) {
                dismissLoading();
                setData(mClassInfoBean);
                if (positoin != 0) {//定位当前播放的位置
                    nestedScrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            int height = (positoin + 3) * 54;
                            if (height < nestedScrollView.getMeasuredHeight())
                                nestedScrollView.smoothScrollTo(0, height);
                            else {
                                nestedScrollView.fullScroll(NestedScrollView.FOCUS_DOWN);
                            }
                        }
                    });
                }
            }
        });
        //节列表结果
        classPlayerModel.getViodListLiveData().observe(this, new Observer<List<ViodBean>>() {
            @Override
            public void onChanged(List<ViodBean> viodBeans) {
                dismissLoading();
                playListAdapter.nodeAddData(directoryBean, 0, viodBeans);
                if (mViodBeans == null) {
                    mViodBeans = new ArrayList<>();
                }
                mViodBeans.addAll(viodBeans);

            }
        });
        //获取失败
        classPlayerModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });

        showLoading("");
        classPlayerModel.classInfo(classId);

    }

    ///数据更新
    protected void setData(MClassInfoBean mClassInfoBean) {
        if (mClassInfoBean == null)
            return;

        List<MClassInfoBean.TeachersBean> teachersBeans = mClassInfoBean.getTeachers();
        if (teachersBeans != null && teachersBeans.size() != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (MClassInfoBean.TeachersBean teachersBean : teachersBeans) {
                stringBuilder.append(teachersBean.getTitle() + " ");
            }
            String teachers = stringBuilder.toString();
            stringBuilder.replace(0, stringBuilder.length(), "");
            stringBuilder.reverse();
            stringBuilder = null;
            tvTeachers.setText(teachers);
        }
        tvProfession.setText(mClassInfoBean.getViod_info().getTitle());
        playListAdapter.setList(mClassInfoBean.getDirectorys());
        for (DirectoryBean directoryBean : mClassInfoBean.getDirectorys()) {
            positoin++;
            if (directoryBean.getViods() != null) {
                if (mViodBeans == null)
                    mViodBeans = new ArrayList<>();
                mViodBeans.addAll(directoryBean.getViods());
            }
            for (ViodBean viodBean : directoryBean.getViods()) {
                positoin++;
                if ("1".equals(viodBean.getIs_last_read())) {
                    classPlayerModel.getViodBeanMutableLiveData().setValue(viodBean);
                    return;
                }
            }
        }


    }


    private class MyDownloadClassListener implements DownloadClassListener {

        @Override
        public void onPrepared(List<TrackInfo> trackInfos, ViodBean viodBean) {
            showClassPopup(trackInfos, viodBean);
        }

        @Override
        public void onStart(ViodBean viodBean) {
            upDataList(viodBean);
            classPlayerModel.getStringMutableLiveData().setValue("");//更新下载数量

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
            classPlayerModel.getStringMutableLiveData().setValue("");//更新下载数量
        }


        @Override
        public void deleteFile(ViodBean viodBean) {
            upDataList(viodBean);
        }
    }

    /**
     * 刷新vid监听
     */
    class MyRefreshVidCallback implements RefreshVidCallback {
        @Override
        public void refreshVid(ViodBean viodBean) {
            //重新获取vid
            classPlayerModel.getDownloadViodLiveData().setValue(viodBean);

        }
    }

    /**
     * 下载更新列表
     *
     * @param viodBean
     */
    protected void upDataList(ViodBean viodBean) {
        if (mViodBeans == null)
            return;
        for (ViodBean viodBean1 : mViodBeans) {
            if (viodBean.getId().equals(viodBean1.getId())) {
                viodBean1.setProgress(viodBean.getProgress());
                viodBean1.setStatus(viodBean.getStatus());
                playListAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    /**
     * 显示下载选项弹窗
     *
     * @param trackInfos
     * @param viodBean
     */
    protected void showClassPopup(List<TrackInfo> trackInfos, ViodBean viodBean) {
        dismissLoading();
        if (downloadClassPopup == null) {
            downloadClassPopup = new DownloadClassPopup(getActivity());
            downloadClassPopup.setMyClickListener(new DownloadClassPopup.MyClickListener() {
                @Override
                public void ClickListener(TrackInfo trackInfo, ViodBean viodBean) {
                    //开始下载
                    DownloadClassManager.getInstance().startDownload(trackInfo, viodBean);
                }
            });
        }
        downloadClassPopup.showAllDownloadItems(trackInfos, viodBean);
        downloadClassPopup.showPop();
    }

//    private class MyClassDownloadListener implements ClassDownloadListener {
//        private WeakReference<PlayerListFragment> weakReference;
//
//        public MyClassDownloadListener(PlayerListFragment playerListFragment) {
//            weakReference = new WeakReference<>(playerListFragment);
//        }
//
//        @Override
//        public void onPrepared(List<ViodBean> viodBeans) {
//            dismissLoading();
//            if (downloadClassPopup == null) {
//                downloadClassPopup = new DownloadClassPopup(weakReference.get().getActivity());
//                downloadClassPopup.setMyClickListener(new DownloadClassPopup.MyClickListener() {
//                    @Override
//                    public void ClickListener(ViodBean downLoadTag) {
//                        //点击了下载
//                        VideoDownloadManager.getInstance().startDownload(downLoadTag);
//                    }
//                });
//            }
//            downloadClassPopup.showAllDownloadItems(viodBeans);
//            downloadClassPopup.showPop();
//        }
//
//        @Override
//        public void onAdd(ViodBean info) {
//
//        }
//
//        @Override
//        public void onStart(ViodBean info) {
//            PlayerListFragment playerListFragment = weakReference.get();
//            if (playerListFragment != null && mViodBeans!=null) {
//                for (ViodBean viodBean : mViodBeans) {
//                    if (viodBean.getId().equals(info.getId())) {
//                        viodBean.setProgress(info.getProgress());
//                        viodBean.setStatus(viodBean.getStatus());
//                    }
//                }
//                playListAdapter.notifyDataSetChanged();
//            }
//
//        }
//
//        @Override
//        public void onProgress(ViodBean info, int percent) {
//            PlayerListFragment playerListFragment = weakReference.get();
//            if (playerListFragment != null && mViodBeans!=null) {
//                for (ViodBean viodBean : mViodBeans) {
//                    if (viodBean.getId().equals(info.getId())) {
//                        viodBean.setProgress(info.getProgress());
//                        viodBean.setStatus(viodBean.getStatus());
//                    }
//                }
//                playListAdapter.notifyDataSetChanged();
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
//            PlayerListFragment playerListFragment = weakReference.get();
//            if (playerListFragment != null && mViodBeans!=null) {
//                for (ViodBean viodBean : mViodBeans) {
//                    if (viodBean.getId().equals(info.getId())) {
//                        viodBean.setProgress(info.getProgress());
//                        viodBean.setStatus(viodBean.getStatus());
//                    }
//                }
//                playListAdapter.notifyDataSetChanged();
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
//            PlayerListFragment playerListFragment = weakReference.get();
//            if (playerListFragment != null && mViodBeans!=null) {
//                for (ViodBean viodBean : mViodBeans) {
//                    if (viodBean.getId().equals(info.getId())) {
//                        viodBean.setProgress(info.getProgress());
//                        viodBean.setStatus(viodBean.getStatus());
//                    }
//                }
//                playListAdapter.notifyDataSetChanged();
//            }
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


    ////
    @Override
    public void onItemClick(int position, View view) {

    }

    DirectoryBean directoryBean;

    @Override
    public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        switch (view.getId()) {
            case R.id.ll_title://章
                directoryBean = (DirectoryBean) data;
                if (directoryBean.isExpanded() && directoryBean.getChildNode().size() == 0) {//获取资料
                    showLoading("");
                    classPlayerModel.viodList(classId, directoryBean.getId(), "2");
                }
                break;
            case R.id.iv_download://下载
                showLoading("");
                //发送视频下载的通知
                classPlayerModel.getDownloadViodLiveData().setValue((ViodBean) data);
                break;
            case R.id.ll_section://播放
                classPlayerModel.getViodBeanMutableLiveData().setValue((ViodBean) data);
                break;
            case R.id.tv_progress://暂停/继续
                ViodBean viodBean = (ViodBean) data;
                if (viodBean.getStatus() == DownloadClassManager.STOP || viodBean.getStatus() == DownloadClassManager.ERROR) {//继续下载
                    if (viodBean.getVidAuth() != null) {
                        L.e("===========重新获取直接下载");
                        DownloadClassManager.getInstance().continueDownload(viodBean);
                    } else {//重新获取vid
                        L.e("===========重新获取vid");
                        classPlayerModel.getDownloadViodLiveData().setValue((ViodBean) data);
                    }
                } else if (viodBean.getStatus() == DownloadClassManager.START) {//停止下载
                    DownloadClassManager.getInstance().stopDownload(viodBean);
                }
                break;
        }

    }

    @Override
    public void onResume() {
        //vid刷新监听
        if (myRefreshVidCallback == null)
            myRefreshVidCallback = new MyRefreshVidCallback();
        DownloadClassManager.getInstance().clearRefreshVidCallback();
        DownloadClassManager.getInstance().setRefreshVidCallback(myRefreshVidCallback);
        super.onResume();
    }


    @Override
    public void onDestroy() {
        DownloadClassManager.getInstance().clearListener(myDownloadClassListener);
        DownloadClassManager.getInstance().clearRefreshVidCallback();
        myDownloadClassListener = null;
        myRefreshVidCallback = null;
        if (mViodBeans != null)
            mViodBeans.clear();
        super.onDestroy();
    }

}
