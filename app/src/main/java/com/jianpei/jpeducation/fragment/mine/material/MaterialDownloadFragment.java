package com.jianpei.jpeducation.fragment.mine.material;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.pdf.PdfReaderActivity;
import com.jianpei.jpeducation.adapter.material.MaterialAdapter;
import com.jianpei.jpeducation.adapter.material.MaterialInfoProvider;
import com.jianpei.jpeducation.adapter.material.MaterialTitleProvider;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.DownloadBean;
import com.jianpei.jpeducation.bean.material.MaterialDataBean;
import com.jianpei.jpeducation.bean.material.MaterialInfoBean;
import com.jianpei.jpeducation.bean.material.MaterialTitle;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.down.NDownloadListener;
import com.jianpei.jpeducation.viewmodel.MaterialModel;
import com.liulishuo.okdownload.DownloadTask;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaterialDownloadFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private MaterialModel materialModel;

    private MaterialAdapter materialAdapter;


    private int page = 1, pagSize = 10;
    //
    private MaterialTitleProvider materialTitleProvider;
    private MaterialInfoProvider materialInfoProvider;
    //
    private MaterialTitle materialTitle;

    private NDownloadListener nDownloadListener;
    private String name;

    private MaterialInfoBean materialInfoBean;
    private BaseViewHolder baseViewHolder;

    @Override
    protected int initLayout() {
        return R.layout.fragment_material_download;
    }

    @Override
    protected void initView(View view) {
        materialModel = new ViewModelProvider(this).get(MaterialModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    protected void initData(Context mContext) {
        materialAdapter = new MaterialAdapter();
        //
        materialTitleProvider = new MaterialTitleProvider();
        materialTitleProvider.setMaterialTitleOnClickListener(new MaterialTitleProvider.MaterialTitleOnClickListener() {//点击标题
            @Override
            public void materialTitleOnClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
                materialTitle = (MaterialTitle) data;
                if (materialTitle.getChildNode() == null || materialTitle.getChildNode().size() == 0) {
                    showLoading("");
                    materialModel.subMaterialData(materialTitle.getCat_id(), materialTitle.getId(), 1);
                }
            }
        });
        //
        materialInfoProvider = new MaterialInfoProvider();
        materialInfoProvider.addChildClickViewIds(R.id.tv_down);
        materialInfoProvider.setMaterialInfoOnClickListener(new MaterialInfoProvider.MaterialInfoOnClickListener() {//点击下载按钮
            @Override
            public void materialInfoOnClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

                materialInfoBean = (MaterialInfoBean) data;
                if (!"2".equals(materialInfoBean.getStatus())) {
                    showLoading("");
                    baseViewHolder = helper;
                    //获取下载地址
                    materialModel.getDownloadUrl(materialInfoBean.getId());
                } else {
                    startActivity(new Intent(getActivity(), PdfReaderActivity.class).putExtra("materialInfoBean", materialInfoBean));
                }

            }
        });
        //
        materialAdapter.addNodeProvider(materialTitleProvider);
        materialAdapter.addNodeProvider(materialInfoProvider);

        recyclerView.setAdapter(materialAdapter);
        //一级列表数据
        materialModel.getMaterialDataBean().observe(this, new Observer<MaterialDataBean>() {
            @Override
            public void onChanged(MaterialDataBean materialDataBean) {
                dismissLoading();
                materialAdapter.setList(materialDataBean.getData());
            }
        });
        //错误返回
        materialModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });
        //二级列表结果
        materialModel.getMaterialInfoBeans().observe(this, new Observer<ArrayList<MaterialInfoBean>>() {
            @Override
            public void onChanged(ArrayList<MaterialInfoBean> materialInfoBeans) {
                dismissLoading();
                materialAdapter.nodeAddData(materialTitle, 0, materialInfoBeans);
            }
        });
        //获取下载接口结果
        materialModel.getDownloadBeanMutableLiveData().observe(this, new Observer<DownloadBean>() {
            @Override
            public void onChanged(DownloadBean downloadBean) {
                dismissLoading();
                if (nDownloadListener == null)
                    nDownloadListener = new NDownloadListener();
                L.e("===============");
                DownloadTask task = new DownloadTask.Builder(downloadBean.getDownloadUrl(), getActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS))
                        .setFilename(materialInfoBean.getTitle())
                        .setMinIntervalMillisCallbackProcess(30)
                        .setPassIfAlreadyCompleted(false)
                        .build();
                nDownloadListener.bind(task, baseViewHolder, materialInfoBean);
                task.enqueue(nDownloadListener);
            }
        });
        showLoading("");
        materialModel.myMaterialData(page, pagSize);

    }

}
