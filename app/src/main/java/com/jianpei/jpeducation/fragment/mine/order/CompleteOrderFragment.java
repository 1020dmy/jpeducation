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

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.activitys.mine.CommentClassActivity;
import com.jianpei.jpeducation.activitys.mine.mclass.MyClassActivity;
import com.jianpei.jpeducation.activitys.order.OrderInfoActivity;
import com.jianpei.jpeducation.adapter.MyItemOnClickListener;
import com.jianpei.jpeducation.adapter.order.NOrderListAdapter;
import com.jianpei.jpeducation.base.BaseFragment;
import com.jianpei.jpeducation.bean.order.OrderDataBean;
import com.jianpei.jpeducation.bean.order.OrderListBean;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.viewmodel.DataNoticeChangeModel;
import com.jianpei.jpeducation.viewmodel.OrderListModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteOrderFragment extends BaseFragment implements MyItemOnClickListener {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private OrderListModel orderListModel;
    private DataNoticeChangeModel dataNoticeChangeModel;


    private List<OrderDataBean> mOrderDataBeans;

//    private CompleteOrderAdapter completeOrderAdapter;

    private NOrderListAdapter nOrderListAdapter;

    private int page = 1, pageSize = 10;


    @Override
    protected int initLayout() {
        return R.layout.fragment_all_order;
    }

    @Override
    protected void initView(View view) {
        orderListModel = new ViewModelProvider(this).get(OrderListModel.class);
        //
        dataNoticeChangeModel = new ViewModelProvider(getActivity()).get(DataNoticeChangeModel.class);
//


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                L.e("开始刷新");
                page = 1;
                orderListModel.orderData(3, page, pageSize);

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                L.e("开始加载更多");
                page++;
                orderListModel.orderData(3, page, pageSize);
            }
        });

    }

    @Override
    protected void initData(Context mContext) {
        mOrderDataBeans = new ArrayList<>();
        nOrderListAdapter = new NOrderListAdapter(mOrderDataBeans, getActivity());
        nOrderListAdapter.setMyItemOnClickListener(this);
        recyclerView.setAdapter(nOrderListAdapter);
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
                if (orderListBean != null)
                    mOrderDataBeans.addAll(orderListBean.getData());
                nOrderListAdapter.notifyDataSetChanged();
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

        //通知全局更新
        dataNoticeChangeModel.getNoticeChangeLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
//                showLoading("");
                page = 1;
                orderListModel.orderData(3, page, pageSize);
            }
        });
        showLoading("");
        orderListModel.orderData(3, page, pageSize);
    }

    @Override
    public void onItemClick(int position, View view) {
        switch (view.getId()) {
            case R.id.tv_comment://去评论
                startActivity(new Intent(getActivity(), CommentClassActivity.class).putExtra("classId", mOrderDataBeans.get(position).getGroup_id()));
                break;
            case R.id.tv_student://去学习
                startActivity(new Intent(getActivity(), MyClassActivity.class));
                break;
            case R.id.linearLayout:
//                startActivity(new Intent(getActivity(), OrderInfoActivity.class).putExtra("orderId", mOrderDataBeans.get(position).getId()));
                startActivityForResult(new Intent(getActivity(), OrderInfoActivity.class).putExtra("orderId", mOrderDataBeans.get(position).getId()), 111);
                break;
        }

    }

    @Override
    public void onItemClick(@NotNull BaseViewHolder helper, @NotNull View view, BaseNode data, int position) {

    }

 /*   @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == 112) {
            showLoading("");
            page = 1;
            orderListModel.orderData(3, page, pageSize);
        }
    }*/

    //    @Override
//    public void onClick(View view, int position) {
//        switch (view.getId()) {
//            case R.id.tv_student:
//                startActivity(new Intent(getActivity(), MyClassActivity.class));
//                break;
//            case R.id.tv_comment:
//                startActivity(new Intent(getActivity(), CommentClassActivity.class).putExtra("classId", mOrderDataBeans.get(position).getGroup_id()));
//                break;
//            case R.id.linearLayout:
//                startActivity(new Intent(getActivity(), OrderInfoActivity.class).putExtra("orderDataBean", mOrderDataBeans.get(position)));
//                break;
//        }
//
//    }
}
