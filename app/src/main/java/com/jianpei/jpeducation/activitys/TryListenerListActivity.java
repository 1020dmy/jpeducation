package com.jianpei.jpeducation.activitys;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.TryListenerListAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.base.BaseNoStatusActivity;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TryListenerListActivity extends BaseNoStatusActivity {


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
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private TryListenerListAdapter tryListenerListAdapter;
    private List<GroupInfoBean> mGroupInfoBeans;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_try_listener_list;
    }

    @Override
    protected void initView() {
        tvTitle.setText("免费试听");
        setTitleViewPadding(ivStatue);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {


            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });

    }

    @Override
    protected void initData() {
        mGroupInfoBeans = new ArrayList<>();
        tryListenerListAdapter = new TryListenerListAdapter(mGroupInfoBeans, this);
        recyclerView.setAdapter(tryListenerListAdapter);

    }


    @OnClick({R.id.iv_back, R.id.imageButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.imageButton:
                break;
        }
    }
}
