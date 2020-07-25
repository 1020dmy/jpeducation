package com.jianpei.jpeducation.activitys.mine.gold;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.gold.GoldDetailAdapter;
import com.jianpei.jpeducation.base.BaseActivity;
import com.jianpei.jpeducation.bean.gold.GoldBean;
import com.jianpei.jpeducation.bean.gold.VirtualCurrencyListBean;
import com.jianpei.jpeducation.viewmodel.GoldModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GoldDetailActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_gold)
    TextView tvGold;
    @BindView(R.id.tv_exchange)
    TextView tvExchange;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private GoldDetailAdapter goldDetailAdapter;

    private GoldModel goldModel;

    private int page = 1, pageSize = 10;

    private List<GoldBean> goldBeans;


    @Override
    protected int setLayoutView() {
        return R.layout.activity_gold_detail;
    }

    @Override
    protected void initView() {

        tvTitle.setText("金币明细");
        tvRight.setText("兑换记录");
        tvRight.setVisibility(View.VISIBLE);

        goldModel = new ViewModelProvider(this).get(GoldModel.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                page++;
                goldModel.virtualCurrencyList(page, pageSize);

            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                showLoading("");
                page = 1;
                goldModel.virtualCurrencyList(page, pageSize);
            }
        });


    }

    @Override
    protected void initData() {
        goldBeans = new ArrayList<>();
        goldDetailAdapter = new GoldDetailAdapter(goldBeans, this);
        recyclerView.setAdapter(goldDetailAdapter);
        goldModel.getVirtualCurrencyListBeanLiveData().observe(this, new Observer<VirtualCurrencyListBean>() {
            @Override
            public void onChanged(VirtualCurrencyListBean virtualCurrencyListBean) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                if (page == 1) {
                    goldBeans.clear();
                }
                goldBeans.addAll(virtualCurrencyListBean.getData());
                goldDetailAdapter.notifyDataSetChanged();

            }
        });
        goldModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                shortToast(o);
            }
        });

        showLoading("");
        goldModel.virtualCurrencyList(page, pageSize);

    }


    @OnClick({R.id.iv_back, R.id.tv_right, R.id.tv_exchange})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                break;
            case R.id.tv_exchange:
                startActivity(new Intent(this, GoldWithdrawActivity.class));
                break;
        }
    }
}
