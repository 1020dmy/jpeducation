package com.jianpei.jpeducation.fragment.mine.material;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.pdf.PdfReaderActivity;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.material.MaterialAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.DownloadBean;
import com.jianpei.jpeducation.bean.material.MaterialDataBean;
import com.jianpei.jpeducation.bean.material.MaterialInfoBean;
import com.jianpei.jpeducation.bean.material.MaterialTitle;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.down.MaterialDownloadListener;
import com.jianpei.jpeducation.utils.down.MaterialDownloadManager;
import com.jianpei.jpeducation.utils.down.ProgressUtil;
import com.jianpei.jpeducation.viewmodel.MaterialModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaterialDownloadFragment extends BaseFragment implements MyItemOnClickListener {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private MaterialModel materialModel;
    private MaterialAdapter materialAdapter;
    private List<MaterialTitle> materialTitles;


    private int page = 1, pagSize = 10;

    private MaterialInfoBean mMaterialInfoBean;
    private MaterialTitle mMaterialTitle;
    //
    private LinkedHashMap<MaterialInfoBean, BaseViewHolder> downloadItems = new LinkedHashMap<>();


    @Override
    protected int initLayout() {
        return R.layout.fragment_material_download;
    }

    @Override
    protected void initView(View view) {
        materialModel = new ViewModelProvider(this).get(MaterialModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                page++;
                materialModel.myMaterialData(page, pagSize);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                page = 1;
                materialModel.myMaterialData(page, pagSize);

            }
        });

    }

    @Override
    protected void initData(Context mContext) {
        materialTitles = new ArrayList<>();
        materialAdapter = new MaterialAdapter(this);
        recyclerView.setAdapter(materialAdapter);
        //一级列表数据
        materialModel.getMaterialDataBean().observe(this, new Observer<MaterialDataBean>() {
            @Override
            public void onChanged(MaterialDataBean materialDataBean) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                if (page == 1) {
                    materialTitles.clear();
                }
                materialTitles.addAll(materialDataBean.getData());
                materialAdapter.setList(materialTitles);
            }
        });
        //错误返回
        materialModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                shortToast(o);
            }
        });
        //二级列表结果
        materialModel.getMaterialInfoBeans().observe(this, new Observer<ArrayList<MaterialInfoBean>>() {
            @Override
            public void onChanged(ArrayList<MaterialInfoBean> materialInfoBeans) {
                dismissLoading();
                materialAdapter.nodeAddData(mMaterialTitle, 0, materialInfoBeans);
            }
        });
        //获取下载接口结果
        materialModel.getDownloadBeanMutableLiveData().observe(this, new Observer<DownloadBean>() {
            @Override
            public void onChanged(DownloadBean downloadBean) {
                dismissLoading();
                mMaterialInfoBean.setDownloadUrl(downloadBean.getDownloadUrl());//设置下载接口
                MaterialDownloadManager.getInstance().setDownloadDir(getActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));//设置存储路径
                MaterialDownloadManager.getInstance().setMaterialDownloadListener(downloadListener);//设置监听
                MaterialDownloadManager.getInstance().startDownload(mMaterialInfoBean);//开始下载

            }
        });
        showLoading("");
        materialModel.myMaterialData(page, pagSize);

    }

    @Override
    public void onItemClick(int position, View view) {

    }

    @Override
    public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        switch (view.getId()) {
            case R.id.linearLayout:
                mMaterialTitle = (MaterialTitle) data;
                if (mMaterialTitle.isExpanded() && mMaterialTitle.getList().size() == 0) {
                    showLoading("");
                    materialModel.subMaterialData(mMaterialTitle.getCat_id(), mMaterialTitle.getId(), 1);
                }
                break;
            case R.id.tv_down:
                mMaterialInfoBean = (MaterialInfoBean) data;
                if ("2".equals(mMaterialInfoBean.getStatus())) {//下载完成直接打开
                    startActivity(new Intent(getActivity(), PdfReaderActivity.class).putExtra("materialInfoBean", mMaterialInfoBean));
                } else {//去下载
                    //获取下载
                    showLoading("");
                    downloadItems.put(mMaterialInfoBean, helper);//下载队列
                    materialModel.getDownloadUrl(mMaterialInfoBean.getId());//获取下载接口
                }
                break;
        }
    }

    //下载监听
    private MaterialDownloadListener downloadListener = new MaterialDownloadListener() {

        @Override
        public void repeat(MaterialInfoBean materialInfoBean) {//重复下载
            L.e("MaterialDownloadListener:repeat");
            if (downloadItems != null)
                downloadItems.remove(materialInfoBean);


        }

        @Override
        public void taskStart(MaterialInfoBean materialInfoBean) {
            if (downloadItems == null)
                return;
            TextView tvDown = downloadItems.get(materialInfoBean).getView(R.id.tv_down);
            ProgressBar progressBar = downloadItems.get(materialInfoBean).getView(R.id.progressBar);
            tvDown.setText("正在下载");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void retry(MaterialInfoBean materialInfoBean) {

        }

        @Override
        public void connected(MaterialInfoBean materialInfoBean) {

        }

        @Override
        public void progress(MaterialInfoBean materialInfoBean, long currentOffset, long totalLength) {
            //进度
            if (downloadItems == null)
                return;
            ProgressUtil.updateProgressToView(downloadItems.get(materialInfoBean).getView(R.id.progressBar), currentOffset, totalLength, false);
        }

        @Override
        public void onError(String errMsg) {
            shortToast(errMsg);

        }

        @Override
        public void onSuccess(MaterialInfoBean materialInfoBean) {
            if (downloadItems == null)
                return;
            TextView tvDown = downloadItems.get(materialInfoBean).getView(R.id.tv_down);
            tvDown.setText("查看");
        }
    };


    @Override
    public void onDestroy() {
        MaterialDownloadManager.getInstance().freed();
        downloadListener = null;
        downloadItems.clear();
        downloadItems = null;
        super.onDestroy();
    }
}
