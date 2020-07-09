package com.jianpei.jpeducation.fragment.mine.order;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.mine.CommentClassActivity;
import com.jianpei.jpeducation.activitys.mine.mclass.MyClassActivity;
import com.jianpei.jpeducation.activitys.order.OrderInfoActivity;
import com.jianpei.jpeducation.adapter.order.CompleteOrderAdapter;
import com.jianpei.jpeducation.adapter.order.MyOrderListItemListener;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.order.OrderDataBean;
import com.jianpei.jpeducation.bean.order.OrderListBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.viewmodel.OrderListModel;
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
public class CompleteOrderFragment extends BaseFragment implements MyOrderListItemListener {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private OrderListModel orderListModel;

    private List<OrderDataBean> mOrderDataBeans;

    private CompleteOrderAdapter completeOrderAdapter;
    private int page = 1;


    @Override
    protected int initLayout() {
        return R.layout.fragment_all_order;
    }

    @Override
    protected void initView(View view) {
        orderListModel = new ViewModelProvider(this).get(OrderListModel.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                L.e("开始刷新");
                page = 1;
                orderListModel.orderData(3, page, 10);

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                L.e("开始加载更多");
                page++;
                orderListModel.orderData(3, page, 10);
            }
        });

    }

    @Override
    protected void initData(Context mContext) {
        mOrderDataBeans = new ArrayList<>();
        completeOrderAdapter = new CompleteOrderAdapter(mOrderDataBeans, getActivity());
        completeOrderAdapter.setMyOrderListItemListener(this);
        recyclerView.setAdapter(completeOrderAdapter);
        //请求订单列表结果
        orderListModel.getOrderDataBeansLiveData().observe(this, new Observer<OrderListBean>() {
            @Override
            public void onChanged(OrderListBean orderListBean) {

                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                if (page == 1) {
                    mOrderDataBeans.clear();
                }
                mOrderDataBeans.addAll(orderListBean.getData());
                completeOrderAdapter.notifyDataSetChanged();
            }
        });
        //错误返回
        orderListModel.getErrData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String o) {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
                dismissLoading();
                shortToast(o);
            }
        });
        showLoading("");
        orderListModel.orderData(3, page, 10);
    }

    @Override
    public void onClick(View view, int position) {
        switch (view.getId()) {
            case R.id.tv_student:
                startActivity(new Intent(getActivity(), MyClassActivity.class));
                break;
            case R.id.tv_comment:
                startActivity(new Intent(getActivity(), CommentClassActivity.class).putExtra("classId", mOrderDataBeans.get(position).getGroup_id()));
                break;
            case R.id.linearLayout:
                startActivity(new Intent(getActivity(), OrderInfoActivity.class).putExtra("orderDataBean", mOrderDataBeans.get(position)));
                break;
        }

    }
}
