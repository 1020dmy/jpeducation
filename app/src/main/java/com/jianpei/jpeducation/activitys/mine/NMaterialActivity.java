package com.jianpei.jpeducation.activitys.mine;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.material.MaterialAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.DownloadBean;
import com.jianpei.jpeducation.bean.material.MaterialDataBean;
import com.jianpei.jpeducation.bean.material.MaterialInfoBean;
import com.jianpei.jpeducation.bean.material.MaterialTitle;
import com.jianpei.jpeducation.viewmodel.MaterialModel;
import com.jianpei.wps.FileDisplayActivity;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NMaterialActivity extends BaseActivity implements MyItemOnClickListener {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
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

    @Override
    protected int setLayoutView() {
        return R.layout.activity_n_material;
    }

    @Override
    protected void initView() {
        tvTitle.setText("我的资料");

        materialModel = new ViewModelProvider(this).get(MaterialModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
    protected void initData() {
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
                FileDisplayActivity.actionStart(NMaterialActivity.this,downloadBean.getDownloadUrl(),mMaterialInfoBean.getTitle());



            }
        });
        showLoading("");
        materialModel.myMaterialData(page, pagSize);
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
            case R.id.linearLayout:
                mMaterialTitle = (MaterialTitle) data;
                if (mMaterialTitle.isExpanded() && mMaterialTitle.getList().size() == 0) {
                    showLoading("");
                    materialModel.subMaterialData(mMaterialTitle.getCat_id(), mMaterialTitle.getId(), 1);
                }
                break;
            case R.id.tv_down:
                mMaterialInfoBean = (MaterialInfoBean) data;
                    //获取下载
                showLoading("");
                materialModel.getDownloadUrl(mMaterialInfoBean.getId());//获取下载接口
                break;
        }
    }
}
