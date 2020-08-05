package com.jianpei.jpeducation.activitys.mine.mclass;


import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aliyun.downloader.AliDownloaderFactory;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.player.TryPlayerActivity;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.offlineclass.OfflieClassAdapter;
import com.jianpei.jpeducation.adapter.offlineclass.OfflineClassContentProvider;
import com.jianpei.jpeducation.adapter.offlineclass.OfflineClassTitleProvider;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.bean.mclass.DirectoryBean;

import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.view.SlideRecyclerView;
import com.jianpei.jpeducation.viewmodel.OfflineClassRoomModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OfflineClassActivity extends BaseNoStatusActivity {

    private String TAG = "OfflineClassActivity";

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.imageButton)
    ImageButton imageButton;
    @BindView(R.id.recyclerView)
    SlideRecyclerView recyclerView;
    @BindView(R.id.iv_statue)
    ImageView ivStatue;

    private OfflieClassAdapter offlieClassAdapter;
    private OfflineClassContentProvider offlineClassContentProvider;
    private OfflineClassRoomModel offlineClassRoomModel;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_offline_class;
    }

    @Override
    protected void initView() {
        setTitleViewPadding(ivStatue);
        tvTitle.setText("离线课程");
        imageButton.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void initData() {
        offlineClassRoomModel = new ViewModelProvider(this).get(OfflineClassRoomModel.class);

        offlineClassContentProvider = new OfflineClassContentProvider();
        offlineClassContentProvider.addChildClickViewIds(R.id.tv_title, R.id.tv_delete);
        offlineClassContentProvider.setMyItemOnClickListener(new MyItemOnClickListener() {
            @Override
            public void onItemClick(int position, View view) {

            }

            @Override
            public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {
                ViodBean viodBean = (ViodBean) data;
                switch (view.getId()) {
                    case R.id.tv_title:
                        startActivity(new Intent(OfflineClassActivity.this, TryPlayerActivity.class).putExtra("localUrl", viodBean.getSavePath()).putExtra("title", viodBean.getTitle()));
                        break;
                    case R.id.tv_delete:
                        showLoading("");
                        offlineClassRoomModel.deleteViodBean(viodBean);
                        break;
                }
            }
        });
        offlieClassAdapter = new OfflieClassAdapter();
        offlieClassAdapter.addNodeProvider(new OfflineClassTitleProvider());
        offlieClassAdapter.addNodeProvider(offlineClassContentProvider);

        recyclerView.setAdapter(offlieClassAdapter);

        //获取离线数据
        offlineClassRoomModel.getDirectoryBeansLiveData().observe(this, new Observer<List<DirectoryBean>>() {
            @Override
            public void onChanged(List<DirectoryBean> directoryBeans) {
                dismissLoading();
                offlieClassAdapter.setList(directoryBeans);
            }
        });
        //删除结果
        offlineClassRoomModel.getDeleteResultLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                L.e("==========s:"+s);
                offlineClassRoomModel.getOfflineCompleteData();
            }
        });


        offlineClassRoomModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                dismissLoading();
                shortToast(o);
            }
        });

        shortToast("");
        offlineClassRoomModel.getOfflineCompleteData();


    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }


}
