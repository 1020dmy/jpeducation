package com.jianpei.jpeducation.fragment.mine.mclass;

import android.content.Context;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliyun.player.bean.ErrorCode;


import com.aliyun.vodplayerview.activity.AliyunPlayerSkinActivity;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.mine.mclass.ClassPlayerActivity;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.mclass.PlayListAdapter;
import com.jianpei.jpeducation.adapter.mclass.PlayListChapterProvider;
import com.jianpei.jpeducation.adapter.mclass.PlayListJIeProvider;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.classinfo.VideoUrlBean;
import com.jianpei.jpeducation.bean.mclass.DirectoryBean;
import com.jianpei.jpeducation.bean.mclass.MClassInfoBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.classdownload.ClassDownloadListener;
import com.jianpei.jpeducation.utils.classdownload.VideoDownloadManager;
import com.jianpei.jpeducation.utils.pop.DownloadClassPopup;
import com.jianpei.jpeducation.viewmodel.ClassPlayerModel;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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


    //    public LinkedHashMap<String, BaseViewHolder> downloadingInfos = new LinkedHashMap<>();
//    private BaseViewHolder mBaseViewHolder;


    int positoin = 0;


    public PlayerListFragment(String classId, String buyId) {
        this.classId = classId;
//        this.buyId = buyId;
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

        VideoDownloadManager.getInstance().setDownloadInfoListener(new MyClassDownloadListener(this));

        nestedScrollView.fullScroll(View.FOCUS_DOWN);


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


    protected void setProgress(TextView tv_progress, int progress) {
        tv_progress.setText(progress + "%");

    }

    private class MyClassDownloadListener implements ClassDownloadListener {
        private WeakReference<PlayerListFragment> weakReference;

        public MyClassDownloadListener(PlayerListFragment playerListFragment) {
            weakReference = new WeakReference<>(playerListFragment);
        }

        @Override
        public void onPrepared(List<ViodBean> viodBeans) {
            dismissLoading();
            if (downloadClassPopup == null) {
                downloadClassPopup = new DownloadClassPopup(weakReference.get().getActivity());
                downloadClassPopup.setMyClickListener(new DownloadClassPopup.MyClickListener() {
                    @Override
                    public void ClickListener(ViodBean downLoadTag) {
                        //点击了下载
                        VideoDownloadManager.getInstance().startDownload(downLoadTag);
                    }
                });
            }
            downloadClassPopup.showAllDownloadItems(viodBeans);
            downloadClassPopup.showPop();
        }

        @Override
        public void onAdd(ViodBean info) {

        }

        @Override
        public void onStart(ViodBean info) {
            PlayerListFragment playerListFragment = weakReference.get();
            if (playerListFragment != null && mViodBeans!=null) {
                for (ViodBean viodBean : mViodBeans) {
                    if (viodBean.getId().equals(info.getId())) {
                        viodBean.setProgress(info.getProgress());
                        viodBean.setStatus(viodBean.getStatus());
                    }
                }
                playListAdapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onProgress(ViodBean info, int percent) {
            PlayerListFragment playerListFragment = weakReference.get();
            if (playerListFragment != null && mViodBeans!=null) {
                for (ViodBean viodBean : mViodBeans) {
                    if (viodBean.getId().equals(info.getId())) {
                        viodBean.setProgress(info.getProgress());
                        viodBean.setStatus(viodBean.getStatus());
                    }
                }
                playListAdapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onStop(ViodBean info) {

        }

        @Override
        public void onCompletion(ViodBean info) {
            PlayerListFragment playerListFragment = weakReference.get();
            if (playerListFragment != null && mViodBeans!=null) {
                for (ViodBean viodBean : mViodBeans) {
                    if (viodBean.getId().equals(info.getId())) {
                        viodBean.setProgress(info.getProgress());
                        viodBean.setStatus(viodBean.getStatus());
                    }
                }
                playListAdapter.notifyDataSetChanged();
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
            PlayerListFragment playerListFragment = weakReference.get();
            if (playerListFragment != null && mViodBeans!=null) {
                for (ViodBean viodBean : mViodBeans) {
                    if (viodBean.getId().equals(info.getId())) {
                        viodBean.setProgress(info.getProgress());
                        viodBean.setStatus(viodBean.getStatus());
                    }
                }
                playListAdapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onDeleteAll() {

        }

        @Override
        public void onFileProgress(ViodBean info) {

        }
    }


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
//                mBaseViewHolder = helper;
                //发送视频下载的通知
                classPlayerModel.getDownloadViodLiveData().setValue((ViodBean) data);
                break;
            case R.id.ll_section://播放
                classPlayerModel.getViodBeanMutableLiveData().setValue((ViodBean) data);
                break;
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
