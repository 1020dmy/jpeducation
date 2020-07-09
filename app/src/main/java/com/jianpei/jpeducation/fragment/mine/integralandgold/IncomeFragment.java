package com.jianpei.jpeducation.fragment.mine.integralandgold;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.adapter.mine.IntegralAndGoldDetailsAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.integral.IntegralDataBean;
import com.jianpei.jpeducation.viewmodel.IntegralModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private IntegralModel integralModel;

    private int type = 1;
    private int pageIndex = 1;
    private int pageSize = 10;
    private IntegralAndGoldDetailsAdapter integralAndGoldDetailsAdapter;

    private List<IntegralDataBean.DataBean> list;

    @Override
    protected int initLayout() {
        return R.layout.fragment_all;
    }

    @Override
    protected void initView(View view) {
        integralModel = new ViewModelProvider(this).get(IntegralModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                showLoading("");
                integralModel.integralData(type, pageIndex, pageSize);


            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                showLoading("");
                integralModel.integralData(type, pageIndex, pageSize);
            }
        });
    }

    @Override
    protected void initData(Context mContext) {
        list = new ArrayList<>();
        integralAndGoldDetailsAdapter = new IntegralAndGoldDetailsAdapter(list, getActivity());
        recyclerView.setAdapter(integralAndGoldDetailsAdapter);
        integralModel.getIntegralDataLiveData().observe(this, new Observer<IntegralDataBean>() {
            @Override
            public void onChanged(IntegralDataBean integralDataBean) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                if (pageIndex == 1) {
                    list.clear();
                }
                list.addAll(integralDataBean.getData());
                integralAndGoldDetailsAdapter.notifyDataSetChanged();

            }
        });
        integralModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                shortToast(o);

            }
        });

        showLoading("");
        integralModel.integralData(type, pageIndex, pageSize);

    }
}
