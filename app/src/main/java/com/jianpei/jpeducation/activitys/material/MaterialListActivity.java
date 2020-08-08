package com.jianpei.jpeducation.activitys.material;


import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.bean.DownloadBean;
import com.jianpei.jpeducation.bean.material.MaterialDataBean;
import com.jianpei.jpeducation.bean.material.MaterialInfoBean;
import com.jianpei.jpeducation.bean.material.MaterialTitle;
import com.jianpei.jpeducation.utils.SpUtils;
import com.jianpei.jpeducation.utils.dialog.IntegralBuyDialog;
import com.jianpei.jpeducation.utils.down.MaterialDownloadListener;
import com.jianpei.jpeducation.utils.down.MaterialDownloadManager;
import com.jianpei.jpeducation.utils.down.ProgressUtil;
import com.jianpei.jpeducation.viewmodel.IntegralModel;
import com.jianpei.jpeducation.viewmodel.MaterialModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MaterialListActivity extends BaseNoStatusActivity implements MyItemOnClickListener {


    @BindView(R.id.iv_statue)
    ImageView ivStatue;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.imageButton)
    ImageButton imageButton;
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    String catName;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    //资料下载
    private MaterialModel materialModel;
    //积分支付
    private IntegralModel integralModel;


    private String cat_id;
    private int pageIndex = 1, pageSize = 10;


    private MaterialAdapter materialAdapter;
    private ArrayList<MaterialTitle> materialTitles;


    private MaterialInfoBean mMaterialInfoBean;
    private MaterialTitle mMaterialTitle;


    //积分付款的
    private IntegralBuyDialog integralBuyDialog;

    private LinkedHashMap<MaterialInfoBean, BaseViewHolder> downloadItems = new LinkedHashMap<>();


    @Override
    protected int setLayoutView() {
        return R.layout.activity_material_list;
    }

    @Override
    protected void initView() {
        setTitleViewPadding(ivStatue);
        //
        initShare();//初始化分享
        //
        tvTitle.setText("资料下载");
        cat_id = SpUtils.getValue(SpUtils.catId);
        catName = SpUtils.getValue(SpUtils.catName);
        tvClass.setText(catName);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //资料下载
        materialModel = new ViewModelProvider(this).get(MaterialModel.class);
        //积分支付
        integralModel = new ViewModelProvider(this).get(IntegralModel.class);


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                pageIndex = 1;
                materialModel.materialData(cat_id, pageIndex, pageSize);


            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                pageIndex++;
                materialModel.materialData(cat_id, pageIndex, pageSize);
            }
        });


    }


    @Override
    protected void initData() {
        materialTitles = new ArrayList<>();
        materialAdapter = new MaterialAdapter(this);
        recyclerView.setAdapter(materialAdapter);

        //获取资料标题
        materialModel.getMaterialDataBean().observe(this, new Observer<MaterialDataBean>() {
            @Override
            public void onChanged(MaterialDataBean materialDataBean) {
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                dismissLoading();
                if (pageIndex == 1) {
                    materialTitles.clear();
                }
                materialTitles.addAll(materialDataBean.getData());
                materialAdapter.setList(materialTitles);
            }
        });
        //获取资料
        materialModel.getMaterialInfoBeans().observe(this, new Observer<ArrayList<MaterialInfoBean>>() {
            @Override
            public void onChanged(ArrayList<MaterialInfoBean> materialInfoBeans) {
                dismissLoading();
                materialAdapter.nodeAddData(mMaterialTitle, 0, materialInfoBeans);

            }
        });

        //获取资料下载的url
        materialModel.getDownloadBeanMutableLiveData().observe(this, new Observer<DownloadBean>() {
            @Override
            public void onChanged(DownloadBean downloadBean) {
                dismissLoading();
                mMaterialInfoBean.setDownloadUrl(downloadBean.getDownloadUrl());
                if ("1".equals(downloadBean.getIs_pay())) {//0不需要积分，直接下载
                    MaterialDownloadManager.getInstance().setDownloadDir(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
                    MaterialDownloadManager.getInstance().setMaterialDownloadListener(downloadListener);
                    MaterialDownloadManager.getInstance().startDownload(mMaterialInfoBean);

                } else {
                    //要积分，弹窗
                    if (integralBuyDialog == null) {
                        integralBuyDialog = new IntegralBuyDialog(MaterialListActivity.this, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                integralBuyDialog.dismiss();
                                showLoading("");
                                integralModel.integrlPay(3, downloadBean.getIntergral_price(), "");
                            }
                        }, mMaterialInfoBean.getTitle(), mMaterialInfoBean.getDownload(), downloadBean.getIntergral_price(), downloadBean.getUser_intergral());
                    }
                    integralBuyDialog.show();

                }

            }
        });
        materialModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                dismissLoading();
                shortToast(o);
            }
        });

        //积分付款
        integralModel.getIntegrlPayLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissLoading();
                MaterialDownloadManager.getInstance().setMaterialDownloadListener(downloadListener);
                MaterialDownloadManager.getInstance().startDownload(mMaterialInfoBean);

            }
        });
        integralModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });

        showLoading("");
        materialModel.materialData(cat_id, pageIndex, pageSize);
    }


    @OnClick({R.id.iv_back, R.id.imageButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.imageButton:
                if (mShareAction != null) {
                    mShareAction.open();
                }
                break;
        }
    }

    //下载监听
    private MaterialDownloadListener downloadListener = new MaterialDownloadListener() {

        @Override
        public void repeat(MaterialInfoBean materialInfoBean) {//重复下载
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

//            ProgressUtil.updateProgressToViewWithMark(downloadItems.get(materialInfoBean).getView(R.id.progressBar), currentOffset, false);
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
    protected void onDestroy() {
        MaterialDownloadManager.getInstance().freed();
        downloadListener = null;
        downloadItems.clear();
        downloadItems = null;
        super.onDestroy();
    }


    @Override
    public void onItemClick(int position, View view) {

    }

    @Override
    public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
        switch (view.getId()) {
            case R.id.tv_down:
                mMaterialInfoBean = (MaterialInfoBean) data;
                if ("2".equals(mMaterialInfoBean.getStatus())) {//下载完成直接打开
                    startActivity(new Intent(MaterialListActivity.this, PdfReaderActivity.class).putExtra("materialInfoBean", mMaterialInfoBean));
                } else {//去下载
                    showLoading("");
                    downloadItems.put(mMaterialInfoBean, helper);
                    materialModel.getDownloadUrl(mMaterialInfoBean.getId());
                }
                break;
            case R.id.linearLayout:
                mMaterialTitle = (MaterialTitle) data;
                if (mMaterialTitle.isExpanded() && mMaterialTitle.getList().size() == 0) {//获取资料
                    showLoading("");
                    materialModel.subMaterialData(cat_id, mMaterialTitle.getId(), 0);
                }
                break;

        }

    }
}
