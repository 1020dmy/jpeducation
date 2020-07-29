package com.jianpei.jpeducation.activitys.mine;


import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.mine.MineMessageAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.mine.MessageBean;
import com.jianpei.jpeducation.bean.mine.MessageDataBean;
import com.jianpei.jpeducation.viewmodel.MineMessageModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

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


    private MineMessageModel mineMessageModel;
    private int page = 1, pageSize = 10;

    private List<MessageBean> messageBeans;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_mine_message;
    }

    @Override
    protected void initView() {
        tvTitle.setText("消息");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mineMessageModel = new ViewModelProvider(this).get(MineMessageModel.class);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                page = 1;
                mineMessageModel.messageData(page, pageSize);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                page++;
                mineMessageModel.messageData(page, pageSize);
            }
        });


    }

    @Override
    protected void initData() {
        messageBeans = new ArrayList<>();
        mineMessageAdapter = new MineMessageAdapter(messageBeans, this);
        recyclerView.setAdapter(mineMessageAdapter);

        mineMessageModel.getMessageDataBeanLiveData().observe(this, new Observer<MessageDataBean>() {
            @Override
            public void onChanged(MessageDataBean messageDataBean) {
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                dismissLoading();
                if (page == 1) {
                    messageBeans.clear();
                }
                messageBeans.addAll(messageDataBean.getData());
                mineMessageAdapter.notifyDataSetChanged();

            }
        });
        mineMessageModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();
                dismissLoading();
                shortToast(o);

            }
        });

        showLoading("");
        mineMessageModel.messageData(page, pageSize);

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
