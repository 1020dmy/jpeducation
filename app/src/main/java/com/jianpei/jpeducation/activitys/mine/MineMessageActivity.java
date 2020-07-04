package com.jianpei.jpeducation.activitys.mine;


import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.mine.MineMessageAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.OnClick;

public class MineMessageActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private MineMessageAdapter mineMessageAdapter;

    @Override
    protected int setLayoutView() {
        return R.layout.activity_mine_message;
    }

    @Override
    protected void initView() {
        tvTitle.setText("消息");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                refreshLayout.finishRefresh(2000);

            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
            }
        });


    }

    @Override
    protected void initData() {
        mineMessageAdapter = new MineMessageAdapter();
        recyclerView.setAdapter(mineMessageAdapter);

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
