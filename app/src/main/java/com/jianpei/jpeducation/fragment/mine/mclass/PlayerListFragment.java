package com.jianpei.jpeducation.fragment.mine.mclass;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliyun.downloader.AliDownloaderFactory;
import com.aliyun.downloader.AliMediaDownloader;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.nativeclass.TrackInfo;
import com.aliyun.player.source.VidAuth;
import com.aliyun.private_service.PrivateService;
import com.aliyun.vodplayerview.activity.AliyunPlayerSkinActivity;
import com.aliyun.vodplayerview.utils.Common;
import com.aliyun.vodplayerview.utils.download.AliyunDownloadManager;
import com.aliyun.vodplayerview.view.download.DownloadDataProvider;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
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
import com.jianpei.jpeducation.utils.pop.DownloadClassPopup;
import com.jianpei.jpeducation.viewmodel.ClassPlayerModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
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

    private int type;

    private AliyunDownloadManager downloadManager;
    private DownloadDataProvider downloadDataProvider;



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
                            classPlayerModel.videoUrl(viodBean.getId(), buyId);
                        }
                        break;
                    case R.id.iv_download:
                        type = 1;
                        classPlayerModel.videoUrl(viodBean.getId(), buyId);
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
                if (type == 0) {
                    dismissLoading();
                    classPlayerModel.getPlayUrlBean().setValue(videoUrlBean);

                } else {//下载

                }


            }
        });
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


    private Common commenUtils;

    private void copyAssets() {
        commenUtils = Common.getInstance(getActivity().getApplicationContext()).copyAssetsToSD("encrypt", "aliyun");
        commenUtils.setFileOperateCallback(

                new Common.FileOperateCallback() {
                    @Override
                    public void onSuccess() {
                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_save/");
                        if (!file.exists()) {
                            file.mkdir();
                        }

                        // 获取AliyunDownloadManager对象
                        downloadManager = AliyunDownloadManager.getInstance(getActivity().getApplicationContext());
                        downloadManager.setEncryptFilePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/aliyun/encryptedApp.dat");
                        PrivateService.initService(getActivity().getApplicationContext(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/aliyun/encryptedApp.dat");
                        downloadManager.setDownloadDir(file.getAbsolutePath());
                        //设置同时下载个数
                        downloadManager.setMaxNum(3);

                        downloadDataProvider = DownloadDataProvider.getSingleton(getActivity().getApplicationContext());
                        // 更新sts回调
//                        downloadManager.setRefreshStsCallback(new AliyunPlayerSkinActivity.MyRefreshStsCallback());

                        // 视频下载的回调
//                        downloadManager.setDownloadInfoListener(new AliyunPlayerSkinActivity.MyDownloadInfoListener(AliyunPlayerSkinActivity.this));
//                        downloadViewSetting(downloadView);
                    }

                    @Override
                    public void onFailed(String error) {
                    }
                });
    }
}
