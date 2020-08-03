package com.jianpei.jpeducation.fragment.mine.mclass;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliyun.player.bean.ErrorCode;

import com.aliyun.player.source.VidAuth;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.mclass.PlayListAdapter;
import com.jianpei.jpeducation.adapter.mclass.PlayListChapterProvider;
import com.jianpei.jpeducation.adapter.mclass.PlayListJIeProvider;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.classinfo.VideoUrlBean;
import com.jianpei.jpeducation.bean.mclass.MClassInfoBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.classdownload.ClassDownloadListener;
import com.jianpei.jpeducation.utils.classdownload.DownloadMediaInfo;
import com.jianpei.jpeducation.utils.classdownload.VideoDownloadManager;
import com.jianpei.jpeducation.utils.pop.DownloadClassPopup;
import com.jianpei.jpeducation.viewmodel.ClassPlayerModel;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PlayerListFragment extends BaseFragment {


    @BindView(R.id.tv_teachers)
    TextView tvTeachers;
    @BindView(R.id.tv_profession)
    TextView tvProfession;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ClassPlayerModel classPlayerModel;

    private String classId;

    private PlayListAdapter playListAdapter;

    private PlayListJIeProvider playListJIeProvider;

    private String buyId;

    private int nPosition = -1;

    private ViodBean viodBean;
    private String viodBeanId;

    private int type;


    private DownloadClassPopup downloadClassPopup;

    private VideoDownloadManager videoDownloadManager;

    private LinkedHashMap<DownloadMediaInfo, BaseViewHolder> downloadingInfos = new LinkedHashMap<>();
    private BaseViewHolder mBaseViewHolder;


    public PlayerListFragment(String classId, String buyId) {
        this.classId = classId;
        this.buyId = buyId;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_player_list;
    }

    @Override
    protected void initView(View view) {
        playListJIeProvider = new PlayListJIeProvider();
        playListJIeProvider.addChildClickViewIds(R.id.linearLayout, R.id.iv_download);
        playListJIeProvider.setMyItemOnClickListener(new MyItemOnClickListener() {
            @Override
            public void onItemClick(int position, View view) {

            }

            @Override
            public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
                switch (view.getId()) {
                    case R.id.linearLayout:
                        type = 0;
                        if (nPosition != position) {
                            showLoading("");
                            if (viodBean != null)
                                viodBean.setIs_last_read("0");
                            viodBean = (ViodBean) data;
                            viodBean.setIs_last_read("1");
                            if (nPosition != -1)
                                playListAdapter.notifyItemChanged(nPosition);
                            playListAdapter.notifyItemChanged(position);
                            nPosition = position;
                            if (TextUtils.isEmpty(viodBean.getmSavePath())){
                                classPlayerModel.videoUrl(viodBean.getId(), buyId);
                            }else{
                                dismissLoading();
                                classPlayerModel.getPlayLocationUrl().setValue(viodBean.getmSavePath());
                            }
                        }
                        break;
                    case R.id.iv_download:
                        showLoading("");
                        mBaseViewHolder = helper;
                        type = 1;
                        ViodBean viodBean1 = (ViodBean) data;
                        viodBeanId = viodBean1.getId();
                        classPlayerModel.videoUrl(viodBeanId, buyId);
                        break;

                }

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        playListAdapter = new PlayListAdapter();
        playListAdapter.addNodeProvider(new PlayListChapterProvider());
        playListAdapter.addNodeProvider(playListJIeProvider);
        recyclerView.setAdapter(playListAdapter);

        //下载初始化
        initDownload();

    }

    @Override
    protected void initData(Context mContext) {
        classPlayerModel = new ViewModelProvider(getActivity()).get(ClassPlayerModel.class);
        classPlayerModel.getmClassInfoBeanLiveData().observe(this, new Observer<MClassInfoBean>() {
            @Override
            public void onChanged(MClassInfoBean mClassInfoBean) {
                dismissLoading();
                setData(mClassInfoBean);
            }
        });
        //视频播放url
        classPlayerModel.getVideoUrlBeansLiveData().observe(this, new Observer<VideoUrlBean>() {
            @Override
            public void onChanged(VideoUrlBean videoUrlBean) {
                videoUrlBean.setType(type);
                if (type == 0) {
                    dismissLoading();
                    classPlayerModel.getPlayUrlBean().setValue(videoUrlBean);
                } else {
                    downloadVideo(videoUrlBean);//开始下载
                }

            }
        });
        classPlayerModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
                L.e("=========errData:"+o);
            }
        });
        showLoading("");
        classPlayerModel.classInfo(classId);

    }


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
        videoDownloadManager.setDownloadInfoListener(new MyVideoDownloadListener());
    }

    //播放视频
    protected void downloadVideo(VideoUrlBean videoUrlBean) {
        if (videoUrlBean == null) {
            shortToast("视频加载失败");
            return;
        }
        VidAuth vidAuth = new VidAuth();
        vidAuth.setPlayAuth(videoUrlBean.getAuth());
        vidAuth.setVid(videoUrlBean.getVid());
        vidAuth.setRegion("cn-shanghai");
        videoDownloadManager.prepareDownload(vidAuth, viodBeanId);

    }


    class MyVideoDownloadListener implements ClassDownloadListener {

        @Override
        public void onPrepared(List<DownloadMediaInfo> viodBeans) {
            dismissLoading();
            if (downloadClassPopup == null) {
                downloadClassPopup = new DownloadClassPopup(getActivity());
                downloadClassPopup.setMyClickListener(new DownloadClassPopup.MyClickListener() {
                    @Override
                    public void ClickListener(DownloadMediaInfo downLoadTag) {
                        //点击了下载
                        videoDownloadManager.startDownload(downLoadTag);

                    }
                });
            }
            downloadClassPopup.showAllDownloadItems(viodBeans);
            downloadClassPopup.showPop();
        }

        @Override
        public void onAdd(DownloadMediaInfo info) {


        }

        @Override
        public void onStart(DownloadMediaInfo info) {
            ImageView imageView = mBaseViewHolder.getView(R.id.iv_download);
            imageView.setImageResource(R.drawable.download_progress_o);
            mBaseViewHolder.getView(R.id.tv_progress).setVisibility(View.VISIBLE);
            downloadingInfos.put(info, mBaseViewHolder);
            //通知更新下载数量
            classPlayerModel.getStringMutableLiveData()
                    .setValue("");
        }

        @Override
        public void onProgress(DownloadMediaInfo info, int percent) {

            L.e("======onProgress:" + percent);
            if (downloadingInfos.get(info) != null)
                setProgress(downloadingInfos.get(info).getView(R.id.tv_progress), percent);

        }

        @Override
        public void onStop(DownloadMediaInfo info) {

        }

        @Override
        public void onCompletion(DownloadMediaInfo info) {

        }

        @Override
        public void onError(DownloadMediaInfo info, ErrorCode code, String msg, String requestId) {
            dismissLoading();

        }

        @Override
        public void onWait(DownloadMediaInfo outMediaInfo) {

        }

        @Override
        public void onDelete(DownloadMediaInfo info) {

        }

        @Override
        public void onDeleteAll() {

        }

        @Override
        public void onFileProgress(DownloadMediaInfo info) {

        }
    }

    protected void setProgress(TextView tv_progress, int progress) {
        tv_progress.setText(progress + "%");

    }
}
